package inso.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import inso.rest.ServiceGenerator;
import inso.rest.model.Component;
import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import inso.rest.model.ProductionLine;
import inso.rest.service.PowerPlantService;
import inso.util.UtilitiesManager;


public class ActivityPowerPlantOverview extends Activity {

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
            LoadProductLinesTask loadProductLinesTask = new LoadProductLinesTask();
            loadProductLinesTask.execute(powerPlant);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            DecimalFormat df = new DecimalFormat("#.00");

            TextView textViewName = (TextView) findViewById(R.id.textView_name);
            textViewName.setText(powerPlant.getName() != null ? powerPlant.getName() : "-");

            TextView textViewCommissioningDate = (TextView) findViewById(R.id.textView_commissioningDate);
            textViewCommissioningDate.setText(powerPlant.getCommissioningDate() != null ? sdf.format(powerPlant.getCommissioningDate()) : "-");

            TextView textViewResidualTime = (TextView) findViewById(R.id.textView_residualTime);
            textViewResidualTime.setText(powerPlant.getResidualTime() != null ? (powerPlant.getResidualTime() + " " + getString(R.string.years)) : "-");

            TextView textViewPeriodOfOverflow = (TextView) findViewById(R.id.textView_periodOfOverflow);
            textViewPeriodOfOverflow.setText(powerPlant.getPeriodOfOverflow() != null ? (powerPlant.getPeriodOfOverflow() + " " + getString(R.string.days)) : "-");

            TextView textViewBreakdownRate = (TextView) findViewById(R.id.textView_breakdownRate);
            textViewBreakdownRate.setText(powerPlant.getBreakdownRate() != null ? (df.format(powerPlant.getBreakdownRate() * 100) + " " + getString(R.string.prozent)) : "-");

            TextView textViewIncOfMaintenance = (TextView) findViewById(R.id.textView_incOfMaintenance);
            textViewIncOfMaintenance.setText(powerPlant.getYearlyIncreaseOfMaintenance() != null ? (df.format(powerPlant.getYearlyIncreaseOfMaintenance()) + " " + getString(R.string.prozent) + " " + getString(R.string.per_year)) : "-");

            TextView textViewStorageType = (TextView) findViewById(R.id.textView_storageType);
            textViewStorageType.setText(powerPlant.getStorageType() != null ? powerPlant.getStorageType() : "-");

            TextView textViewMaintenanceStrategy = (TextView) findViewById(R.id.textView_maintenanceStrategy);
            textViewMaintenanceStrategy.setText(powerPlant.getMaintenanceStrategy() != null ? powerPlant.getMaintenanceStrategy() : "-");

            TextView textViewTurbineType = (TextView) findViewById(R.id.textView_turbineType);
            textViewTurbineType.setText(powerPlant.getTurbineType() != null ? powerPlant.getTurbineType() : "-");
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

    private class LoadProductLinesTask extends AsyncTask<PowerPlant, Void, List<ProductionLine>> {

        @Override
        protected List<ProductionLine> doInBackground(PowerPlant... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());
            PowerPlant powerPlant = params[0];

            List<ProductionLine> productionLines =  powerPlantService.getProductionLines(powerPlant.getId());

            for (ProductionLine productionLine : productionLines) {
                productionLine.setEvaluation(powerPlantService.getProductionLineEvaluation(productionLine.getId()));
                productionLine.setComponents(powerPlantService.getComponentsFromProductionLines(productionLine.getId()));

                for (Component component : productionLine.getComponents()) {
                    component.setEvaluation(powerPlantService.getComponentEvaluation(component.getId()));
                }
            }

            return productionLines;
        }

        @Override
        protected void onPostExecute(List<ProductionLine> productionLines) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutProdutionLines);

            if(productionLines.isEmpty()) {
                TableRow tr = new TableRow(ActivityPowerPlantOverview.this);
                Button b = new Button(ActivityPowerPlantOverview.this);
                b.setText(getString(R.string.not_found));
                tr.addView(b);
                tableLayout.addView(tr);
            } else {
                for (ProductionLine productionLine : productionLines) {
                    TableRow tr = new TableRow(ActivityPowerPlantOverview.this);
                    tr.setBackgroundColor(Color.DKGRAY);

                    Button b = new Button(ActivityPowerPlantOverview.this);
                    b.setBackgroundColor(Color.TRANSPARENT);
                    b.setText(productionLine.getName());
                    b.setGravity(Gravity.LEFT);
                    tr.addView(b);

                    TextView textViewEvalProduct = getTextViewWithColor(productionLine.getEvaluation());
                    textViewEvalProduct.setGravity(Gravity.RIGHT);
                    tr.addView(textViewEvalProduct);

                    tableLayout.addView(tr);

                    for (Component component : productionLine.getComponents()) {
                        tr = new TableRow(ActivityPowerPlantOverview.this);
                        Button buttonComponent = new Button(ActivityPowerPlantOverview.this);
                        buttonComponent.setBackgroundColor(Color.TRANSPARENT);
                        buttonComponent.setText(component.getName());
                        buttonComponent.setGravity(Gravity.LEFT);
                        tr.addView(buttonComponent);

                        TextView textViewEval = getTextViewWithColor(component.getEvaluation());
                        textViewEval.setGravity(Gravity.RIGHT);
                        tr.addView(textViewEval);

                        tableLayout.addView(tr);
                    }
                }
            }
        }
    }

    private TextView getTextViewWithColor(Evaluation evaluation) {
        DecimalFormat df = new DecimalFormat("#.00");

        TextView textView = new TextView(ActivityPowerPlantOverview.this);
        textView.setText(df.format(evaluation.getState()));

        if(evaluation.getState() <= 2) {
            textView.setTextColor(Color.parseColor("#188225"));
        } else if(evaluation.getState() <= 4) {
            textView.setTextColor(Color.parseColor("#FCC719"));
        } else if(evaluation.getState() <= 6) {
            textView.setTextColor(Color.parseColor("#F37F18"));
        }  else if(evaluation.getState() <= 8) {
            textView.setTextColor(Color.parseColor("#D44113"));
        } else {
            textView.setTextColor(Color.parseColor("#A11409"));
        }
        return textView;
    }
}
