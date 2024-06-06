package com.example.tfg;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Adapter.CocheAdapter;
import DAO.CocheDAO;
import Model.Coche;
import Model.Usuario;

public class HomeActivity extends ToolbarActivity{
    private RecyclerView recyclerView;
    private CocheAdapter adapter;
    private CocheDAO cocheDAO;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);


        initializeCocheDAO();
        setToolbarOnClicks();
        setupRecyclerView();
        setupFeatureButtons();
    }

    private void initializeCocheDAO() {
        cocheDAO = new CocheDAO(this);
    }

    private void setupFeatureButtons() {
        findViewById(R.id.imageButtonCochesRecomendados).setOnClickListener(v -> displayFeaturedCars());
        findViewById(R.id.imageButtonCochesCercanos).setOnClickListener(v -> displayCloseCars(usuario.getUbicacion()));
    }

    private void displayFeaturedCars() {
        List<Coche> coches = cocheDAO.listarCoches();
        if (coches.isEmpty()) {
            coches = new ArrayList<>();
        }
        Collections.shuffle(coches, new Random());
        updateRecyclerView(coches.subList(0, Math.min(coches.size(), 5)));
    }

    private void displayCloseCars(String ubicacion) {
        List<Coche> eventosValorados = cocheDAO.buscarCochesPorUbicacion(ubicacion);
        if (eventosValorados.isEmpty()) {
            eventosValorados = new ArrayList<>();
        }
        updateRecyclerView(eventosValorados);
    }
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewShow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CocheAdapter(this, new ArrayList<>(), this.usuario); // Se inicializa aquí el adaptador.
        recyclerView.setAdapter(adapter);
    }

    private void updateRecyclerView(List<Coche> cochesList) {
        adapter.updateData(cochesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Usuario usuario = (Usuario) data.getSerializableExtra("usuario_logeado");
            // Maneja el usuario aquí
        }
    }
}
