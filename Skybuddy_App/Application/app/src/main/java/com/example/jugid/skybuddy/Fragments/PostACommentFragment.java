package com.example.jugid.skybuddy.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Activities.EditProfileActivity;
import com.example.jugid.skybuddy.Activities.MainActivity;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostACommentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostACommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostACommentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ID_VOL = "idvol";
    private static final String ID_USER_NOTE = "idusernote";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mVol;
    private String mUserNote;



    private OnFragmentInteractionListener mListener;

    public PostACommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostACommentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostACommentFragment newInstance(String param1, String param2,String idvol, String idusernote) {
        PostACommentFragment fragment = new PostACommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ID_VOL,idvol);
        args.putString(ID_USER_NOTE,idusernote);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mVol = getArguments().getString(ID_VOL);
            mUserNote = getArguments().getString(ID_USER_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_post_a_comment, container, false);
        final EditText user_comment = (EditText) v.findViewById(R.id.comment_user);
        Button button_submit_comment = (Button) v.findViewById(R.id.submit_comment);

        final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.rating_bar_users);


        button_submit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ratingBar.getRating() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                    builder.setCancelable(true);
                    builder.setTitle("Voulez-vous lui attribuer la note de 0 ?");
                    /*builder.setMessage("Voulez-vous envoyer ce message quand même ?");*/

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            VolleyCallback callback = new VolleyCallback() {
                                @Override
                                public void onSuccess(String response) {
                                    //ICI TU MET CE QUE TU VEUX FAIRE
                                    if(response.equals("1")){
                                        //LA C'EST BON
                                        Toast.makeText(getContext(), "Commentaire envoyé", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(getContext(),"Une erreur est survenue", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            user_comment.setText(user_comment.getText());
                            String etoile = Float.toString(ratingBar.getRating());
                            Chloe.addComment(getContext(), etoile, user_comment.getText().toString(), mUserNote, mVol, callback);

                        }
                    });
                    builder.show();

                }
               else{
                    VolleyCallback callback = new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            //ICI TU MET CE QUE TU VEUX FAIRE
                            if(response.equals("1")){
                                //LA C'EST BON
                                Toast.makeText(getContext(), "Commentaire envoyé", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(getContext(),"Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    user_comment.setText(user_comment.getText());
                    String etoile = Float.toString(ratingBar.getRating());
                    Chloe.addComment(getContext(), etoile, user_comment.getText().toString(), mUserNote, mVol, callback);

                }


            }
        });

        return v;
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
