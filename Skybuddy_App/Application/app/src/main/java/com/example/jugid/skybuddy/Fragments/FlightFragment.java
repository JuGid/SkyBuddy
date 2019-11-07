package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jugid.skybuddy.Adapters.FlightRecyclerViewAdapter;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FlightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlightFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "titre";

    private String type;
    private String titre;

    private OnFragmentInteractionListener mListener;

    private View view;
    private FlightRecyclerViewAdapter recyclerViewAdapter;

    private List<Vol> liste_vols = new ArrayList<>();

    public FlightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type Parameter 1.
     * @param titre Parameter 2.
     * @return A new instance of fragment FlightFragment.
     */
    public static FlightFragment newInstance(String type, String titre) {
        FlightFragment fragment = new FlightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        args.putString(ARG_PARAM2, titre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
            titre = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_flight, container, false);
        RecyclerView recyclerFlight = view.findViewById(R.id.FlightRecyclerView);
        recyclerViewAdapter = new FlightRecyclerViewAdapter(getContext());

        recyclerFlight.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFlight.setAdapter(recyclerViewAdapter);

        FloatingActionButton addFlight = view.findViewById(R.id.floatingButtonAddFlight);
        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, AddFlightFragment.newInstance("Fragment","Add flight fragment"));
                Thibaut.precedent = "Flight";
                transaction.commit();
            }
        });

        final VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                List<Vol> liste = Jason.getInstance().jsonToListVol(response);
                liste_vols.clear();
                liste_vols.addAll(liste);
                recyclerViewAdapter.changeData(liste_vols);
            }
        };

        Chloe.getAllVols(getContext(),callback);

        final EditText rechercher = view.findViewById(R.id.TV_flightfragment_rechercher);
        rechercher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        liste_vols.clear();
                        List<Vol> liste = Jason.getInstance().jsonToListVol(response);
                        liste_vols.addAll(liste);
                        recyclerViewAdapter.changeData(liste_vols);
                    }
                };

                //Je suis obligé de faire comme ça sinon Nodejs fait n'importe quoi !
                //A voir avec le prof !
                if(TextUtils.isEmpty(rechercher.getText().toString())){
                    Chloe.getAllVols(getContext(),callback);
                }
                else{
                    Chloe.getVolsByLike(getContext(),callback,rechercher.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view ;
    }

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
}
