package inso.revex.androidannotations.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.rest.RestService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import inso.revex.androidannotations.activity.util.PositionStore;
import inso.revex.androidannotations.rest.model.Component;
import inso.revex.androidannotations.rest.model.Evaluation;
import inso.revex.androidannotations.rest.model.PowerPlant;
import inso.revex.androidannotations.rest.model.ProductionLine;
import inso.revex.androidannotations.rest.service.PowerPlantService;

@EActivity(R.layout.activity_power_plant_overview)
public class ActivityPowerPlantOverview extends Activity {

    private int id;

    @RestService
    PowerPlantService powerPlantService;

    @AfterViews
    protected void init() {
        TextView textViewName = (TextView) findViewById(R.id.textView_name);

        id = getIntent().getExtras().getInt(ActivityAllPowerPlants.KEY);
        textViewName.setText("" + id);

        LoadPowerPlantMainDataTask loadPowerPlantMainDataTask = new LoadPowerPlantMainDataTask();
        loadPowerPlantMainDataTask.execute();

        LoadPowerPlantEvaluationTask loadPowerPlantEvaluationTask = new LoadPowerPlantEvaluationTask();
        loadPowerPlantEvaluationTask.execute();
    }

    private class LoadPowerPlantMainDataTask extends AsyncTask<Void, Void, PowerPlant> {

        @Override
        protected PowerPlant doInBackground(Void... params) {
            return powerPlantService.getPowerPlantById(id);
        }

        @Override
        protected void onPostExecute(PowerPlant powerPlant) {
            LoadProductLinesTask loadProductLinesTask = new LoadProductLinesTask();
            loadProductLinesTask.execute(powerPlant);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("0.00");

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
            textViewIncOfMaintenance.setText(powerPlant.getYearlyIncreaseOfMaintenance() != null ? (df.format(powerPlant.getYearlyIncreaseOfMaintenance() * 100 - 100 ) + " " + getString(R.string.prozent) + " " + getString(R.string.per_year)) : "-");

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
            return powerPlantService.getPowerPlantEvaluation(id);
        }

        @Override
        protected void onPostExecute(Evaluation evaluation) {
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("0.00");

            TextView textViewTurbineType = (TextView) findViewById(R.id.textView_resultEvaluation);
            textViewTurbineType.setText(df.format(evaluation.getState()));
        }
    }

    private class LoadProductLinesTask extends AsyncTask<PowerPlant, Void, List<ProductionLine>> {

        @Override
        protected List<ProductionLine> doInBackground(PowerPlant... params) {
            PowerPlant powerPlant = params[0];

            return powerPlantService.getProductionLines(powerPlant.getId());
        }

        @Override
        protected void onPostExecute(List<ProductionLine> productionLines) {
            final TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutProdutionLines);

            if(productionLines.isEmpty()) {
                TableRow tr = new TableRow(ActivityPowerPlantOverview.this);
                Button b = new Button(ActivityPowerPlantOverview.this);
                b.setText(getString(R.string.not_found));
                tr.addView(b);
                tableLayout.addView(tr);
            } else {
                int indexProductionLine = 0;
                final PositionStore positionStore = PositionStore.getInstance();
                positionStore.clear();
                for (final ProductionLine productionLine : productionLines) {
                    final TableRow tr = new TableRow(ActivityPowerPlantOverview.this);
                    positionStore.addProductionLine(indexProductionLine);
                    tr.setBackgroundColor(Color.parseColor("#282828"));

                    Button b = new Button(ActivityPowerPlantOverview.this);
                    b.setBackgroundColor(Color.TRANSPARENT);
                    b.setText(productionLine.getName());
                    b.setTextColor(Color.parseColor("#428bca"));
                    b.setGravity(Gravity.LEFT);
                    tr.addView(b);

                    TextView textViewEvalProduct = new TextView(ActivityPowerPlantOverview.this);
                    textViewEvalProduct.setText("-");
                    textViewEvalProduct.setGravity(Gravity.RIGHT);
                    tr.addView(textViewEvalProduct);

                    tableLayout.addView(tr);

                    LoadProductionLineEvaluationTask productionLineEvaluationTask = new LoadProductionLineEvaluationTask(textViewEvalProduct);
                    productionLineEvaluationTask.execute(productionLine);

                    LoadComponentsForProductionLineTask componentsForProductionLineTask = new LoadComponentsForProductionLineTask(indexProductionLine);
                    componentsForProductionLineTask.execute(productionLine);

                    indexProductionLine++;
                }
            }
        }
    }

    private class LoadComponentsForProductionLineTask extends AsyncTask<ProductionLine, Void, List<Component>> {

        private int indexProductionLine;

        public LoadComponentsForProductionLineTask(int indexProductionLine) {
            this.indexProductionLine = indexProductionLine;
        }

        @Override
        protected List<Component> doInBackground(ProductionLine... params) {
            ProductionLine productionLine = params[0];

            return powerPlantService.getComponentsFromProductionLines(productionLine.getId());
        }

        @Override
        protected void onPostExecute(List<Component> componentList) {
            PositionStore positionStore = PositionStore.getInstance();
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayoutProdutionLines);

            int count = 1;
            positionStore.addProductionLine(indexProductionLine, componentList.size());
            for (Component component : componentList) {
                final TableRow tr2 = new TableRow(ActivityPowerPlantOverview.this);

                Button buttonComponent = new Button(ActivityPowerPlantOverview.this);
                buttonComponent.setBackgroundColor(Color.TRANSPARENT);
                buttonComponent.setText(component.getName());
                buttonComponent.setGravity(Gravity.LEFT);
                tr2.addView(buttonComponent);

                TextView textViewEval = new TextView(ActivityPowerPlantOverview.this);
                textViewEval.setText("-");
                textViewEval.setGravity(Gravity.RIGHT);
                tr2.addView(textViewEval);

                LoadComponentEvaluationTask componentEvaluationTask = new LoadComponentEvaluationTask(textViewEval);
                componentEvaluationTask.execute(component);

                tableLayout.addView(tr2, positionStore.getPosition(indexProductionLine) + count);
                count++;
            }
        }
    }

    private class LoadProductionLineEvaluationTask extends AsyncTask<ProductionLine, Void, Evaluation> {

        private TextView textView;

        public LoadProductionLineEvaluationTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected Evaluation doInBackground(ProductionLine... params) {
            ProductionLine productionLine = params[0];

            return powerPlantService.getProductionLineEvaluation(productionLine.getId());
        }

        @Override
        protected void onPostExecute(Evaluation evaluation) {
            changeTextViewColorAndSetText(textView, evaluation);
        }
    }

    private class LoadComponentEvaluationTask extends AsyncTask<Component, Void, Evaluation> {

        private TextView textView;

        public LoadComponentEvaluationTask(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected Evaluation doInBackground(Component... params) {
            Component component = params[0];

            return powerPlantService.getComponentEvaluation(component.getId());
        }

        @Override
        protected void onPostExecute(Evaluation evaluation) {
            changeTextViewColorAndSetText(textView, evaluation);
        }
    }

    private void changeTextViewColorAndSetText(TextView textView, Evaluation evaluation) {
        if(evaluation != null) {
            DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("0.00");

            textView.setText(df.format(evaluation.getState()) + "   ");

            if (evaluation.getState() <= 2) {
                textView.setTextColor(Color.parseColor("#188225"));
            } else if (evaluation.getState() <= 4) {
                textView.setTextColor(Color.parseColor("#FCC719"));
            } else if (evaluation.getState() <= 6) {
                textView.setTextColor(Color.parseColor("#F37F18"));
            } else if (evaluation.getState() <= 8) {
                textView.setTextColor(Color.parseColor("#D44113"));
            } else {
                textView.setTextColor(Color.parseColor("#A11409"));
            }
        }
    }
}
