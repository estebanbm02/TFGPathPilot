package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import Model.Usuario;

public class ToolbarActivity extends AppCompatActivity {
    protected String query="";

    protected Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_layout);
        usuario = (Usuario) getIntent().getSerializableExtra("usuario_logeado");
        setToolbarOnClicks();
    }

    public void setToolbarOnClicks() {
        findViewById(R.id.imageButtonHome).setOnClickListener(v -> openHomeActivity());
        findViewById(R.id.imageButtonCar).setOnClickListener(v -> mostrarDialogoCoches());
        findViewById(R.id.imageButtonShop).setOnClickListener(v -> openBusquedaArticuloActivity());
        findViewById(R.id.imageButtonProfile).setOnClickListener(v -> openSettingsActivity());
        findViewById(R.id.imageButtonAñadir).setOnClickListener(v -> mostrarDialogoAnadir());
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
    private void openBusquedaArticuloActivity() {
        Intent intent = new Intent(this, BusquedaArticuloActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openTusCochesActivity() {
        Intent intent = new Intent(this, TusCochesActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openTodosLosCochesActivity() {
        Intent intent = new Intent(this, TodosLosCochesActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openMantenimientosActivity() {
        Intent intent = new Intent(this, MantenimientosActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openAlquileresActivity() {
        Intent intent = new Intent(this, AlquilerActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    public void mostrarDialogoAnadir() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Mantenimientos", "Alquileres"}, (dialog, which) -> {
                    if (which == 0) {
                        openMantenimientosActivity();
                    } else if (which == 1) {
                        openAlquileresActivity();
                    }
                })
                .show();
    }

    public void mostrarDialogoCoches() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona una opción")
                .setItems(new CharSequence[]{"Mis coches", "Todos los coches"}, (dialog, which) -> {
                    if (which == 0) {
                        openTusCochesActivity();
                    } else if (which == 1) {
                        openTodosLosCochesActivity();
                    }
                })
                .show();
    }
}