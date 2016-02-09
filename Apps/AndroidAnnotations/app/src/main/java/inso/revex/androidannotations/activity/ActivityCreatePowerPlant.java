package inso.revex.androidannotations.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import inso.revex.androidannotations.activity.util.KeyValueArrayAdapter;
import inso.revex.androidannotations.rest.model.PowerPlant;
import inso.revex.androidannotations.rest.service.PowerPlantService;

@EActivity(R.layout.activity_create_power_plant)
public class ActivityCreatePowerPlant extends Activity {

    private final String date_format = "dd.MM.yyyy";

    @RestService
    PowerPlantService powerPlantService;

    @AfterViews
    protected void init() {
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

        Spinner spinnerStorageType = (Spinner) findViewById(R.id.spinner_storageType);
        KeyValueArrayAdapter adapterStorageType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterStorageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterStorageType.setKeyValue(
                getResources().getStringArray(R.array.option_array_storageType_key),
                getResources().getStringArray(R.array.option_array_storageType_value));
        spinnerStorageType.setAdapter(adapterStorageType);

        Spinner spinnerMaintenanceStrategy = (Spinner) findViewById(R.id.spinner_maintenanceStrategy);
        KeyValueArrayAdapter adapterMaintenanceStrategy = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterMaintenanceStrategy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMaintenanceStrategy.setKeyValue(
                getResources().getStringArray(R.array.option_array_maintenanceStrategy_key),
                getResources().getStringArray(R.array.option_array_maintenanceStrategy_value));
        spinnerMaintenanceStrategy.setAdapter(adapterMaintenanceStrategy);
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
                        textViewDate.setText(String.format("%02d.%02d.%d", dayOfMonth, (monthOfYear + 1), year));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void createPowerPlant(View view) {
        TextView textViewName = (TextView) findViewById(R.id.editText_name);
        TextView textViewDate = (TextView) findViewById(R.id.editText_date);
        TextView textViewBreakdownRate = (TextView) findViewById(R.id.editText_breakdownRate);
        TextView textViewIncOfMaintenance = (TextView) findViewById(R.id.editText_incOfMaintenance);
        TextView textViewResidualTime = (TextView) findViewById(R.id.editText_residualTime);
        TextView textViewPeriodOfOverflow = (TextView) findViewById(R.id.editText_periodOfOverflow);

        if(textViewName.getText().toString().isEmpty() || textViewDate.getText().toString().isEmpty() ||
                textViewBreakdownRate.getText().toString().isEmpty() || textViewIncOfMaintenance.getText().toString().isEmpty() ||
                textViewResidualTime.getText().toString().isEmpty() || textViewPeriodOfOverflow.getText().toString().isEmpty()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCreatePowerPlant.this);
            alert.setTitle("Warning");
            alert.setMessage("Bitte alle Felder ausfüllen");
            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.show();
        } else {
            PowerPlant powerPlant = new PowerPlant();
            powerPlant.setName(textViewName.getText().toString());

            Spinner spinnerTurbineType = (Spinner) findViewById(R.id.spinnerTurbineType);
            KeyValueArrayAdapter.KeyValue keyValueTurbineType = (KeyValueArrayAdapter.KeyValue) spinnerTurbineType.getSelectedItem();
            powerPlant.setTurbineType(keyValueTurbineType.getKey());

            Spinner spinnerPressureType = (Spinner) findViewById(R.id.spinnerPressureType);
            KeyValueArrayAdapter.KeyValue keyValuePressureType = (KeyValueArrayAdapter.KeyValue) spinnerPressureType.getSelectedItem();
            powerPlant.setPressureType(keyValuePressureType.getKey());

            Spinner spinnerStorageType = (Spinner) findViewById(R.id.spinner_storageType);
            KeyValueArrayAdapter.KeyValue keyValueStorageType = (KeyValueArrayAdapter.KeyValue) spinnerStorageType.getSelectedItem();
            powerPlant.setStorageType(keyValueStorageType.getKey());

            Spinner spinnerMaintenanceStrategy = (Spinner) findViewById(R.id.spinner_maintenanceStrategy);
            KeyValueArrayAdapter.KeyValue keyValueMaintenanceStrategy = (KeyValueArrayAdapter.KeyValue) spinnerMaintenanceStrategy.getSelectedItem();
            powerPlant.setMaintenanceStrategy(keyValueMaintenanceStrategy.getKey());

            SimpleDateFormat sdf = new SimpleDateFormat(date_format);

            try {
                powerPlant.setCommissioningDate(sdf.parse(textViewDate.getText().toString()));
            } catch (ParseException e) {
                powerPlant.setCommissioningDate(new Date());
            }

            powerPlant.setBreakdownRate(Double.valueOf(textViewBreakdownRate.getText().toString()) / 100);

            powerPlant.setYearlyIncreaseOfMaintenance(Double.valueOf(textViewIncOfMaintenance.getText().toString()) / 100 + 1);

            powerPlant.setResidualTime(Integer.valueOf(textViewResidualTime.getText().toString()));

            powerPlant.setPeriodOfOverflow(Integer.valueOf(textViewPeriodOfOverflow.getText().toString()));

            CreatePowerPlantTask createPowerPlantTask = new CreatePowerPlantTask();
            createPowerPlantTask.execute(powerPlant);
        }
    }

    private class CreatePowerPlantTask extends AsyncTask<PowerPlant, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(PowerPlant... params) {
            return powerPlantService.createPowerPlant(params[0]);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityCreatePowerPlant.this);
            alert.setTitle("Create PowerPlant");
            alert.setMessage("Kraftwerk " + powerPlant.getName() + " wurde erstellt.");
            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    startActivity(new Intent(ActivityCreatePowerPlant.this, ActivityAllPowerPlants_.class));
                }
            });

            alert.show();
        }
    }
}
