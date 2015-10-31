package inso.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import inso.activity.ActivityAllPowerPlants;
import inso.activity.ActivityPowerPlantOverview;
import inso.activity.R;
import inso.rest.model.PowerPlant;

/**
 * Created by Elisabeth on 30.06.2015.
 */
public class PowerPlantAdapter extends RecyclerView.Adapter<PowerPlantAdapter.PowerPlantViewHolder> implements  OnItemClickListener{

    private List<PowerPlant> powerPlants;
    private ActivityAllPowerPlants context;

    public PowerPlantAdapter(List<PowerPlant> powerPlants, ActivityAllPowerPlants context){
        this.powerPlants = powerPlants;
        this.context = context;
    }

    @Override
    public PowerPlantAdapter.PowerPlantViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_powerplant, viewGroup, false);
        PowerPlantViewHolder pvh = new PowerPlantViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PowerPlantAdapter.PowerPlantViewHolder holder, final int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        holder.name.setText(powerPlants.get(i).getName());

        if(powerPlants.get(i).getTurbineType() != null && !powerPlants.get(i).getTurbineType().isEmpty()) {
            holder.turbineType.setText(powerPlants.get(i).getTurbineType());
        }

        if(powerPlants.get(i).getCommissioningDate() != null) {
            holder.commissioningDate.setText(sdf.format(powerPlants.get(i).getCommissioningDate()));
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deletePowerPlant(powerPlants.get(i));
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.updatePowerPlant(powerPlants.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return powerPlants.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(View view, int position) {
        PowerPlant powerPlant = powerPlants.get(position);

        Intent i = new Intent(context, ActivityPowerPlantOverview.class);
        i.putExtra(ActivityAllPowerPlants.KEY, powerPlant.getId());
        context.startActivity(i);
    }


    public class PowerPlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView name;
        TextView commissioningDate;
        TextView turbineType;
        ImageButton edit;
        ImageButton delete;

        private PowerPlantViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv_powerPlant);
            name = (TextView)itemView.findViewById(R.id.powerPlant_name);
            commissioningDate = (TextView)itemView.findViewById(R.id.powerPlant_commissioningDate);
            turbineType = (TextView)itemView.findViewById(R.id.powerPlant_turbineType);
            edit = (ImageButton)itemView.findViewById(R.id.powerPlant_edit);
            delete = (ImageButton)itemView.findViewById(R.id.powerPlant_delete);

            cv.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onItemClick(v, getPosition());
        }
    }
}
