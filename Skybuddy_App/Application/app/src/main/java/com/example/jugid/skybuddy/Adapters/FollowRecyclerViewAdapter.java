package com.example.jugid.skybuddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Fragments.DetailFlightFragment;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class FollowRecyclerViewAdapter extends RecyclerView.Adapter<FollowRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<Vol> liste_vols = new ArrayList<>();

    public FollowRecyclerViewAdapter(Context context){
        this.mContext = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recycler_follow_element_list,parent,false);
        final ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //C'est ici que l'on fait le passage des data
        holder.nomVol.setText(liste_vols.get(position).getNomVol());
        holder.nbUser.setText(liste_vols.get(position).getNbUser());

        holder.heure_depart.setText(Jason.getHours(liste_vols.get(position).getDate_depart()));
        holder.heure_arrivee.setText(Jason.getHours(liste_vols.get(position).getDate_arrivee()));

        holder.ville_depart.setText(liste_vols.get(position).getVille_depart());
        holder.ville_arrivee.setText(liste_vols.get(position).getVille_arrivee());

        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, DetailFlightFragment.newInstance("Fragment","Add flight fragment",liste_vols.get(position).getIdvols()));
                Thibaut.precedent="Follow";
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return liste_vols.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void changeData(List<Vol> liste_vols){
        this.liste_vols.clear();
        this.liste_vols.addAll(liste_vols);
        this.notifyDataSetChanged();
        if(liste_vols.size() <= 0){
            Toast.makeText(mContext, "Vous ne suivez aucun vol.", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomVol;
        LinearLayout layoutClick;
        TextView ville_depart;
        TextView ville_arrivee;
        TextView heure_depart;
        TextView heure_arrivee;
        TextView nbUser;

        private ViewHolder(View itemView) {
            super(itemView);
            layoutClick = itemView.findViewById(R.id.followGeneralLayout);
            nomVol = itemView.findViewById(R.id.TV_followrecycler_nom);
            ville_depart = itemView.findViewById(R.id.TV_followrecycler_villedepart);
            ville_arrivee = itemView.findViewById(R.id.TV_followrecycler_villearrivee);
            heure_depart = itemView.findViewById(R.id.TV_followrecycler_datedepart) ;
            heure_arrivee = itemView.findViewById(R.id.TV_followrecycler_datearrivee) ;
            nbUser = itemView.findViewById(R.id.TV_followrecycler_nbuser);
        }
    }
}


