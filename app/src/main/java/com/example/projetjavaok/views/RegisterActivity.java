package com.example.projetjavaok.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetjavaok.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private TextInputLayout InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName =(TextInputLayout) findViewById(R.id.register_username_input);
        InputPassword = (TextInputLayout) findViewById(R.id.register_password_input);
        InputPhoneNumber = (TextInputLayout) findViewById(R.id.register_phone_number_input);

        loadingBar = new ProgressDialog(this);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });


    }
// pour verifier les lettres sur les mot de pass

//    private void CreateAccount(){
//        String name = InputName.getEditText().getText().toString();
//        String phone = InputPhoneNumber.getEditText().getText().toString();
//        String password = InputPassword.getEditText().getText().toString();
//
//        // Vérification des champs vides
//        if (TextUtils.isEmpty(name)) {
//            Toast.makeText(this, "S'il vous plaît écrivez votre nom...", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(phone)) {
//            Toast.makeText(this, "Veuillez écrire votre numéro de téléphone...", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Veuillez écrire votre mot de passe...", Toast.LENGTH_SHORT).show();
//        } else {
//            // Vérification que le mot de passe contient des lettres et des chiffres
//            if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*[0-9].*")) {
//                Toast.makeText(this, "Le mot de passe doit contenir des lettres et des chiffres.", Toast.LENGTH_SHORT).show();
//                return; // Arrêtez l'exécution ici si le mot de passe n'est pas valide
//            }
//
//            loadingBar.setTitle("Créer un compte");
//            loadingBar.setMessage("Veuillez patienter pendant que nous vérifions les informations d'identification.");
//            loadingBar.setCanceledOnTouchOutside(false);
//            loadingBar.show();
//
//            ValidatephoneNumber(name, phone, password);
//        }
//    }

    private void CreateAccount(){
        String name = InputName.getEditText().getText().toString();
        String phone = InputPhoneNumber.getEditText().getText().toString();
        String password = InputPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "S'il vous plaît écrivez votre nom...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Veuillez écrire votre numéro de téléphone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Veuillez écrire votre mot de passe...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Créer un compte");
            loadingBar.setMessage("Veuillez patienter pendant que nous vérifions les informations d'identification.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, phone, password);
        }

    }

    private void ValidatephoneNumber(final String name, final String phone,final String password) {
        FirebaseApp.initializeApp(this);
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "Félicitations, votre compte a été créé avec succes.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, AutActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Erreur réseau : veuillez réessayer après un certain temps...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(RegisterActivity.this, "Ce " + phone + " existe déjà.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Veuillez réessayer en utilisant un autre numéro de téléphone.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
