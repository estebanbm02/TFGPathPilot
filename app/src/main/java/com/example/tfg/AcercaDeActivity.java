package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class AcercaDeActivity extends ToolbarActivity {
    private static final String TAG = "AcercaDeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de_layout);

        setToolbarOnClicks();
        initializeButton();
    }

    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackAcercaDe);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(AcercaDeActivity.this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

}
