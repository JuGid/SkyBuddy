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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jugid.skybuddy.Fragments.DetailFlightFragment;
import com.example.jugid.skybuddy.Fragments.UserProfileFragment;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Objects.User;

import java.util.ArrayList;
import java.util.List;

public class DetailFlightRecyclerViewAdapter extends RecyclerView.Adapter<DetailFlightRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private List<User> buddies = new ArrayList<>();

    public DetailFlightRecyclerViewAdapter(Context context){
        this.mContext = context;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.recycler_detailflight_element,parent,false);
        final ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //C'est ici que l'on fait le passage des data
        final User buddy = buddies.get(position);
        holder.buddyName.setText(buddy.getPrenom());
        holder.buddyAge.setText(String.format( "%s ans", buddy.getAge()));
        holder.buddyLastname.setText(buddy.getNomUser());
        holder.buddyRank.setText(buddy.getRang());
        holder.buddyRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TEST DETAIL FLIGHT FRAGMENT RVA",buddy.getId()+"");
                FragmentTransaction transaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, UserProfileFragment.newInstance("Fragment","User Profile Fragment",buddy.getId()));
                Thibaut.precedent="DetailFlight";
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {return buddies.size();}

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void changeData(List<User> liste_users){
        this.buddies.clear();
        this.buddies.addAll(liste_users);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView buddyName;
        TextView buddyAge;
        ImageView buddyPicture;
        ImageView buddyMoreButton;
        TextView buddyLastname;
        TextView buddyRank;
        LinearLayout buddyRV;

        private ViewHolder(View itemView) {
            super(itemView);
            buddyName = itemView.findViewById(R.id.TV_Name_BuddiesRecycler);
            buddyAge = itemView.findViewById(R.id.TV_Age_BuddiesRecycler);
            buddyPicture = itemView.findViewById(R.id.IV_BuddyPicture_BuddiesRecycler);
            buddyLastname = itemView.findViewById(R.id.TV_LastName_BuddiesRecycler);
            buddyRank =  itemView.findViewById(R.id.TV_Rank_BuddiesRecycler);
            buddyRV = itemView.findViewById(R.id.RV_Buddy);
        }
    }
}


