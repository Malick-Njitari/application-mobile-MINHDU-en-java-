package com.example.projetjavaok;

// PdfListActivity.java

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PdfListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    private List<Uri> pdfUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pdf);

        recyclerView = findViewById(R.id.recyclerView);
        pdfAdapter = new PdfAdapter((List<String>) pdfAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pdfAdapter);

        fetchPdfFiles();
    }
//j'ai toucher le code ci
    private void fetchPdfFiles() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("path/to/pdfs"); // Mettez à jour le chemin

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pdfUris.add(uri);
                            pdfAdapter.notifyDataSetChanged(); // Notifiez l'adaptateur que les données ont changé
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Gérer les erreurs et aussi les ecxception
            }
        });
    }
}