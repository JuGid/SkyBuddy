package com.example.jugid.skybuddy.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Jason;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Skybuddy;

public class StartActivity extends Activity {

    ProgressBar prgBarmember;
    LinearLayout layoutProgress;
    LinearLayout layoutSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        prgBarmember = findViewById(R.id.member_progress_bar);
        layoutProgress = findViewById(R.id.linearProgress);
        layoutSignin = findViewById(R.id.linearSignin);

        final EditText username = findViewById(R.id.editTextUsername);
        final EditText password = findViewById(R.id.editTextPassword);

        final Intent i = new Intent(this, MainActivity.class);
        Button btnConnection = findViewById(R.id.btnConnection);
        btnConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Thibaut.responseCode = response;
                        if(!Thibaut.responseCode.equals("0")){
                            Thibaut.user = Jason.jsonToUser(response);
                            layoutProgress.setVisibility(View.VISIBLE);
                            layoutSignin.setVisibility(View.GONE);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"L'utilisateur ou le mot de passe est incorrect",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Chloe.login(getApplicationContext(),username.getText().toString(),password.getText().toString(),callback);
            }
        });

        final Intent i2 = new Intent(this,SubscribeActivity.class);
        TextView btnSubscribe = findViewById(R.id.btn_subscribe);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
            }
        });

        if(getIntent().getExtras() != null){
            String str = getIntent().getExtras().getString("username");
            EditText editUsername = findViewById(R.id.editTextUsername);
            editUsername.setText(str);
        }

    }
}
