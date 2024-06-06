package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapter.CocheAdapter;
import DAO.CocheDAO;
import Model.Coche;

public class TusCochesActivity extends ToolbarActivity {

    private static final String TAG = "TusCochesActivity";
    private CocheAdapter cocheAdapter;
    private RecyclerView recyclerView;
    private CocheDAO cocheDAO;
    private SearchView searchViewTusCoches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tus_coches_layout);

        recyclerView = findViewById(R.id.recyclerView);
        searchViewTusCoches = findViewById(R.id.searchViewTusCoches);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeCocheDAO();
        setToolbarOnClicks();

        if (this.usuario != null) {
            cargarCoches();
        } else {
            Log.e(TAG, "Usuario no encontrado.");
        }

        initializeSearchView();
    }

    private void initializeCocheDAO() {
        cocheDAO = new CocheDAO(this);
    }

    private void initializeSearchView() {
        if (searchViewTusCoches != null) {
            searchViewTusCoches.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    iniciarActividadResultados(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        } else {
            Log.e(TAG, "searchViewTusCoches no encontrado en el layout.");
        }
    }

    private void cargarCoches() {
        List<Coche> coches = cocheDAO.listarCochesUsuario(this.usuario.getId());
        if (coches != null && !coches.isEmpty()) {
            cocheAdapter = new CocheAdapter(this, coches, this.usuario);
            recyclerView.setAdapter(cocheAdapter);
        } else {
            Toast.makeText(this, "No se encontraron coches.", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniciarActividadResultados(String query) {
        Intent intent = new Intent(this, ResultadosBusquedaCocheActivity.class);
        intent.putExtra("query", query);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
