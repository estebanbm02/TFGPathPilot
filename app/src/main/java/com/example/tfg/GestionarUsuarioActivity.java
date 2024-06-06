package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import DAO.UsuarioDAO;
import DB.DatabaseHelper;
import Model.Usuario;

public class GestionarUsuarioActivity extends ToolbarActivity{
    private static final String TAG = "GestionarUsuarioActivity";
    private DatabaseHelper dbHelper;
    private UsuarioDAO usuarioDAO;
    private EditText editTextCorreo;
    private EditText editTextNombre;
    private EditText editTextUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestionar_usuario_layout);
        usuarioDAO = new UsuarioDAO(this);

        editTextCorreo = findViewById(R.id.textInputEditTextCorreo);
        editTextNombre = findViewById(R.id.textInputEditTextNombre);
        editTextUbicacion = findViewById(R.id.textInputEditTextUbicacion);

        findViewById(R.id.buttonCerrarSesion).setOnClickListener(v -> mostrarDialogoCerrarSesion());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("usuario_logeado")) {
            usuario = (Usuario) intent.getSerializableExtra("usuario_logeado");
            if (usuario != null) {
                cargarDatosUsuario(usuario);
            } else {
                Toast.makeText(this, "No se pudo cargar la información del usuario", Toast.LENGTH_SHORT).show();
            }
        }

        initializeButton();

        Button buttonAplicarCambios = findViewById(R.id.buttonAplicar);
        if (buttonAplicarCambios != null) {
            buttonAplicarCambios.setOnClickListener(v -> aplicarCambiosUsuario());
        } else {
            Log.e(TAG, "El botón con ID 'buttonAplicarCambios' no se encontró en el layout.");
        }

        setToolbarOnClicks();
    }

    private void cargarDatosUsuario(Usuario usuario) {
        editTextCorreo.setText(usuario.getEmail());
        editTextNombre.setText(usuario.getNombre());
        editTextUbicacion.setText(usuario.getUbicacion());
    }

    private void mostrarDialogoCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí, estoy seguro", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesion();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void cerrarSesion() {
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackInformacionGestionarUsuario);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void aplicarCambiosUsuario() {
        String correo = editTextCorreo.getText().toString().trim();
        String nombre = editTextNombre.getText().toString().trim();
        String ubicacion = editTextUbicacion.getText().toString().trim();

        if (correo.isEmpty() || nombre.isEmpty() || ubicacion.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (correo.equals(usuario.getEmail()) && nombre.equals(usuario.getNombre()) && ubicacion.equals(usuario.getUbicacion())) {
            Toast.makeText(this, "No has cambiado nada", Toast.LENGTH_SHORT).show();
            return;
        }

        usuario.setEmail(correo);
        usuario.setNombre(nombre);
        usuario.setUbicacion(ubicacion);

        boolean actualizado = usuarioDAO.actualizarUsuario(usuario);

        if (actualizado) {
            Toast.makeText(this, "Información de usuario actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar la información del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
