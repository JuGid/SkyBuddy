package com.example.jugid.skybuddy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Skybuddy;

public class SubscribeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        final EditText username = findViewById(R.id.editTextUsername);
        final EditText password = findViewById(R.id.editTextPassword);
        final EditText nom = findViewById(R.id.editTextNom);
        final EditText prenom = findViewById(R.id.editTextPrenom);
        final EditText email = findViewById(R.id.editTextEmail);
        final EditText age = findViewById(R.id.editTextAge);

        Button btnSubscribe = findViewById(R.id.btn_subscribe);
        final Intent i = new Intent(this,StartActivity.class);

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleyCallback callback = new VolleyCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Thibaut.responseCode = response;
                        if(Thibaut.responseCode.equals("1")){
                            Toast.makeText(getApplicationContext(),"Tu es maintenant membre de Sky Buddy !",Toast.LENGTH_LONG).show();
                            i.putExtra("username",username.getText().toString());
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Ce nom d'utilisateur est déjà prit.",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                if(verifyRules()){
                    //subscribe(final Context context, final String nom, final String prenom, final String username, final String password,final String email, final String age,final VolleyCallback callback)
                    Chloe.subscribe(getApplicationContext(),nom.getText().toString(),prenom.getText().toString(),username.getText().toString(),password.getText().toString(),email.getText().toString(), age.getText().toString(),callback);
                }
            }
        });

    }

    private boolean verifyRules(){
        EditText username = findViewById(R.id.editTextUsername);
        EditText password = findViewById(R.id.editTextPassword);
        EditText nom = findViewById(R.id.editTextNom);
        EditText prenom = findViewById(R.id.editTextPrenom);
        EditText email = findViewById(R.id.editTextEmail);
        EditText verify_password = findViewById(R.id.editTextverifyPassword);
        EditText age = findViewById(R.id.editTextAge);

        EditText [] editTexts = {username,password,verify_password,email,nom,prenom,age};
        for(EditText edt : editTexts){
            edt.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }

        boolean valide = true;

        for(EditText edt : editTexts){
            if(edt.getText().toString().length() <= 0){
                edt.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
                valide = false;
            }
        }

        if(!valide){
            Toast.makeText(getApplicationContext(), "Des champs sont vides.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.getText().toString().equals(verify_password.getText().toString())){
            password.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            verify_password.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.getText().toString().length() <= 8){
            password.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Le mot de passe est trop court ( <= 8 caractères).", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.getText().toString().contains("@")){
            email.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Ce ne sont pas des emails...", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


}
