package inso.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import inso.rest.ServiceGenerator;
import inso.rest.model.PowerPlant;
import inso.rest.service.PowerPlantService;
import inso.rest.service.UserService;
import inso.util.UtilitiesManager;


public class ActivityAllPowerPlants extends ActionBarActivity {

    public static final String KEY = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_power_plant);

        LoadTask loadTask = new LoadTask();
        loadTask.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_power_plant, menu);
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


    public void createPowerPlant(View view) {
        Intent i = new Intent(ActivityAllPowerPlants.this, ActivityCreatePowerPlant.class);
        startActivity(i);
    }

    private class LoadTask extends AsyncTask<Void, Void, List<PowerPlant>> {

        @Override
        protected List<PowerPlant> doInBackground(Void... params) {
            UserService userService = ServiceGenerator.createService(UserService.class);
            UtilitiesManager.getInstance().
                    setAuthToken(userService.getAuthToken(UtilitiesManager.getInstance().getStandardUser()));

            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            return  powerPlantService.getPowerPlants();
        }

        @Override
        protected void onPostExecute(List<PowerPlant> powerPlants) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listViewPowerPlants);
            for (final PowerPlant powerPlant : powerPlants) {
                Button b = new Button(ActivityAllPowerPlants.this);
                b.setText(powerPlant.getName());
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(ActivityAllPowerPlants.this, ActivityPowerPlantOverview.class);
                        i.putExtra(ActivityAllPowerPlants.KEY, powerPlant.getId());
                        startActivity(i);
                    }
                });
                linearLayout.addView(b);
            }
        }
    }
}
