package inso.revex_app_resty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static us.monoid.web.Resty.content;


public class ActivityPowerPlants extends ActionBarActivity {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_plants);

        new TokenTask().execute();
        token = "admin:1428595104815:0085accb498755c8f3aabe54d86b6696";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_power_plants, menu);
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

    public void getXPathToken() {
        JSONResource res = null;

        try {
//            Resty resty = new Resty().identifyAsMozilla();
//            resty.withHeader("Content-Type", "application/json;charset=UTF-8");
//            resty.withHeader("Accept", "application/json, text/plain, */*");
//            res = resty.json("https://revex.inso.tuwien.ac.at/dev/api/user/authentication", content("{\"username\":\"admin\",\"password\":\"admin\"}"));
//
//            JSONObject ob = res.toObject();
//            token  = (String) ob.get("token");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textViewToChange = (TextView) findViewById(R.id.textViewToken);
                    textViewToChange.setText(token);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getPowerPlants() {
        JSONResource res = null;

        try {
            Resty resty = new Resty().identifyAsMozilla();
            resty.withHeader("Accept", "application/json, text/plain, */*");
            resty.withHeader("X-Auth-Token", token);
            res = resty.json("https://revex.inso.tuwien.ac.at/dev/api/powerplants");

            final JSONArray jsonArray = res.array();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   TextView textViewToChange = (TextView) findViewById(R.id.textViewPowerPlants);
                    try {
                        StringBuilder sb = new StringBuilder();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            sb.append(jsonObject.getString("name"));
                            sb.append("\n");
                        }
                        textViewToChange.setText(sb.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TokenTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            getXPathToken();
            getPowerPlants();
            return null;
        }
    }


}
