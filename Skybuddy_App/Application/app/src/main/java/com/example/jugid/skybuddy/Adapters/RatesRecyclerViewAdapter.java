package com.example.jugid.skybuddy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jugid.skybuddy.Objects.Mark;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.ArrayList;
import java.util.List;

public class RatesRecyclerViewAdapter extends RecyclerView.Adapter<RatesRecyclerViewAdapter.ViewHolder>{

    private Context mContext;

    private List<Mark> liste_mark = new ArrayList<>();

    public RatesRecyclerViewAdapter(Context context){
        this.mContext = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recycler_rates_element,parent,false);
        final ViewHolder vHolder = new ViewHolder(v);

        return vHolder;
    }

    public void changeData(List<Mark> liste_mark){
        this.liste_mark.clear();
        this.liste_mark.addAll(liste_mark);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //C'est ici que l'on fait le passage des data
        holder.nomNoteur.setText(liste_mark.get(position).getUser_noteur());
        holder.contenu.setText(liste_mark.get(position).getContenu());

        String noteStr = String.format("%s/5",liste_mark.get(position).getValeur());
        holder.note.setText(noteStr);

    }

    @Override
    public int getItemCount() {
        return liste_mark.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nomNoteur;
        TextView contenu;
        TextView note;

        private ViewHolder(View itemView) {
            super(itemView);

            nomNoteur = itemView.findViewById(R.id.TV_ratesfragment_nomnoteur);
            contenu = itemView.findViewById(R.id.TV_ratesfragment_contenu);
            note = itemView.findViewById(R.id.TV_ratesfragment_note);
        }
    }
}


