package com.example.projetjavaok.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetjavaok.R;
import com.example.projetjavaok.controller.Prevalent.Prevalent;
import com.example.projetjavaok.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AgentImmoActivity extends AppCompatActivity {

    private TextInputLayout InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    private TextView compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agent_immo);

        LoginButton = (Button) findViewById(R.id.login_btn1);
        InputPassword = (TextInputLayout) findViewById(R.id.login_password_input1);
        InputPhoneNumber = (TextInputLayout) findViewById(R.id.login_phone_number_input1);

        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb1);
        compte = (TextView) findViewById(R.id.compte1);
        Paper.init(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent julia = new Intent(AgentImmoActivity.this,RegisterActivity.class);
                startActivity(julia);
            }
        });



    }


//    private void LoginUser()
//    {
//        String phone = InputPhoneNumber.getEditText().getText().toString();
//        String password = InputPassword.getEditText().getText().toString();
//
//        if (TextUtils.isEmpty(phone))
//        {
//            Toast.makeText(this, "Veuillez écrire votre numéro de téléphone...", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(password))
//        {
//            Toast.makeText(this, "Veuillez écrire votre mot de passe...", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            // Vérifie si le mot de passe contient des chiffres et des lettres
//            if (password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*"))
//            {
//                // Le mot de passe contient des chiffres et des lettres
//                loadingBar.setTitle("Compte de connexion");
//                loadingBar.setMessage("Veuillez patienter pendant que nous vérifions les informations d'identification.");
//                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
//
//                AllowAccessToAccount(phone, password);
//            }
//            else
//            {
//                // Le mot de passe ne contient pas des chiffres et des lettres
//                Toast.makeText(this, "Le mot de passe doit contenir au moins un caractère alphanumérique.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    private void LoginUser()
    {
        String phone = InputPhoneNumber.getEditText().getText().toString();
        String password = InputPassword.getEditText().getText().toString();


        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Veuillez écrire votre numéro de téléphone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Veuillez écrire votre mot de passe...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Compte de connexion");
            loadingBar.setMessage("Veuillez patienter pendant que nous vérifions les informations d'identification.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(phone, password);
        }
    }


    private void AllowAccessToAccount(final String phone, final String password)
    {

        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        FirebaseApp.initializeApp(this);
        final DatabaseReference RootRef;

        FirebaseDatabase.getInstance().getReference().orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onCancelled(DatabaseError error) {

                    }

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(parentDbName).child(phone).exists()){

                            Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                            if (usersData.getPhone().equals(phone))
                            {
                                if (usersData.getPassword().equals(password))
                                {
                                    if(parentDbName.equals("Admin"))
                                    {
                                        Toast.makeText(AgentImmoActivity.this, "Bienvenue Admin, vous êtes connecté avec succès...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AgentImmoActivity.this,HomeAdminActivity.class);
                                        startActivity(intent);
                                    }
                                    else if (parentDbName.equals("Users")){
                                        Toast.makeText(AgentImmoActivity.this, "Connecté avec succès...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AgentImmoActivity.this, HomeActivity.class);
                                        Prevalent.currentOnlineUser = usersData;
                                        startActivity(intent);
                                    }

                                }
                                else {
                                    loadingBar.dismiss();
                                    Toast.makeText(AgentImmoActivity.this,"Le mot de passe est incorrect veuillez ressayer svp",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(AgentImmoActivity.this, "Compte avec ça " + phone + " le numéro n'existe pas.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }


                });
    }
}
