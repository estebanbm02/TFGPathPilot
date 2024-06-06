package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.ArticuloAdapter;
import DAO.ArticuloDAO;
import Model.Articulo;

import java.util.List;


public class ResultadoBusquedaArticuloActivity extends ToolbarActivity {

    private RecyclerView recyclerView;
    private ArticuloAdapter adapter;
    private ArticuloDAO articuloDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_busqueda_articulo);

        articuloDAO = new ArticuloDAO(this);
        inicializarRecyclerView();
        initializeButton();
        setToolbarOnClicks();
    }

    private List<Articulo> obtenerArticulos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return articuloDAO.listarArticulos();
        } else {
            return articuloDAO.buscarArticulosPorNombre(nombre);
        }
    }
    private void inicializarRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        if (query != null) { // Verifica que el query no sea nulo
            List<Articulo> articulos = obtenerArticulos(query);

            if (!articulos.isEmpty()) {
                adapter = new ArticuloAdapter(this, articulos, this.usuario);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron artículos.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error al obtener los datos de búsqueda.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackResultBusquedaArticulo);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'buttonBackResultBusquedaArticulo' no se encontró en el layout.");
        }
    }


    private void openSettingsActivity() {
        Intent intent = new Intent(ResultadoBusquedaArticuloActivity.this, BusquedaArticuloActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
