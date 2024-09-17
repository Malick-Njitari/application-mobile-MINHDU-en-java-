package com.example.projetjavaok.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetjavaok.PdfAdapter;
import com.example.projetjavaok.R;
//import com.example.projetjavaok.model.PdfItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private List<String> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pdfList = new ArrayList<>();
        pdfAdapter = new PdfAdapter(pdfList);
        recyclerView.setAdapter(pdfAdapter);

        fetchPdfFiles();
    }

    private void fetchPdfFiles() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("pdfs"); // Chemin où se trouvent vos PDFs

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    pdfList.add(item.getName()); // Ajoutez le nom du fichier à la liste
                }
                pdfAdapter.notifyDataSetChanged(); // Notifiez l'adaptateur que les données ont changé
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Gérer les erreurs ici
                Toast.makeText(HomeAdminActivity.this,"Erreur",Toast.LENGTH_SHORT).show();
            }
        });
    }
}