package com.example.jugid.skybuddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jugid.skybuddy.Fragments.DetailFlightFragment;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class FlightRecyclerViewAdapter extends RecyclerView.Adapter<FlightRecyclerViewAdapter.ViewHolder>{

    private Context mContext;

    private List<Vol> liste_vols = new ArrayList<>();

    public FlightRecyclerViewAdapter(Context context){
        this.mContext = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recycler_flight_element,parent,false);
        final ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //C'est ici que l'on fait le passage des data

        holder.nomVol.setText(liste_vols.get(position).getNomVol());
        holder.createur.setText(liste_vols.get(position).getNomPrenomCreateur());
        holder.villeDepart.setText(liste_vols.get(position).getVille_depart());
        holder.villeArrivee.setText(liste_vols.get(position).getVille_arrivee());
        holder.heureDepart.setText(Jason.getHours(liste_vols.get(position).getDate_depart()));
        holder.heureArrivee.setText(Jason.getHours(liste_vols.get(position).getDate_arrivee()));

        holder.nbUser.setText(liste_vols.get(position).getNbUser());

        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, DetailFlightFragment.newInstance("Fragment","Add flight fragment",liste_vols.get(position).getIdvols()));
                Thibaut.precedent="Flight";
                transaction.commit();
            }
        });
        //Ajouter la date d'arrivee et de depart
    }

    public void changeData(List<Vol> liste_vols){
        this.liste_vols.clear();
        this.liste_vols.addAll(liste_vols);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.liste_vols.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout layoutClick;
        TextView nomVol;
        TextView createur;
        TextView villeDepart;
        TextView villeArrivee;
        TextView heureDepart;
        TextView heureArrivee;
        TextView nbUser;

        private ViewHolder(View itemView) {
            super(itemView);

            layoutClick = itemView.findViewById(R.id.flightGeneralLayout);
            nomVol = itemView.findViewById(R.id.TV_flightrecycler_nom);
            createur = itemView.findViewById(R.id.TV_flightrecycler_createur);
            villeDepart= itemView.findViewById(R.id.TV_flightrecycler_villedepart);
            villeArrivee= itemView.findViewById(R.id.TV_flightrecycler_villearrivee);
            heureDepart= itemView.findViewById(R.id.TV_flightrecycler_datedepart);
            heureArrivee= itemView.findViewById(R.id.TV_flightrecycler_datearrivee);
            nbUser = itemView.findViewById(R.id.TV_flightrecycler_nbuser);
        }
    }
}


