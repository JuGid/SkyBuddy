package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Activities.LocalisationActivity;
import com.example.jugid.skybuddy.Adapters.DetailFlightRecyclerViewAdapter;
import com.example.jugid.skybuddy.Adapters.FlightRecyclerViewAdapter;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Objects.User;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Joey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFlightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFlightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ID_VOL = "idvol";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mVol;

    private OnFragmentInteractionListener mListener;

    private View view;
    private DetailFlightRecyclerViewAdapter recyclerViewAdapter;

    private Vol flight = new Vol();
    private boolean isOnFlight = false;

    private TextView flightID;
    private TextView takeOffCity;
    private TextView landingCity;
    private TextView flightMonth;
    private TextView flightDay;
    private TextView takeOffTime;
    private TextView landingTime;
    private ImageView findBuddies;
    private Button joinFlight;

    public DetailFlightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFlightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFlightFragment newInstance(String param1, String param2, String idvol) {
        DetailFlightFragment fragment = new DetailFlightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ID_VOL,idvol);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mVol = getArguments().getString(ID_VOL);
        }
        Thibaut.lastIdVolViewed = mVol;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_flight, container, false);

        flightID    = view.findViewById(R.id.TV_FlightID_DetailFlight);
        takeOffCity = view.findViewById(R.id.TV_TakeOffCity_DetailFlight);
        landingCity = view.findViewById(R.id.TV_LandingCity_DetailFlight);
        flightMonth = view.findViewById(R.id.TV_FlightMonth_DetailFlight);
        flightDay   = view.findViewById(R.id.TV_FlightDay_DetailFlight);
        takeOffTime = view.findViewById(R.id.TV_TakeOffTime_DetailFlight);
        landingTime = view.findViewById(R.id.TV_LandingTime_DetailFlight);
        findBuddies = view.findViewById(R.id.IV_FindBuddies_DetailFlight);
        joinFlight  = view.findViewById(R.id.BTN_JoinFlight_DetailFlight);

        // Setting up recycler view
        RecyclerView recyclerFlight = view.findViewById(R.id.RV_Buddies_DetailFlight);
        recyclerViewAdapter = new DetailFlightRecyclerViewAdapter(getContext());
        recyclerFlight.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFlight.setAdapter(recyclerViewAdapter);

        //Lien vers la vue de localisation
        final Intent i = new Intent(getActivity(),LocalisationActivity.class);
        i.putExtra("ID_VOL",mVol);
        findBuddies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        //getting flight info
        final VolleyCallback getVolByIdCallback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                flight = Jason.getInstance().jsonToVol(response);
                isOnFlight = flight.isFollowing(Thibaut.user);
                Log.i("DetailFlightFragment", String.format("updateView: isOnFlight = %b", isOnFlight)); //TODO - Remove
                updateView();
            }
        };
        Chloe.getVolById(getContext(),getVolByIdCallback, mVol);

        // Joining/Leaving flight management
        final VolleyCallback quitterCallback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getActivity(), "Vous avez quitté ce vol",
                        Toast.LENGTH_LONG).show();
                //Updates flight datas
                Chloe.getVolById(getContext(),getVolByIdCallback, mVol);
            }
        };
        final VolleyCallback participerCallback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getActivity(), "Bienvenue à bord!",
                        Toast.LENGTH_LONG).show();
                //Updates flight datas
                Chloe.getVolById(getContext(),getVolByIdCallback, mVol);
            }
        };
        joinFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnFlight){    //Leave flight
                    Chloe.debarquer(getContext(), mVol, quitterCallback);
                }else{  //Join Flight
                    Chloe.participer(getContext(), mVol, participerCallback);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateView(){
        // Current fragment
        String depDate = flight.getDate_depart();
        String depTime = flight.getDate_depart();
        String AriTime = flight.getDate_arrivee();
        flightID.setText(flight.getNomVol());
        takeOffCity.setText(flight.getVille_depart());
        landingCity.setText(flight.getVille_arrivee());
        flightDay.setText(depDate.substring(8,10));
        flightMonth.setText(getLiteralDate(depDate));
        takeOffTime.setText(depTime.substring(11,16));
        landingTime.setText(AriTime.substring(11,16));

        if (isOnFlight){
            joinFlight.setText("Débarquer!");
            findBuddies.setVisibility(View.VISIBLE);
        }else{
            joinFlight.setText("Embarquer!");
            findBuddies.setVisibility(View.GONE);
        }

        // Recycler view
        recyclerViewAdapter.changeData(flight.getListe_user());
    }

    public String getLiteralDate(String date){
        switch(date.substring(5,7)){
            case "01":
                return "JAN";
            case "02":
                return "FEV";
            case "03":
                return "MAR";
            case "04":
                return "AVR";
            case "05":
                return "MAI";
            case "06":
                return "JUIN";
            case "07":
                return "JUIL";
            case "08":
                return "AOU";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
            default:
                return "NaD";
        }
    }
}
