package inso.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import inso.activity.util.KeyValueArrayAdapter;
import inso.rest.ServiceGenerator;
import inso.rest.model.PowerPlant;
import inso.rest.service.PowerPlantService;
import inso.util.UtilitiesManager;


public class ActivityCreatePowerPlant extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_power_plant);

        Spinner spinnerTurbineType = (Spinner) findViewById(R.id.spinnerTurbineType);
        KeyValueArrayAdapter adapterTurbineType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterTurbineType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTurbineType.setKeyValue(
                getResources().getStringArray(R.array.option_array_turbineType_key),
                getResources().getStringArray(R.array.option_array_turbineType_value));
        spinnerTurbineType.setAdapter(adapterTurbineType);

        Spinner spinnerPressureType = (Spinner) findViewById(R.id.spinnerPressureType);
        KeyValueArrayAdapter adapterPressureType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterPressureType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterPressureType.setKeyValue(
                getResources().getStringArray(R.array.option_array_pressureType_key),
                getResources().getStringArray(R.array.option_array_pressureType_value));
        spinnerPressureType.setAdapter(adapterPressureType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_power_plant, menu);
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
        TextView textViewName = (TextView) findViewById(R.id.editText_name);

        PowerPlant powerPlant = new PowerPlant();
        powerPlant.setName(textViewName.getText().toString());

        Spinner spinnerTurbineType = (Spinner) findViewById(R.id.spinnerTurbineType);
        KeyValueArrayAdapter.KeyValue keyValueTurbineType  = (KeyValueArrayAdapter.KeyValue) spinnerTurbineType.getSelectedItem();
        powerPlant.setTurbineType(keyValueTurbineType.getKey());

        Spinner spinnerPressureType = (Spinner) findViewById(R.id.spinnerPressureType);
        KeyValueArrayAdapter.KeyValue keyValuePressureType  = (KeyValueArrayAdapter.KeyValue) spinnerPressureType.getSelectedItem();
        powerPlant.setPressureType(keyValuePressureType.getKey());
        powerPlant.setCommissioningDate(new Date());
        CreatePowerPlantTask createPowerPlantTask = new CreatePowerPlantTask();
        createPowerPlantTask.execute(powerPlant);
    }

    private class CreatePowerPlantTask extends AsyncTask<PowerPlant, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(PowerPlant... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());
            return powerPlantService.createPowerPlant(params[0]);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            TextView textViewId = (TextView) findViewById(R.id.textView_createPowerPlantId);
            textViewId.setText(powerPlant.getId().toString());
        }

    }
}
