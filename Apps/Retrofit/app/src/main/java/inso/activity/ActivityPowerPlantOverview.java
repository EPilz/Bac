package inso.activity;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import inso.activity.util.CallWithData;
import inso.activity.util.PositionStore;
import inso.rest.ServiceGenerator;
import inso.rest.model.Component;
import inso.rest.model.Evaluation;
import inso.rest.model.PowerPlant;
import inso.rest.model.ProductionLine;
import inso.rest.service.PowerPlantService;
import inso.util.UtilitiesManager;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


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

    private class LoadPowerPlantMainDataTask extends AsyncTask<Void, Void, Void> implements Callback<PowerPlant> {

        @Override
        protected Void doInBackground(Void... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            Call<PowerPlant> call = powerPlantService.getPowerPlantById(id);
            call.enqueue(this);

            return null;
        }

        @Override
        public void onResponse(Response<PowerPlant> response, Retrofit retrofit) {
            PowerPlant powerPlant = response.body();
            LoadProductLinesTask loadProductLinesTask = new LoadProductLinesTask();
            loadProductLinesTask.execute(powerPlant);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
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

        @Override
        public void onFailure(Throwable t) {
            t.fillInStackTrace();
        }
    }

    private class LoadPowerPlantEvaluationTask extends AsyncTask<Void, Void, Void> implements Callback<Evaluation>{

        @Override
        protected Void doInBackground(Void... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            Call<Evaluation> call =  powerPlantService.getPowerPlantEvaluation(id);
            call.enqueue(this);

            return null;
        }

        @Override
        public void onResponse(Response<Evaluation> response, Retrofit retrofit) {
            Evaluation evaluation = response.body();

            DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
            df.applyPattern("0.00");

            TextView textViewTurbineType = (TextView) findViewById(R.id.textView_resultEvaluation);
            textViewTurbineType.setText(df.format(evaluation.getState()));
        }

        @Override
        public void onFailure(Throwable t) {
            t.fillInStackTrace();
        }
    }

    private class LoadProductLinesTask extends AsyncTask<PowerPlant, Void, Void> implements Callback<List<ProductionLine>>{

        @Override
        protected Void doInBackground(PowerPlant... params) {
            PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());
            PowerPlant powerPlant = params[0];

            Call<List<ProductionLine>> call = powerPlantService.getProductionLines(powerPlant.getId());
            call.enqueue(this);

            return null;
        }

        @Override
        public void onResponse(Response<List<ProductionLine>> response, Retrofit retrofit) {
            final PowerPlantService powerPlantService = ServiceGenerator.
                    createServiceWithAuthToken(PowerPlantService.class, UtilitiesManager.getInstance().getAuthToken());

            List<ProductionLine> productionLines = response.body();

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

                    Call<Evaluation> callEvaluation = powerPlantService.getProductionLineEvaluation(productionLine.getId());
                    callEvaluation.enqueue(new CallWithData<Evaluation>(textViewEvalProduct) {
                        @Override
                        public void onResponse(Response<Evaluation> response, Retrofit retrofit) {
                            TextView textViewEvalProduct = (TextView) getData();
                            changeTextViewColorAndSetText(textViewEvalProduct, response.body());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.fillInStackTrace();
                        }
                    });

                    Call<List<Component>> callComponents = powerPlantService.getComponentsFromProductionLines(productionLine.getId());

                    callComponents.enqueue(new CallWithData<List<Component>>(indexProductionLine) {
                        @Override
                        public void onResponse(Response<List<Component>> response, Retrofit retrofit) {
                            int indexProductionLine = (Integer) getData();
                            List<Component> componentList = response.body();
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

                                Call<Evaluation> callComponentEvaluation = powerPlantService.getComponentEvaluation(component.getId());
                                callComponentEvaluation.enqueue(new CallWithData<Evaluation>(textViewEval) {

                                    @Override
                                    public void onResponse(Response<Evaluation> response, Retrofit retrofit) {
                                        TextView textViewEval = (TextView) getData();
                                        changeTextViewColorAndSetText(textViewEval, response.body());
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        t.fillInStackTrace();
                                    }
                                });

                                tableLayout.addView(tr2, positionStore.getPosition(indexProductionLine) + count);
                                //positionStore.addCountOnIndex(indexProductionLine);
                                count++;
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            t.fillInStackTrace();
                        }
                    });
                    indexProductionLine++;
                }
            }
        }


        @Override
        public void onFailure(Throwable t) {
            t.fillInStackTrace();
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
