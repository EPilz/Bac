package inso.revex.androidannotations.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

import inso.revex.androidannotations.activity.adapter.PowerPlantAdapter;
import inso.revex.androidannotations.rest.model.PowerPlant;
import inso.revex.androidannotations.rest.service.PowerPlantService;

@EActivity(R.layout.activity_all_power_plant)
public class ActivityAllPowerPlants extends Activity {

    public static final String KEY = "ID";

    @RestService
    PowerPlantService powerPlantService;

    @AfterViews
    public void init() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView_powerPlants);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(ActivityAllPowerPlants.this);
        rv.setLayoutManager(llm);

        rv.setItemAnimator(new DefaultItemAnimator());

        LoadTask loadTask = new LoadTask();
        loadTask.execute();
    }

    public void createPowerPlant(View view) {
       Intent i = new Intent(ActivityAllPowerPlants.this, ActivityCreatePowerPlant_.class);
       startActivity(i);
    }

    private class LoadTask extends AsyncTask<Void, Void, List<PowerPlant>> {

        @Override
        protected List<PowerPlant> doInBackground(Void... params) {
            return powerPlantService.getPowerPlants();
        }

        @Override
        protected void onPostExecute(List<PowerPlant> powerPlants) {
            RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView_powerPlants);

            PowerPlantAdapter adapter = new PowerPlantAdapter(powerPlants, ActivityAllPowerPlants.this);
            rv.setAdapter(adapter);

            adapter.notifyDataSetChanged();
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
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void updatePowerPlant(final PowerPlant powerPlant) {
        Intent i = new Intent(this, ActivityUpdatePowerPlant_.class);
        i.putExtra(ActivityAllPowerPlants.KEY, powerPlant.getId());
        startActivity(i);
    }

    public class DeletePowerPlantTask extends AsyncTask<PowerPlant, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(PowerPlant... params) {
            return powerPlantService.deletePowerPlantById(params[0].getId());
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            Intent i = new Intent(ActivityAllPowerPlants.this, ActivityAllPowerPlants_.class);
            startActivity(i);
        }
    }
}
