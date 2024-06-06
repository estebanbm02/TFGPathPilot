package com.example.tfg;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
public class ContactanosActivity extends ToolbarActivity {
    EditText inputTextNombre, inputTextEmail, inputTextAsunto, inputTextMensaje;
    Button buttonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactanos_layout);
        setToolbarOnClicks();
        initializeViews();
        setupSendButtonListener();
        initializeButton();
    }

    private void initializeViews() {
        inputTextNombre = findViewById(R.id.inputTextNombre);
        inputTextEmail = findViewById(R.id.inputTextCorreo);
        inputTextAsunto = findViewById(R.id.inputTextAsunto);
        inputTextMensaje = findViewById(R.id.inputTextMensaje);
        buttonEnviar = findViewById(R.id.buttonEnviar);
    }

    private void setupSendButtonListener() {
        if (buttonEnviar != null) {
            buttonEnviar.setOnClickListener(v -> enviarEmail());
        } else {
            Toast.makeText(this, "Error: Botón de enviar no encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarEmail() {
        String nombre = inputTextNombre.getText().toString();
        String emailUsuario = inputTextEmail.getText().toString();
        String asunto = inputTextAsunto.getText().toString();
        String mensaje = inputTextMensaje.getText().toString();

        String cuerpoEmail = String.format("Nombre: %s\nEmail del usuario: %s\nMensaje: %s",
                nombre, emailUsuario, mensaje);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"estebanbernaldam@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, cuerpoEmail);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeButton() {
        ImageButton button2 = findViewById(R.id.buttonBackContactanos);
        if (button2 != null) {
            button2.setOnClickListener(v -> openSettingsActivity());
        } else {
            Log.e(TAG, "El botón con ID 'button2' no se encontró en el layout.");
        }
    }


    private void openSettingsActivity() {
        Intent intent = new Intent(ContactanosActivity.this, SettingsActivity.class);
        intent.putExtra("usuario_logeado", this.usuario);
        startActivity(intent);
    }
}
