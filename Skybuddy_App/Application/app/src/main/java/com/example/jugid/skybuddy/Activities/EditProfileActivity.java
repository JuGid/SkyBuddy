package com.example.jugid.skybuddy.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;

import java.io.FileNotFoundException;

public class EditProfileActivity extends AppCompatActivity {

    Button pictureEdit, editProfile;
    ImageView profilePicture;
    EditText editPassword, editPasswordCheck, editEmail, editEmailCheck, editDescription;
    TextView actualMail, actualDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        actualMail = findViewById(R.id.editTextActualEmail);
        actualDescription = findViewById(R.id.editTextActualDescription);

        pictureEdit = findViewById(R.id.ButtonEditPicture);
        editProfile = findViewById(R.id.ButtonEdit);
        editDescription = findViewById(R.id.editTextEditDescription);
        profilePicture = findViewById(R.id.ImageViewProfilePicture);
        editPassword = findViewById(R.id.editTextEditPassword);
        editPasswordCheck = findViewById(R.id.editTextEditPasswordCheck);
        editEmail = findViewById(R.id.editTextEditEmail);
        editEmailCheck = findViewById(R.id.editTextEditEmailCheck);

        editPassword.setLongClickable(false);
        editPasswordCheck.setLongClickable(false);

        actualMail.setText(Thibaut.user.getEmail());
        actualDescription.setText(Thibaut.user.getDescription());

        pictureEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Allo",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,0);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mail, password, description;

                if(verifyRules()){
                    //TODO
                    if(editEmail.length()>0){
                        mail = editEmail.getText().toString();
                    }else{
                        mail = Thibaut.user.getEmail();
                    }

                    if(editPassword.length()>0){
                        password = editPassword.getText().toString();
                    }else{
                        password = Thibaut.user.getPassword();
                    }

                    if(editDescription.length()>0){
                        description = editDescription.getText().toString();
                    }else{
                        description = Thibaut.user.getDescription();
                    }

                    VolleyCallback callback = new VolleyCallback() {
                        @Override
                        public void onSuccess(String response) {
                            //ICI TU MET CE QUE TU VEUX FAIRE
                            if(response.equals("1")){
                                //LA C'EST BON
                                Toast.makeText(getApplicationContext(), "Modifications OK", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Une erreur est survenue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    Chloe.updateUser(getApplicationContext(),password,mail,description,callback);
                }
            }
        });
    }

    private boolean verifyRules(){
        EditText password, passwordCheck, email, emailCheck;
        password = findViewById(R.id.editTextEditPassword);
        passwordCheck = findViewById(R.id.editTextEditPasswordCheck);
        email = findViewById(R.id.editTextEditEmail);
        emailCheck = findViewById(R.id.editTextEditEmailCheck);

        if(!password.getText().toString().equals(passwordCheck.getText().toString())){
            password.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            passwordCheck.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!email.getText().toString().equals(emailCheck.getText().toString())){
            email.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            emailCheck.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Les adresses email ne correspondent pas...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.getText().toString().length() > 0 && password.getText().toString().length() <= 8){
            password.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            passwordCheck.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Le mot de passe est trop court...", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(email.getText().toString().length()>0 && !email.getText().toString().contains("@")){
            email.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            emailCheck.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_subscribe_error));
            Toast.makeText(getApplicationContext(), "Ce ne sont pas des emails...", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try{
                bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                profilePicture.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
    }
}
