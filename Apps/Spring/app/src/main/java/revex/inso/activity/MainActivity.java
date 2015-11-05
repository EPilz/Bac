package revex.inso.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import revex.inso.rest.model.AuthToken;
import revex.inso.rest.model.User;
import revex.inso.rest.service.UserService;
import revex.inso.util.UtilitiesManager;


public class MainActivity extends Activity {

    private final static String FILE_NAME = "config";
    private final static String KEY_TOKEN = "token";
    private final static String KEY_TOKENTIME = "tokenTime";
    private final static String KEY_NAME = "userName";
    private final static long MINUTE_MILLIS = 60000;
    private final static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.buttonLogin);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = getSharedPreferences(FILE_NAME, 0);
                String nameOld = sharedPrefs.getString(KEY_NAME, "");
                String user = ((EditText)  findViewById(R.id.editText_name)).getText().toString();
                String password  = ((EditText)  findViewById(R.id.editText_password)).getText().toString();
                UtilitiesManager.getInstance().setUser(new User(user, password));

                if(nameOld.equals(user)) {
                    int minutes = minutesDiffFromToken();
                    Log.i("my", "minutes: " + minutes);
                    if (minutes >= 0 && minutes <= 50) {
                        String token = sharedPrefs.getString(KEY_TOKEN, "");
                        UtilitiesManager.getInstance().setAuthToken(new AuthToken(token));
                        Log.i("my", "Token: " + UtilitiesManager.getInstance().getAuthToken() + "");
                    } else {
                        Log.i("my", "else load task");
                        LoginTask loadTask = new LoginTask();
                        loadTask.execute();
                    }
                } else {
                    Log.i("my", "new user");
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.putString(KEY_NAME, UtilitiesManager.getInstance().getUser().getUsername());
                    editor.commit();
                    LoginTask loadTask = new LoginTask();
                    loadTask.execute();
                }

                startActivity(new Intent(MainActivity.this, ActivityAllPowerPlants.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int minutesDiffFromToken() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        SharedPreferences sharedPrefs = getSharedPreferences(FILE_NAME, 0);
        String tokenTime = sharedPrefs.getString(KEY_TOKENTIME, "");

        if(! tokenTime.equals("")) {
            try {
                Date tokenDate = formatter.parse(tokenTime);
                Date currentDate = new Date();

                return (int) ((currentDate.getTime() / MINUTE_MILLIS) - (tokenDate.getTime() / MINUTE_MILLIS));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private class LoginTask extends AsyncTask<Void, Void, AuthToken> {

        @Override
        protected AuthToken doInBackground(Void... params) {
            AuthToken authToken = UserService.getAuthToken(UtilitiesManager.getInstance().getUser());
            UtilitiesManager.getInstance().setAuthToken(authToken);

            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

            SharedPreferences sharedPrefs = getSharedPreferences(FILE_NAME, 0);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(KEY_TOKEN, UtilitiesManager.getInstance().getAuthToken().getToken());
            editor.putString(KEY_TOKENTIME, formatter.format(new Date()));
            editor.commit();

            return UtilitiesManager.getInstance().getAuthToken();
        }

    }

}
