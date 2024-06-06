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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private DatabaseHelper dbHelper;
    private UsuarioDAO usuarioDAO;
    private EditText editTextCorreo;
    private EditText editTextContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        usuarioDAO = new UsuarioDAO(this);

        editTextCorreo = findViewById(R.id.textInputEditTextCorreo);
        editTextContrasenia = findViewById(R.id.textInputEditTextPassword);

        initializeButton();
        findViewById(R.id.buttonCrearCuenta).setOnClickListener(v -> openCrearCuentaActivity());
    }

    private void initializeButton() {
        Button button2 = findViewById(R.id.buttonLogin);
        if (button2 != null) {
            button2.setOnClickListener(v -> {
                String correo = editTextCorreo.getText().toString();
                String contrasenia = editTextContrasenia.getText().toString();
                Usuario usuarioLogeado = usuarioDAO.login(correo, contrasenia);

                if (usuarioLogeado != null) {
                    openHomeActivity(usuarioLogeado);
                } else {
                    Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }

    private void openCrearCuentaActivity() {
        Intent intent = new Intent(this, CrearCuentaActivity.class);
        startActivity(intent);
    }

    private void openHomeActivity(Usuario usuario) {
        // Guardar el estado de inicio de sesión en SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putInt("userId", usuario.getId());
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("usuario_logeado", usuario);
        startActivity(intent);
        finish();
    }
}
