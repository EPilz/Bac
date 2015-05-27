package inso.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import inso.activity.util.KeyValueArrayAdapter;
import inso.rest.ServiceGenerator;
import inso.rest.model.PowerPlant;
import inso.rest.service.PowerPlantService;
import inso.util.UtilitiesManager;


public class ActivityCreatePowerPlant extends Activity {

    private final String date_format = "dd.MM.yyyy";

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

    public void openDatePicker(View view) {
        final TextView textViewDate = (TextView) findViewById(R.id.editText_date);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        textViewDate.setText(String.format("%02d.%02d.%d", dayOfMonth, (monthOfYear+1), year));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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

        SimpleDateFormat sdf = new SimpleDateFormat(date_format);
        TextView textViewDate = (TextView) findViewById(R.id.editText_date);
        try {
            powerPlant.setCommissioningDate(sdf.parse(textViewDate.getText().toString()));
        } catch (ParseException e) {
            powerPlant.setCommissioningDate(new Date());
        }
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
