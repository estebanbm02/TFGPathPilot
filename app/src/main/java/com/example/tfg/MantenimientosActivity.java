package com.example.tfg;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.android.material.textfield.TextInputEditText;

import Adapter.CocheAdapter;
import DAO.CocheDAO;
import DAO.MantenimientoDAO;
import Model.Coche;
import Model.Mantenimiento;

public class MantenimientosActivity extends ToolbarActivity{
    private static final String TAG = "MantenimientosActivity";
    private CocheAdapter cocheAdapter;
    private CocheDAO cocheDAO;
    private MantenimientoDAO mantenimientoDAO;
    private Mantenimiento mantenimiento;
    private TextInputEditText textInputEditTextFechaMantenimiento;
    private TextInputEditText textInputEditTextTipoMantenimiento;
    private AutoCompleteTextView autoCompleteTextViewCoche;
    private ArrayList<String> coches;
    private Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimiento_layout);

        coches = new ArrayList<>();

        cocheDAO = new CocheDAO(this);
        mantenimientoDAO = new MantenimientoDAO(this);

        autoCompleteTextViewCoche = findViewById(R.id.textInputEditTextCoche);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        cargarCoches();

        setToolbarOnClicks();

        textInputEditTextFechaMantenimiento = findViewById(R.id.textInputEditTextFechaMantenimiento);
        textInputEditTextTipoMantenimiento = findViewById(R.id.textInputEditTextTipoMantenimiento);


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
                guardarMantenimiento();
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
        List<Coche> listaCoches = cocheDAO.listarCochesUsuario(this.usuario.getId());
        if (listaCoches != null && !listaCoches.isEmpty()) {
            for(Coche coche : listaCoches){
                coches.add(coche.getId()+ ", " + coche.getMarca() + ", " + coche.getModelo());
            }
        } else {
            Toast.makeText(this, "No se encontraron coches.", Toast.LENGTH_SHORT).show();
        }
        return coches;
    }

    private void guardarMantenimiento() {
        String cocheSeleccionado = autoCompleteTextViewCoche.getText().toString();
        if (cocheSeleccionado.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona un coche.", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] cocheParts = cocheSeleccionado.split(", ", 2);
        int idCoche = Integer.parseInt(cocheParts[0]);

        String fechaMantenimiento = textInputEditTextFechaMantenimiento.getText().toString();
        String tipomantenimiento = textInputEditTextTipoMantenimiento.getText().toString();

        if (!fechaMantenimiento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Formato de fecha incorrecto.", Toast.LENGTH_SHORT).show();
            return;
        }

        mantenimiento = new Mantenimiento();
        mantenimiento.setId_usuario(this.usuario.getId());
        mantenimiento.setId_coche(idCoche);
        mantenimiento.setTipo_mantenimiento(tipomantenimiento);
        mantenimiento.setFecha_alquiler(fechaMantenimiento);

        boolean resultado = mantenimientoDAO.anadirMantenimiento(mantenimiento);
        if (resultado) {
            Toast.makeText(this, "Mantenimiento guardado correctamente.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El coche ya está ocupado este día.", Toast.LENGTH_SHORT).show();
        }
    }
}