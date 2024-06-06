package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapter.CocheAdapter;
import DAO.CocheDAO;
import Model.Coche;

public class ResultadosBusquedaCocheActivity extends ToolbarActivity {

    private RecyclerView recyclerView;
    private CocheAdapter adapter;
    private CocheDAO cocheDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_busqueda_coche_layout);

        cocheDAO = new CocheDAO(this);
        inicializarRecyclerView();
        initializeButton();
        setToolbarOnClicks();
    }

    private List<Coche> obtenerCoches(String marca) {
        if (marca == null || marca.isEmpty()) {
            return cocheDAO.listarTodosLosCoches();
        } else {
            return cocheDAO.buscarCochePorMarca(marca);
        }
    }

    private void inicializarRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        if (query != null) { // Verifica que el query no sea nulo
            List<Coche> coches = obtenerCoches(query);

            if (!coches.isEmpty()) {
                adapter = new CocheAdapter(this, coches, this.usuario);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No se encontraron coches.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error al obtener los datos de búsqueda.", Toast.LENGTH_SHORT).show();
        }
    }
    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackResultBusquedaCoche);
        if (button2 != null) {
            button2.setOnClickListener(v -> openTodosLosCochesActivity());
        } else {
            Log.e(TAG, "El botón con ID 'buttonBackResultBusquedaCoche' no se encontró en el layout.");
        }
    }


    private void openTodosLosCochesActivity() {
        Intent intent = new Intent(ResultadosBusquedaCocheActivity.this, TodosLosCochesActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }

}
