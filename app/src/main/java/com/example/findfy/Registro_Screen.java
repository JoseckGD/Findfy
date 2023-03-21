package com.example.findfy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.BreakIterator;
import java.util.Objects;

public class Registro_Screen extends AppCompatActivity {

    private EditText editext_nombre;

    private String nivel_educativo;
    private AdapterView<Adapter> spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_Nivel);
        editext_nombre = (EditText) findViewById(R.id.editText_Nombre);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.nivel_educativo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                nivel_educativo= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

    }
    public void Entrar (View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (editext_nombre.getText().toString().length() == 0 && Objects.equals(nivel_educativo, "Elije tu nivel educativo")){
            Snackbar.make(view, "Ingresa tu nombre y selecciona tu nivel educativo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        if (editext_nombre.getText().toString().length() == 0) {
            Snackbar.make(view, "Ingresa tu nombre", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        if (Objects.equals(nivel_educativo, "Elije tu nivel educativo")){
            Snackbar.make(view, "Selecciona tu nivel educativo", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }else {
            editor.putString("Nombre", editext_nombre.getText().toString());
            editor.putString("NivelEducativo", nivel_educativo);
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void Omitir(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "ola", Toast.LENGTH_SHORT).show();
    }
}
