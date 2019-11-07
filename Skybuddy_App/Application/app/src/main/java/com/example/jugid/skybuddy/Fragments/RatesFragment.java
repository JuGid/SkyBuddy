package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Adapters.FollowRecyclerViewAdapter;
import com.example.jugid.skybuddy.Adapters.RatesRecyclerViewAdapter;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Objects.Mark;
import com.example.jugid.skybuddy.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private RatesRecyclerViewAdapter recyclerViewAdapter;

    private final List<Mark> liste_marks = new ArrayList<>();

    public RatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatesFragment newInstance(String param1, String param2) {
        RatesFragment fragment = new RatesFragment();
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
        view = inflater.inflate(R.layout.fragment_rates, container, false);

        RecyclerView recyclerFlight = view.findViewById(R.id.RatesRecyclerView);
        recyclerViewAdapter = new RatesRecyclerViewAdapter(getContext());

        recyclerFlight.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFlight.setAdapter(recyclerViewAdapter);

        VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                //LE CALLBACK DE L'ENFER
                liste_marks.clear();
                List<Mark> liste = Jason.getInstance().jsonToListMark(response);
                liste_marks.addAll(liste);
                recyclerViewAdapter.changeData(liste_marks);

                //Pour l'affichage global des stats
                float moyenne=0.0f;
                for (Mark mrk : liste_marks){
                    moyenne += Integer.parseInt(mrk.getValeur());
                }
                moyenne = moyenne/liste_marks.size();

                TextView noteMoyenne = view.findViewById(R.id.TV_ratesfragment_notemoyenne);
                TextView nbAvis = view.findViewById(R.id.TV_ratesfragment_nbnotes);

                //On round la moyenne pour que ce soit plus sympa à voir
                try{
                    BigDecimal bd = new BigDecimal(moyenne);
                    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                    moyenne = bd.floatValue();
                    noteMoyenne.setText(Float.toString(moyenne));

                    String nbNoteStr = String.format("(%s avis)",Integer.toString(liste_marks.size()));
                    nbAvis.setText(nbNoteStr);
                }
                catch(Exception e){
                    Toast.makeText(getContext(), "Vous n'avez pas reçu de note pour le moment.", Toast.LENGTH_SHORT).show();
                    nbAvis.setText("(0 avis)");
                    noteMoyenne.setText("0");
                }
            }
        };

        Chloe.getMarksOfUser(getContext(),callback);
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
