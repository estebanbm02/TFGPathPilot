package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class SiguenosActivity extends ToolbarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siguenos_layout);

        setToolbarOnClicks();
        initializeButton();

        // Botón para volver a la pantalla de configuraciones
        ImageButton buttonBack = findViewById(R.id.buttonBackRedes);
        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> startActivity(new Intent(SiguenosActivity.this, SettingsActivity.class)));
        } else {
            Log.e("SiguenosActivity", "buttonBack no encontrado.");
        }

        // Botón para seguir en Twitter
        Button openTwitterBtn = findViewById(R.id.buttonSeguir);
        if (openTwitterBtn != null) {
            openTwitterBtn.setOnClickListener(view -> {
                String url = "https://x.com/PathPilotTFG";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            });
        } else {
            Log.e("SiguenosActivity", "openTwitterBtn no encontrado.");
        }
    }

    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackRedes);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(SiguenosActivity.this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
