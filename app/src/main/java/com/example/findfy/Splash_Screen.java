package com.example.findfy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString("Nombre", "");

        // Ocultar la barra de acción en la pantalla de inicio
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hacer que la pantalla de inicio sea de pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Iniciar la siguiente actividad después de que transcurra 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (nombre.length() > 0) {
                    Intent intent = new Intent(Splash_Screen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(Splash_Screen.this, Registro_Screen.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}


