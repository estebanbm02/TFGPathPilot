package com.example.tfg;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapter.CocheAdapter;
import DAO.CocheDAO;
import DAO.MantenimientoDAO;
import DAO.PrestamoDAO;
import Model.Coche;
import Model.Mantenimiento;
import Model.Prestamo;

public class AlquilerActivity extends ToolbarActivity {
    private static final String TAG = "MantenimientosActivity";
    private CocheAdapter cocheAdapter;
    private CocheDAO cocheDAO;
    private PrestamoDAO prestamoDAO;
    private Prestamo prestamo;
    private TextInputEditText textInputEditTextFechaMantenimiento;
    private AutoCompleteTextView autoCompleteTextViewCoche;
    private ArrayList<String> coches;
    private Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alquiler_layout);

        coches = new ArrayList<>();

        cocheDAO = new CocheDAO(this);
        prestamoDAO = new PrestamoDAO(this);

        autoCompleteTextViewCoche = findViewById(R.id.textInputEditTextCoche);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        cargarCoches();

        setToolbarOnClicks();

        textInputEditTextFechaMantenimiento = findViewById(R.id.textInputEditTextFechaMantenimiento);

        textInputEditTextFechaMantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, coches);
        autoCompleteTextViewCoche.setAdapter(adapter);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAlquiler();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format("%04d-%02d-%02d", year, (monthOfYear + 1), dayOfMonth);
                        textInputEditTextFechaMantenimiento.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private ArrayList<String> cargarCoches() {
        System.out.println(this.usuario.getId());
        List<Coche> listaCoches = cocheDAO.listarCochesOtros(this.usuario.getId());
        if (listaCoches != null && !listaCoches.isEmpty()) {
            for(Coche coche : listaCoches){
                coches.add(coche.getId()+ ", " + coche.getMarca() + ", " + coche.getModelo());
            }
        } else {
            Toast.makeText(this, "No se encontraron coches.", Toast.LENGTH_SHORT).show();
        }
        return coches;
    }

    private void guardarAlquiler() {
        String cocheSeleccionado = autoCompleteTextViewCoche.getText().toString();
        if (cocheSeleccionado.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona un coche.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] cocheParts = cocheSeleccionado.split(", ", 2);
        int idCoche = Integer.parseInt(cocheParts[0]);

        String fechaMantenimiento = textInputEditTextFechaMantenimiento.getText().toString();

        if (!fechaMantenimiento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Formato de fecha incorrecto.", Toast.LENGTH_SHORT).show();
            return;
        }

        prestamo = new Prestamo();
        prestamo.setId_usuario(this.usuario.getId()); // Asegúrate de tener el ID del usuario disponible
        prestamo.setId_coche(idCoche);
        prestamo.setFecha_alquiler(fechaMantenimiento);

        boolean resultado = prestamoDAO.anadirPrestamo(prestamo);
        if (resultado) {
            Toast.makeText(this, "Alquiler guardado correctamente.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "El vehículo está ocupado el día seleccionado.", Toast.LENGTH_SHORT).show();
        }
    }
}
