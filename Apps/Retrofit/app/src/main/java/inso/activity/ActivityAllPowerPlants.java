package inso.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import inso.activity.adapter.PowerPlantAdapter;
import inso.rest.ServiceGenerator;
import inso.rest.model.PowerPlant;
import inso.rest.service.PowerPlantService;
import inso.rest.service.UserService;
import inso.util.UtilitiesManager;


public class ActivityAllPowerPlants extends Activity {

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
                    setAuthToken(userService.getAuthToken(UtilitiesManager.getInstance().getUser()));

            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            return  powerPlantService.getPowerPlants();
        }

        @Override
        protected void onPostExecute(List<PowerPlant> powerPlants) {
            RecyclerView rv = (RecyclerView)findViewById(R.id.recyclerView_powerPlants);
            rv.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(ActivityAllPowerPlants.this);
            rv.setLayoutManager(llm);

            PowerPlantAdapter adapter = new PowerPlantAdapter(powerPlants, ActivityAllPowerPlants.this);
            rv.setAdapter(adapter);
          //  rv.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter.notifyDataSetChanged();

            /*LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listViewPowerPlants);
            for (final PowerPlant powerPlant : powerPlants) {
                LinearLayout linearLayoutInline = new LinearLayout(ActivityAllPowerPlants.this);
                linearLayoutInline.setOrientation(LinearLayout.HORIZONTAL);

                Button buttonPowerPlant = new Button(ActivityAllPowerPlants.this);
                buttonPowerPlant.setText(powerPlant.getName());
                buttonPowerPlant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ActivityAllPowerPlants.this, ActivityPowerPlantOverview.class);
                        i.putExtra(ActivityAllPowerPlants.KEY, powerPlant.getId());
                        startActivity(i);
                    }
                });

                Button buttonDelete = new Button(ActivityAllPowerPlants.this);
                buttonDelete.setText("D");
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DeletePowerPlantTask deletePowerPlantTask = new DeletePowerPlantTask();
                        deletePowerPlantTask.execute(powerPlant);
                    }
                });
                linearLayoutInline.addView(buttonPowerPlant);
                linearLayoutInline.addView(buttonDelete);
                linearLayout.addView(linearLayoutInline);
            }*/
        }
    }

    public void deletePowerPlant(final PowerPlant powerPlant) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete PowerPlant");
        alert.setMessage("Are you sure you want delete this?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeletePowerPlantTask deletePowerPlantTask = new DeletePowerPlantTask();
                deletePowerPlantTask.execute(powerPlant);

                dialog.dismiss();
            }
        });
        alert.setNegativeButton("mm", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public class DeletePowerPlantTask extends AsyncTask<PowerPlant, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(PowerPlant... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            return  powerPlantService.deletePowerPlantById(params[0].getId());
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            Intent i = new Intent(ActivityAllPowerPlants.this, ActivityAllPowerPlants.class);
            startActivity(i);
        }
    }
}
