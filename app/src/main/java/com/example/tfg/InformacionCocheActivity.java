package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InformacionCocheActivity extends ToolbarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_coche_layout);
        setToolbarOnClicks();
        extractIntentDataAndPopulateView();
        initializeButton();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
    }

    private void extractIntentDataAndPopulateView() {
        Intent intent = getIntent();
        if (intent != null) {
            //idCoche = intent.getIntExtra("id_coche", -1);
            updateTextView(R.id.textViewMarcaCocheText, intent.getStringExtra("marca"));
            updateTextView(R.id.textViewModeloCocheText, intent.getStringExtra("modelo"));
            updateTextView(R.id.textViewTipoCocheText, intent.getStringExtra("tipo"));
            updateTextView(R.id.textViewCaballosCocheText, intent.getStringExtra("caballos"));
            updateTextView(R.id.textViewUbicacionCocheText, intent.getStringExtra("ubicacion"));
            updateTextView(R.id.textViewPrecioCocheText, String.format("€%.2f", intent.getDoubleExtra("precio_por_dia", 0.0)));
            updateImageView(R.id.imageView, intent.getStringExtra("imagen"));
        }
    }

    private void updateTextView(int textViewId, String text) {
        TextView textView = findViewById(textViewId);
        if (textView != null) {
            textView.setText(text != null ? text : "No disponible");
        }
    }

    private void updateImageView(int resourceId, String imageName) {
        ImageView imageView = findViewById(resourceId);
        if (imageView != null && imageName != null) {
            int imageResourceId = getImageResourceId(imageName);
            imageView.setImageResource(imageResourceId);
        }
    }

    private int getImageResourceId(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            String cleanedName = imageName.replaceAll("\\.jpg|\\.png", "").replace("$", "").toLowerCase();
            int resourceId = getResources().getIdentifier(cleanedName, "drawable", getPackageName());
            return resourceId != 0 ? resourceId : R.drawable.fotoerror;
        }
        return R.drawable.fotoerror;
    }

    private void initializeButton() {
        ImageButton button = findViewById(R.id.buttonBackInformacionCoche);
        if (button != null) {
            button.setOnClickListener(v -> onBackPressed());
        } else {
            Log.e(TAG, "El botón con ID 'button' no se encontró en el layout.");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("usuario_logeado", this.usuario);
        setResult(RESULT_OK, intent);
        super.onBackPressed(); // Llama al comportamiento por defecto
    }
}