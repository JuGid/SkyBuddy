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
import android.widget.Toast;

import com.example.jugid.skybuddy.Fragments.ActionDiscussing;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class DiscussingRecyclerViewAdapter extends RecyclerView.Adapter<DiscussingRecyclerViewAdapter.ViewHolder>{

    private Context mContext;

    private List<Vol> liste_vols = new ArrayList<>();

    public DiscussingRecyclerViewAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recycler_discussing_element,parent,false);
        final ViewHolder vHolder = new ViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //C'est ici que l'on fait le passage des data

        holder.nomVol.setText(liste_vols.get(position).getNomVol());
        if(liste_vols.get(position).getLastMessage() == null){
            holder.lastMessage.setText("PAS DE MESSAGE");
        }
        else{
            holder.lastMessage.setText(liste_vols.get(position).getLastMessage());
        }

        holder.layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, ActionDiscussing.newInstance("Fragment","Add flight fragment",liste_vols.get(position).getIdvols()));
                Thibaut.precedent="Discussing";
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //Declare des objets

        TextView nomVol;
        TextView lastMessage;
        LinearLayout layoutClick;

        private ViewHolder(View itemView) {
            super(itemView);

            nomVol = itemView.findViewById(R.id.TV_discussingrecycler_nomVol);
            lastMessage = itemView.findViewById(R.id.TV_discussingrecycler_lastMessage);
            layoutClick = itemView.findViewById(R.id.LL_discussing_recyclerview);
        }
    }

    public void changeData(List<Vol> liste_vols){
        this.liste_vols.clear();
        this.liste_vols.addAll(liste_vols);
        this.notifyDataSetChanged();
        if(liste_vols.size() <= 0){
            Toast.makeText(mContext, "Vous ne suivez aucun vol.", Toast.LENGTH_SHORT).show();
        }
    }
}


