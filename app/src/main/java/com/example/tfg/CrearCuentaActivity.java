package com.example.tfg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import DAO.UsuarioDAO;
import DB.DatabaseHelper;
import Model.Usuario;

public class CrearCuentaActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private DatabaseHelper dbHelper;
    private UsuarioDAO usuarioDAO;
    private EditText editTextCorreo;
    private EditText editTextContrasenia;
    private EditText editTextNombre;
    private EditText editTextUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cuenta_layout);
        usuarioDAO = new UsuarioDAO(this);

        editTextCorreo = findViewById(R.id.textInputEditTextCorreo);
        editTextContrasenia = findViewById(R.id.textInputEditTextPassword);
        editTextNombre = findViewById(R.id.textInputEditTextNombre);
        editTextUbicacion = findViewById(R.id.textInputEditTextUbicacion);

        initializeButton();
    }

    private void initializeButton() {
        Button buttonCrearCuenta = findViewById(R.id.buttonCrearCuenta);
        if (buttonCrearCuenta != null) {
            buttonCrearCuenta.setOnClickListener(v -> {
                String correo = editTextCorreo.getText().toString().trim();
                String contrasenia = editTextContrasenia.getText().toString().trim();
                String nombre = editTextNombre.getText().toString().trim();
                String ubicacion = editTextUbicacion.getText().toString().trim();

                if (correo.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty() || ubicacion.isEmpty()) {
                    Toast.makeText(CrearCuentaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean usuarioExiste = usuarioDAO.verificarSiUsuarioExiste(correo);

                if (!usuarioExiste) {
                    Usuario nuevoUsuario = usuarioDAO.crearCuenta(correo, contrasenia, nombre, ubicacion);
                    if (nuevoUsuario != null) {
                        openHomeActivity(nuevoUsuario);
                    } else {
                        Toast.makeText(CrearCuentaActivity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CrearCuentaActivity.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "El botón con ID 'buttonCrearCuenta' no se encontró en el layout.");
        }
    }

    private void openHomeActivity(Usuario usuario) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("usuario_logeado", usuario);
        startActivity(intent);
    }
}
