package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class InformacionArticuloActivity extends ToolbarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_articulo_layout);
        setToolbarOnClicks();
        populateViewsFromIntent();
        initializeButton();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
    }

    private void populateViewsFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            updateTextView(R.id.textViewNombreText, intent.getStringExtra("nombre"));
            updateTextView(R.id.textViewTipoText, intent.getStringExtra("tipo"));
            updateTextView(R.id.textViewPrecioText, String.format("€%.2f", intent.getDoubleExtra("precio", 0.0)));
            updateImageView(R.id.imageView, intent.getStringExtra("imagen"));
        }
    }

    private void updateTextView(int resourceId, String text) {
        TextView textView = findViewById(resourceId);
        if (textView != null) {
            textView.setText(text != null ? text : "Información no disponible");
        }
    }

    private void updateImageView(int resourceId, String imageName) {
        ImageView imageView = findViewById(resourceId);
        if (imageView != null) {
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
        ImageButton button2 = findViewById(R.id.buttonBackInformacionArticulo);
        if (button2 != null) {
            button2.setOnClickListener(v -> openBusquedaArticuloActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void openBusquedaArticuloActivity() {
        Intent intent = new Intent(InformacionArticuloActivity.this, BusquedaArticuloActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
    public void getImagenes(){

    }
}
