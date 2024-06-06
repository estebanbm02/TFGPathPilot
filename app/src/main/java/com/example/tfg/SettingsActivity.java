package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class SettingsActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        inicializarBotones();
        setToolbarOnClicks();
    }

    private void inicializarBotones() {
        findViewById(R.id.arrowButtonAcercaDe).setOnClickListener(v -> openAcercaDeActivity());
        findViewById(R.id.arrowButtonRedes).setOnClickListener(v -> openRedesActivity());
        findViewById(R.id.arrowButtonContactanos).setOnClickListener(v -> openContactanosActivity());
        findViewById(R.id.arrowButtonTema).setOnClickListener(v -> openTemaActivity());
        findViewById(R.id.arrowButtonNotificaciones).setOnClickListener(v -> openNotificacionesActivity());
        findViewById(R.id.arrowButtonGestionarUsuario).setOnClickListener(v -> openGestionarUsuarioActivity());
    }

    private void openAcercaDeActivity() {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openRedesActivity() {
        Intent intent = new Intent(this, SiguenosActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openContactanosActivity() {
        Intent intent = new Intent(this, ContactanosActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openTemaActivity() {
        Intent intent = new Intent(this, TemaActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openNotificacionesActivity() {
        Intent intent = new Intent(this, NotificacionesActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void openGestionarUsuarioActivity(){
        Intent intent = new Intent(this, GestionarUsuarioActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesion();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void cerrarSesion() {
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}