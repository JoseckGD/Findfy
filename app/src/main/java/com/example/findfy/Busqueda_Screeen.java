package com.example.findfy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Busqueda_Screeen extends AppCompatActivity {

    private TextView titulo, respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_screeen);

        Intent intent = getIntent();
        String Nombre = intent.getStringExtra("Titulo");
        String Respuesta = intent.getStringExtra("Respuesta");

        titulo = findViewById(R.id.textView_busqueda);
        respuesta = findViewById(R.id.textView_Respuesta);

        titulo.setText(Nombre);
        respuesta.setText(Respuesta);

    }
}