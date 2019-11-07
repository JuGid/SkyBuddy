package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.Objects.User;
import com.example.jugid.skybuddy.R;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ID_USER = "iduser";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mUser;

    private User userCurrent = new User();

    private OnFragmentInteractionListener mListener;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2,String mUser) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ID_USER,mUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mUser = getArguments().getString(ID_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Thibaut.precedent = "DetailFlight";
        Thibaut.lastIdUserViewed = mUser;

        Button btnComment = view.findViewById(R.id.post_comment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayoutGeneral, PostACommentFragment.newInstance("Fragment","Post a comment",Thibaut.lastIdVolViewed,Thibaut.lastIdUserViewed));
                Thibaut.precedent = "UserProfile";
                transaction.commit();
            }
        });

        VolleyCallback callback = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                userCurrent = Jason.jsonToUser(response);

                TextView prenom = view.findViewById(R.id.TV_userprofile_Prenom);
                TextView nom = view.findViewById(R.id.TV_userprofile_Nom);
                TextView rang = view.findViewById(R.id.TV_userprofile_Rang);
                TextView nbVols = view.findViewById(R.id.TV_userprofile_nbFlight);
                TextView note = view.findViewById(R.id.TV_userprofile_noteMoyenne);
                TextView description = view.findViewById(R.id.TV_userprofile_Description);

                prenom.setText(userCurrent.getPrenom());
                nom.setText(userCurrent.getNomUser());

                if(userCurrent.getNbVol()== null){
                    nbVols.setText("0");
                }else{
                    nbVols.setText(userCurrent.getNbVol());
                }

                if(userCurrent.getNoteMoyenne()==null){
                    note.setText("0");
                }
                else{
                    BigDecimal bd = new BigDecimal(userCurrent.getNoteMoyenne());
                    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                    float moyenne = bd.floatValue();
                    note.setText(Float.toString(moyenne));
                }

                if(userCurrent.getDescription()==null){
                    description.setText("Vous n'avez pas de description");
                }
                else{
                    description.setText(userCurrent.getDescription());
                }

                rang.setText(userCurrent.getRang());
            }
        };
        Chloe.getUserById(getContext(),callback,mUser);
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
