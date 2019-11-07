package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jugid.skybuddy.Activities.EditProfileActivity;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                Thibaut.user = Jason.jsonToUser(response);
            }
        };

        Chloe.getUserById(getContext(),callback,Thibaut.user.getId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView prenom = view.findViewById(R.id.TV_Prenom_ProfileView);
        TextView nom = view.findViewById(R.id.TV_Nom_ProfileView);
        TextView nbVols = view.findViewById(R.id.TV_NbVols_ProfileView);
        TextView note = view.findViewById(R.id.TV_Note_ProfileView);
        TextView description = view.findViewById(R.id.TV_Description_ProfileView);
        TextView rang = view.findViewById(R.id.TV_Rang_ProfileView);

        prenom.setText(Thibaut.user.getPrenom());
        nom.setText(Thibaut.user.getNomUser());

        if(Thibaut.user.getNbVol()== null){
            nbVols.setText("0");
        }else{
            nbVols.setText(Thibaut.user.getNbVol());
        }

        if(Thibaut.user.getNoteMoyenne()==null){
            note.setText("0");
        }
        else{
            BigDecimal bd = new BigDecimal(Thibaut.user.getNoteMoyenne());
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
            float moyenne = bd.floatValue();
            note.setText(Float.toString(moyenne));
        }

        if(Thibaut.user.getDescription()==null){
            description.setText("Vous n'avez pas de description");
        }
        else{
            description.setText(Thibaut.user.getDescription());
        }

        rang.setText(Thibaut.user.getRang());


        FloatingActionButton modifierProfile = view.findViewById(R.id.modifierProfil);
        final Intent i = new Intent(getActivity(), EditProfileActivity.class);
        modifierProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        return view ;
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
}
