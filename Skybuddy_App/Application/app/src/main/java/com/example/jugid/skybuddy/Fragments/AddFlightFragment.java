package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFlightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFlightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFlightFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PRECEDENT = "precedent";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFlightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFlightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFlightFragment newInstance(String param1, String param2) {
        AddFlightFragment fragment = new AddFlightFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_flight, container, false);

        final NumberPicker pickerHourDepart = view.findViewById(R.id.pickerHourDepart);
        final NumberPicker pickerMinutesDepart = view.findViewById(R.id.pickerMinutesDepart);
        final NumberPicker pickerHourArrivee = view.findViewById(R.id.pickerHourArrivee);
        final NumberPicker pickerMinutesArrivee = view.findViewById(R.id.pickerMinutesArrivee);

        pickerHourDepart.setMaxValue(23);
        pickerHourDepart.setMinValue(0);

        pickerMinutesDepart.setMaxValue(59);
        pickerMinutesDepart.setMinValue(0);

        pickerHourArrivee.setMaxValue(23);
        pickerHourArrivee.setMinValue(0);

        pickerMinutesArrivee.setMaxValue(59);
        pickerMinutesArrivee.setMinValue(0);

        final EditText nomVol = view.findViewById(R.id.TV_addflight_nom);
        final EditText ville_depart = view.findViewById(R.id.TV_addflight_ville_depart);
        final EditText ville_arrivee = view.findViewById(R.id.TV_addflight_ville_arrivee);
        final EditText date_depart = view.findViewById(R.id.TV_addflight_date_depart);
        final EditText date_arrivee = view.findViewById(R.id.TV_addflight_date_arrivee);

        Button btnAddFlight = view.findViewById(R.id.createAddFlight);
        btnAddFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(response.equals("0")){
                            Toast.makeText(getContext(), "Une erreur s'est produite lors de l'ajout du Vol", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Thibaut.lastIdVolViewed = response;
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frameLayoutGeneral, DetailFlightFragment.newInstance("Fragment","Add flight fragment",Thibaut.lastIdVolViewed));
                            Thibaut.precedent="Flight";
                            transaction.commit();
                        }
                    }
                };

                String pattern = "[A-Z]{2}[1-9]{3}";

                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(nomVol.getText().toString());

                EditText[] editTexts = {nomVol,ville_depart,ville_arrivee,date_depart,date_arrivee};
                boolean verification = true;
                for(EditText edt : editTexts){
                    if(edt.getText().toString().isEmpty()){
                        verification = false;
                    }
                }

                if(m.find() && verification){
                    String dateDepart = String.format("%s %02d:%02d:000",date_depart.getText().toString().replace('/','-'),pickerHourDepart.getValue(),pickerMinutesDepart.getValue());
                    String dateArrivee = String.format("%s %02d:%02d:000",date_arrivee.getText().toString().replace('/','-'),pickerHourArrivee.getValue(),pickerMinutesArrivee.getValue());

                    Vol volToAdd = new Vol();
                    volToAdd.setNomVol(nomVol.getText().toString());
                    volToAdd.setDate_depart(dateDepart);
                    volToAdd.setDate_arrivee(dateArrivee);
                    volToAdd.setVille_arrivee(ville_arrivee.getText().toString());
                    volToAdd.setVille_depart(ville_depart.getText().toString());
                    volToAdd.setIdCreateur(Thibaut.user.getId());

                    Chloe.addVol(getContext(),volToAdd,callback);
                }
                else{
                    Toast.makeText(getContext(), "Le code de référence n'est pas bon, un champ est vide ou la date ne suit pas le bon format.", Toast.LENGTH_SHORT).show();
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
}
