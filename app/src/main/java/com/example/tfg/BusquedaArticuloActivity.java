package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Adapter.ArticuloAdapter;
import Adapter.CocheAdapter;
import DAO.ArticuloDAO;
import DAO.CocheDAO;
import Model.Articulo;
import Model.Coche;
import Model.Usuario;

public class BusquedaArticuloActivity extends ToolbarActivity {
    private static final String TAG = "BusquedaArticuloActivity";
    private SearchView searchViewArticulo;
    private ArticuloAdapter articuloAdapter;
    private RecyclerView recyclerView;
    private ArticuloDAO articuloDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_articulo_activity);
        recyclerView = findViewById(R.id.recyclerView);

        initializearticuloDAO();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        cargarArticulos();

        setToolbarOnClicks();
        initializeSearchView();
        System.out.println(this.usuario);

        recyclerView.setAdapter(articuloAdapter);
    }

    private void initializearticuloDAO() {
        articuloDAO = new ArticuloDAO(this);
    }

    private void initializeSearchView() {
        searchViewArticulo = findViewById(R.id.searchViewArticulo);

        if (searchViewArticulo != null) {
            searchViewArticulo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
            Log.e(TAG, "searchViewArticulo no encontrado en el layout.");
        }
    }

    private void cargarArticulos() {
        List<Articulo> articulos = articuloDAO.listarArticulos();
        if (articulos != null && !articulos.isEmpty()) {
            articuloAdapter = new ArticuloAdapter(this, articulos, this.usuario);
            recyclerView.setAdapter(articuloAdapter);
        } else {
            Toast.makeText(this, "No se encontraron coches.", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniciarActividadResultados(String query) {
        Intent intent = new Intent(this, ResultadoBusquedaArticuloActivity.class);
        intent.putExtra("query", query);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
