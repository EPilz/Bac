package inso.revex.androidannotations.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.rest.spring.annotations.RestService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import inso.revex.androidannotations.activity.util.KeyValueArrayAdapter;
import inso.revex.androidannotations.rest.model.PowerPlant;
import inso.revex.androidannotations.rest.service.PowerPlantService;

@EActivity(R.layout.activity_update_power_plant)
public class ActivityUpdatePowerPlant extends Activity {

    private final String date_format = "dd.MM.yyyy";
    private int id;

    @RestService
    PowerPlantService powerPlantService;

    @AfterViews
    protected void init() {
        id = getIntent().getExtras().getInt(ActivityAllPowerPlants.KEY);

        Spinner spinnerTurbineType = (Spinner) findViewById(R.id.update_spinnerTurbineType);
        KeyValueArrayAdapter adapterTurbineType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterTurbineType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTurbineType.setKeyValue(
                getResources().getStringArray(R.array.option_array_turbineType_key),
                getResources().getStringArray(R.array.option_array_turbineType_value));
        spinnerTurbineType.setAdapter(adapterTurbineType);

        Spinner spinnerPressureType = (Spinner) findViewById(R.id.update_spinnerPressureType);
        KeyValueArrayAdapter adapterPressureType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterPressureType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterPressureType.setKeyValue(
                getResources().getStringArray(R.array.option_array_pressureType_key),
                getResources().getStringArray(R.array.option_array_pressureType_value));
        spinnerPressureType.setAdapter(adapterPressureType);

        Spinner spinnerStorageType = (Spinner) findViewById(R.id.update_spinnerStorageType);
        KeyValueArrayAdapter adapterStorageType = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterStorageType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterStorageType.setKeyValue(
                getResources().getStringArray(R.array.option_array_storageType_key),
                getResources().getStringArray(R.array.option_array_storageType_value));
        spinnerStorageType.setAdapter(adapterStorageType);

        Spinner spinnerMaintenanceStrategy = (Spinner) findViewById(R.id.update_spinnerMaintenanceStrategy);
        KeyValueArrayAdapter adapterMaintenanceStrategy = new KeyValueArrayAdapter(this, android.R.layout.simple_spinner_item);
        adapterMaintenanceStrategy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMaintenanceStrategy.setKeyValue(
                getResources().getStringArray(R.array.option_array_maintenanceStrategy_key),
                getResources().getStringArray(R.array.option_array_maintenanceStrategy_value));
        spinnerMaintenanceStrategy.setAdapter(adapterMaintenanceStrategy);

        LoadPowerPlantDataTask loadPowerPlantDataTask = new LoadPowerPlantDataTask();
        loadPowerPlantDataTask.execute();
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
        final TextView textViewDate = (TextView) findViewById(R.id.updateText_date);
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

    public void updatePowerPlant(View view) {
        TextView textViewName = (TextView) findViewById(R.id.updateText_name);
        TextView textViewDate = (TextView) findViewById(R.id.updateText_date);
        TextView textViewBreakdownRate = (TextView) findViewById(R.id.updateText_breakdownRate);
        TextView textViewIncOfMaintenance = (TextView) findViewById(R.id.updateText_incOfMaintenance);
        TextView textViewResidualTime = (TextView) findViewById(R.id.updateText_residualTime);
        TextView textViewPeriodOfOverflow = (TextView) findViewById(R.id.updateText_periodOfOverflow);

        if(textViewName.getText().toString().isEmpty() || textViewDate.getText().toString().isEmpty() ||
                textViewBreakdownRate.getText().toString().isEmpty() || textViewIncOfMaintenance.getText().toString().isEmpty() ||
                textViewResidualTime.getText().toString().isEmpty() || textViewPeriodOfOverflow.getText().toString().isEmpty()) {

            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityUpdatePowerPlant.this);
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
            powerPlant.setId(id);

            powerPlant.setName(textViewName.getText().toString());

            Spinner spinnerTurbineType = (Spinner) findViewById(R.id.update_spinnerTurbineType);
            KeyValueArrayAdapter.KeyValue keyValueTurbineType = (KeyValueArrayAdapter.KeyValue) spinnerTurbineType.getSelectedItem();
            powerPlant.setTurbineType(keyValueTurbineType.getKey());

            Spinner spinnerPressureType = (Spinner) findViewById(R.id.update_spinnerPressureType);
            KeyValueArrayAdapter.KeyValue keyValuePressureType = (KeyValueArrayAdapter.KeyValue) spinnerPressureType.getSelectedItem();
            powerPlant.setPressureType(keyValuePressureType.getKey());

            Spinner spinnerStorageType = (Spinner) findViewById(R.id.update_spinnerStorageType);
            KeyValueArrayAdapter.KeyValue keyValueStorageType = (KeyValueArrayAdapter.KeyValue) spinnerStorageType.getSelectedItem();
            powerPlant.setStorageType(keyValueStorageType.getKey());

            Spinner spinnerMaintenanceStrategy = (Spinner) findViewById(R.id.update_spinnerMaintenanceStrategy);
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

            UpdatePowerPlantTask updatePowerPlantTask = new UpdatePowerPlantTask();
            updatePowerPlantTask.execute(powerPlant);
        }
    }

    private class UpdatePowerPlantTask extends AsyncTask<PowerPlant, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(PowerPlant... params) {
            return powerPlantService.updatePowerPlant(id, params[0]);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            AlertDialog.Builder alert = new AlertDialog.Builder(ActivityUpdatePowerPlant.this);
            alert.setTitle("Update PowerPlant");
            alert.setMessage("Kraftwerk " + powerPlant.getName() + " wurde erfolgreich bearbeitet.");
            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    startActivity(new Intent(ActivityUpdatePowerPlant.this, ActivityAllPowerPlants_.class));
                }
            });
            alert.show();
        }
    }


    private class LoadPowerPlantDataTask extends AsyncTask<Void, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(Void... params) {
            return powerPlantService.getPowerPlantById(id);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("0.00");

            TextView textViewName = (TextView) findViewById(R.id.updateText_name);
            textViewName.setText(powerPlant.getName() != null ? powerPlant.getName() : "-");

            TextView textViewCommissioningDate = (TextView) findViewById(R.id.updateText_date);
            textViewCommissioningDate.setText(powerPlant.getCommissioningDate() != null ? sdf.format(powerPlant.getCommissioningDate()) : "");

            TextView textViewResidualTime = (TextView) findViewById(R.id.updateText_residualTime);
            textViewResidualTime.setText(powerPlant.getResidualTime() != null ? powerPlant.getResidualTime().toString() : "");

            TextView textViewPeriodOfOverflow = (TextView) findViewById(R.id.updateText_periodOfOverflow);
            textViewPeriodOfOverflow.setText(powerPlant.getPeriodOfOverflow() != null ? powerPlant.getPeriodOfOverflow().toString()  : "");

            TextView textViewBreakdownRate = (TextView) findViewById(R.id.updateText_breakdownRate);
            textViewBreakdownRate.setText(powerPlant.getBreakdownRate() != null ? (df.format(powerPlant.getBreakdownRate() * 100)) : "");

            TextView textViewIncOfMaintenance = (TextView) findViewById(R.id.updateText_incOfMaintenance);
            textViewIncOfMaintenance.setText(powerPlant.getYearlyIncreaseOfMaintenance() != null ? (df.format(powerPlant.getYearlyIncreaseOfMaintenance() * 100 - 100)) : "");

            Spinner spinnerTurbineType = (Spinner) findViewById(R.id.update_spinnerTurbineType);
            KeyValueArrayAdapter adapterTurbineType = (KeyValueArrayAdapter) spinnerTurbineType.getAdapter();
            spinnerTurbineType.setSelection(adapterTurbineType.getPosition(powerPlant.getTurbineType()));

            Spinner spinnerPressureType = (Spinner) findViewById(R.id.update_spinnerPressureType);
            KeyValueArrayAdapter adapterPressureType = (KeyValueArrayAdapter) spinnerPressureType.getAdapter();
            spinnerPressureType.setSelection(adapterPressureType.getPosition(powerPlant.getPressureType()));

            Spinner spinnerStorageType = (Spinner) findViewById(R.id.update_spinnerStorageType);
            KeyValueArrayAdapter adapterStorageType = (KeyValueArrayAdapter) spinnerStorageType.getAdapter();
            spinnerStorageType.setSelection(adapterStorageType.getPosition(powerPlant.getStorageType()));

            Spinner spinnerMaintenanceStrategy = (Spinner) findViewById(R.id.update_spinnerMaintenanceStrategy);
            KeyValueArrayAdapter adapterMaintenanceStrategy = (KeyValueArrayAdapter) spinnerMaintenanceStrategy.getAdapter();
            spinnerMaintenanceStrategy.setSelection(adapterMaintenanceStrategy.getPosition(powerPlant.getMaintenanceStrategy()));
        }
    }
}
