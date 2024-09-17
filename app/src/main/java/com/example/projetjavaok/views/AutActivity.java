package com.example.projetjavaok.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projetjavaok.R;

public class AutActivity extends AppCompatActivity {

    private Button btnProIm,btnAgentIm;
    private Button btnVie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aut);

        btnVie = (Button) findViewById(R.id.btn1);
        btnProIm = (Button) findViewById(R.id.btn2);
        btnAgentIm = (Button) findViewById(R.id.btn3);

        btnAgentIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutActivity.this,AdminActivity.class );//AgentImmoActivity.class
                startActivity(intent);
            }
        });

        btnProIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AutActivity.this,ProImmoActivity.class);
                startActivity(intent2);
            }
        });

        btnVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(AutActivity.this,AgentImmoActivity.class);
                startActivity(intent3);
            }
        });

//        btnVie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent3 = new Intent(AutActivity.this,AdminActivity.class);
//                startActivity(intent3);
//            }
//        });


    }
}