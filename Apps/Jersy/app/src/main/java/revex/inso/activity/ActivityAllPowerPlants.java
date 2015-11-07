package revex.inso.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import revex.inso.activity.adapter.PowerPlantAdapter;
import revex.inso.rest.model.PowerPlant;
import revex.inso.rest.service.PowerPlantService;


public class ActivityAllPowerPlants extends Activity {

    public static final String KEY = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_power_plant);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView_powerPlants);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(ActivityAllPowerPlants.this);
        rv.setLayoutManager(llm);

        rv.setItemAnimator(new DefaultItemAnimator());

        LoadTask loadTask = new LoadTask();
        loadTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_power_plant, menu);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
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
        //TODO Intent i = new Intent(ActivityAllPowerPlants.this, ActivityCreatePowerPlant.class);
        //startActivity(i);
    }

    private class LoadTask extends AsyncTask<Void, Void, List<PowerPlant>> {

        @Override
        protected List<PowerPlant> doInBackground(Void... params) {
            return PowerPlantService.getPowerPlants();
        }

        @Override
        protected void onPostExecute(List<PowerPlant> powerPlants) {
            RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView_powerPlants);

            PowerPlantAdapter adapter = new PowerPlantAdapter(powerPlants, ActivityAllPowerPlants.this);
            rv.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    }

   /* public void deletePowerPlant(final PowerPlant powerPlant) {
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
        Intent i = new Intent(this, ActivityUpdatePowerPlant.class);
        i.putExtra(ActivityAllPowerPlants.KEY, powerPlant.getId());
        startActivity(i);
    }

    public class DeletePowerPlantTask extends AsyncTask<PowerPlant, Void, Void> implements Callback<PowerPlant> {

        @Override
        protected Void doInBackground(PowerPlant... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());


            Call<PowerPlant> call = powerPlantService.deletePowerPlantById(params[0].getId());
            call.enqueue(this);

            return null;
        }

        @Override
        public void onResponse(Response<PowerPlant> response, Retrofit retrofit) {
            Intent i = new Intent(ActivityAllPowerPlants.this, ActivityAllPowerPlants.class);
            startActivity(i);
        }

        @Override
        public void onFailure(Throwable t) {
            t.fillInStackTrace();
        }
    }*/
}
