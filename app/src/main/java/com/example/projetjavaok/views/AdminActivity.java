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

import com.example.projetjavaok.controller.Prevalent.Prevalent;

import com.example.projetjavaok.R;
import com.example.projetjavaok.model.Users;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AdminActivity extends AppCompatActivity {

    private TextInputLayout InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;
    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    private TextView compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        LoginButton = (Button) findViewById(R.id.btnConnexion);
        InputPassword = (TextInputLayout) findViewById(R.id.editEmail);
        InputPhoneNumber = (TextInputLayout) findViewById(R.id.editMotDePasse);
        loadingBar = new ProgressDialog(this);
        compte = (TextView) findViewById(R.id.creer);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
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

                Intent julia = new Intent(AdminActivity.this,RegisterActivity.class);
                startActivity(julia);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Connexion Admin ImmoManager");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admin";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Se connecter");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }
    private void LoginUser()
    {

        String phone = InputPhoneNumber.getEditText().getText().toString();
        String password = InputPassword.getEditText().getText().toString();


        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Veuillez saisir votre numéro de téléphone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Veuillez saisir votre mot de passe...", Toast.LENGTH_SHORT).show();
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
                                    if(parentDbName.equals("Admins"))
                                    {
                                        Toast.makeText(AdminActivity.this, "Bienvenue Admin, vous êtes connecté avec succès...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AdminActivity.this,HomeAdminActivity.class);
                                        startActivity(intent);
                                    }
                                    else if (parentDbName.equals("Users")){
                                        Toast.makeText(AdminActivity.this, "Connecté avec succès...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AdminActivity.this, HomeAdminActivity.class);
                                        Prevalent.currentOnlineUser = usersData;
                                        startActivity(intent);
                                    }

                                }
                                else {
                                    loadingBar.dismiss();
                                    Toast.makeText(AdminActivity.this,"Le mot de passe est incorrect",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(AdminActivity.this, "Compte avec ça " + phone + " le numéro n'existe pas.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }


                });
    }
}
