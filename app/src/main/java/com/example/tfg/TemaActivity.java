package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

public class TemaActivity extends ToolbarActivity {
    private Switch switchModoOscuro;
    private static final String PREFS_NAME = "TemaPrefs";
    private SharedPreferences sharedPreferences;
    private boolean isSwitchUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        AppCompatDelegate.setDefaultNightMode(modoOscuro ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tema_layout);
        setToolbarOnClicks();
        initializeButton();

        inicializarComponentesUI();
        cargarConfiguraciones();
        configurarListeners();
    }

    private void inicializarComponentesUI() {
        switchModoOscuro = findViewById(R.id.switchTemaOscuro);
        ImageButton buttonBack = findViewById(R.id.buttonBackTema);

        buttonBack.setOnClickListener(v -> finish());
    }

    private void configurarListeners() {
        switchModoOscuro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isSwitchUpdating) {
                guardarModoOscuro(isChecked);
            }
        });
    }
    private void cargarConfiguraciones() {
        boolean modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        boolean brilloAutomatico = sharedPreferences.getBoolean("brilloAutomatico", false);

        isSwitchUpdating = true;
        switchModoOscuro.setChecked(modoOscuro);
        isSwitchUpdating = false;
    }

    private void guardarModoOscuro(boolean modoOscuro) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("modoOscuro", modoOscuro);
        editor.apply();

        AppCompatDelegate.setDefaultNightMode(modoOscuro ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private void guardarConfiguraciones() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("modoOscuro", switchModoOscuro.isChecked());
        editor.apply();
    }


    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackTema);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'buttonBackTema' no se encontró en el layout.");
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(TemaActivity.this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}