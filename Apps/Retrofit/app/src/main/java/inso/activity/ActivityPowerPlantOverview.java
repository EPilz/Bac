package inso.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import inso.rest.ServiceGenerator;
import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import inso.rest.service.PowerPlantService;
import inso.util.UtilitiesManager;


public class ActivityPowerPlantOverview extends ActionBarActivity {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_plant_overview);

        TextView textViewName = (TextView) findViewById(R.id.textView_name);

        id = getIntent().getExtras().getInt(ActivityAllPowerPlants.KEY);
        textViewName.setText("" + id);

        LoadPowerPlantMainDataTask loadPowerPlantMainDataTask = new LoadPowerPlantMainDataTask();
        loadPowerPlantMainDataTask.execute();

        LoadPowerPlantEvaluationTask loadPowerPlantEvaluationTask = new LoadPowerPlantEvaluationTask();
        loadPowerPlantEvaluationTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_power_plant_overview, menu);
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

    private class LoadPowerPlantMainDataTask extends AsyncTask<Void, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(Void... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            return powerPlantService.getPowerPlantById(id);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            DecimalFormat df = new DecimalFormat("#.00");

            TextView textViewName = (TextView) findViewById(R.id.textView_name);
            textViewName.setText(powerPlant.getName());

            TextView textViewCommissioningDate = (TextView) findViewById(R.id.textView_commissioningDate);
            textViewCommissioningDate.setText(sdf.format(powerPlant.getCommissioningDate()));

            TextView textViewResidualTime = (TextView) findViewById(R.id.textView_residualTime);
            textViewResidualTime.setText(powerPlant.getResidualTime() + " " + getString(R.string.years));

            TextView textViewPeriodOfOverflow = (TextView) findViewById(R.id.textView_periodOfOverflow);
            textViewPeriodOfOverflow.setText(powerPlant.getPeriodOfOverflow() + " " + getString(R.string.days));

            TextView textViewBreakdownRate = (TextView) findViewById(R.id.textView_breakdownRate);
            textViewBreakdownRate.setText(df.format(powerPlant.getBreakdownRate() * 100) + " " + getString(R.string.prozent));

            TextView textViewIncOfMaintenance = (TextView) findViewById(R.id.textView_incOfMaintenance);
            textViewIncOfMaintenance.setText(df.format(powerPlant.getYearlyIncreaseOfMaintenance()) + " " + getString(R.string.prozent) + " " + getString(R.string.per_year));

            TextView textViewStorageType = (TextView) findViewById(R.id.textView_storageType);
            textViewStorageType.setText(powerPlant.getStorageType());

            TextView textViewMaintenanceStrategy = (TextView) findViewById(R.id.textView_maintenanceStrategy);
            textViewMaintenanceStrategy.setText(powerPlant.getMaintenanceStrategy());

            TextView textViewTurbineType = (TextView) findViewById(R.id.textView_turbineType);
            textViewTurbineType.setText(powerPlant.getTurbineType());
        }
    }

    private class LoadPowerPlantEvaluationTask extends AsyncTask<Void, Void, Evaluation> {

        @Override
        protected Evaluation doInBackground(Void... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            return powerPlantService.getPowerPlantEvaluation(id);
        }

        @Override
        protected void onPostExecute(Evaluation evaluation) {
            DecimalFormat df = new DecimalFormat("#.00");

            TextView textViewTurbineType = (TextView) findViewById(R.id.textView_resultEvaluation);
            textViewTurbineType.setText(df.format(evaluation.getState()));
        }
    }
}
