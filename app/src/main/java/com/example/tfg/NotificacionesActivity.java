package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NotificacionesActivity extends ToolbarActivity {
    private static final int PERMISSIONS_REQUEST_SMS = 123;
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_RECIBIR_NOTIFICACIONES_PUSH = "recibirNotificacionesPush";
    private static final String KEY_PERMITIR_SMS = "permitirSms";

    private SharedPreferences sharedPreferences;
    private Switch switchSMS, switchNotificacionesPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificaciones_layout);
        setToolbarOnClicks();
        initSharedPreferences();
        initSwitches();
        initializeButton();
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private void initSwitches() {
        switchSMS = findViewById(R.id.switchSMS);
        switchNotificacionesPush = findViewById(R.id.switchNotificacionesPush);

        switchNotificacionesPush.setChecked(sharedPreferences.getBoolean(KEY_RECIBIR_NOTIFICACIONES_PUSH, false));
        switchSMS.setChecked(sharedPreferences.getBoolean(KEY_PERMITIR_SMS, false));

        switchNotificacionesPush.setOnCheckedChangeListener(this::toggleNotificacionesPush);
        switchSMS.setOnCheckedChangeListener(this::toggleSMS);
    }

    private void toggleNotificacionesPush(CompoundButton buttonView, boolean isChecked) {
        sharedPreferences.edit().putBoolean(KEY_RECIBIR_NOTIFICACIONES_PUSH, isChecked).apply();
        mostrarMensaje(isChecked ? "Recibiendo notificaciones push..." : "Notificaciones push deshabilitadas");
    }

    private void toggleSMS(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            solicitarPermisosSMS();
        } else {
            sharedPreferences.edit().putBoolean(KEY_PERMITIR_SMS, false).apply();
            mostrarMensaje("Recepción de SMS deshabilitada");
        }
    }

    private void solicitarPermisosSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSIONS_REQUEST_SMS);
            } else {
                sharedPreferences.edit().putBoolean(KEY_PERMITIR_SMS, true).apply();
                mostrarMensaje("Permisos para recibir SMS ya concedidos");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sharedPreferences.edit().putBoolean(KEY_PERMITIR_SMS, true).apply();
                mostrarMensaje("Permisos para recibir SMS concedidos");
            } else {
                switchSMS.setChecked(false);
                mostrarMensaje("Permisos para recibir SMS denegados");
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void initializeButton() {
        ImageButton imageButtonBack = findViewById(R.id.buttonBackNotificaciones);
        if (imageButtonBack != null) {
            imageButtonBack.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(NotificacionesActivity.this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
