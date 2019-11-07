package com.example.jugid.skybuddy.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Adapters.MessageRecyclerViewAdapter;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Objects.Message;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Service.DiscussingActualizerService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActionDiscussing.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionDiscussing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionDiscussing extends Fragment {
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

    private List<Message> messageList = new ArrayList<>();

    private MessageRecyclerViewAdapter mMessageAdapter;
    private RecyclerView mMessageRecycler;

    private VolleyCallback callback = new VolleyCallback() {
        @Override
        public void onSuccess(String response) {
                List<Message> liste_messages = Jason.getInstance().jsonToListMessages(response);
                messageList.clear();
                messageList.addAll(liste_messages);
                mMessageAdapter.changeData(liste_messages);
                mMessageRecycler.scrollToPosition(liste_messages.size() - 1);
        }
    };

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Chloe.getAllMessagesByVolId(getContext(),mVol,callback);
            Toast.makeText(context, "Mise à jour des messages", Toast.LENGTH_SHORT).show();
        }
    };

    public ActionDiscussing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActionDiscussing.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionDiscussing newInstance(String param1, String param2, String idvol) {
        ActionDiscussing fragment = new ActionDiscussing();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mVol = getArguments().getString(ID_VOL);
        }

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("actualize-message-vol"));
        getActivity().startService( new Intent(getContext(),DiscussingActualizerService.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_action_discussing, container, false);

        mMessageRecycler = view.findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new MessageRecyclerViewAdapter(getContext(), messageList);

        LinearLayoutManager mMessageLayoutManager = new LinearLayoutManager(getContext());
        mMessageLayoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageRecycler.setAdapter(mMessageAdapter);

        final TextView TV_contenu = view.findViewById(R.id.edittext_chatbox);

        final VolleyCallback callbackOnclick = new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                if(response.equals("0")){
                    Toast.makeText(getContext(), "Une erreur est survenue lors de l'envoi du message.", Toast.LENGTH_SHORT).show();
                }
                else{
                    TV_contenu.setText("");
                    Chloe.getAllMessagesByVolId(getContext(),mVol,callback);
                }
            }
        };

        ImageView sendButton = view.findViewById(R.id.IV_actionDiscussing_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new java.util.Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(dt);
                Chloe.addMessage(getContext(),mVol,TV_contenu.getText().toString(),currentTime,callbackOnclick);
            }
        });

        ImageView reloadButton = view.findViewById(R.id.IV_actionDiscussing_reload);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chloe.getAllMessagesByVolId(getContext(),mVol,callback);
            }
        });
        
        Chloe.getAllMessagesByVolId(getContext(),mVol,callback);


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
        getActivity().stopService(new Intent(getContext(), DiscussingActualizerService.class));
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
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
