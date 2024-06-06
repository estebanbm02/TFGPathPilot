package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import DB.DatabaseHelper;
import Model.Articulo;
import Model.Coche;
import Model.Usuario;

public class UsuarioDAO {
    private DatabaseHelper dbHelper;
    private static final String TAG = "UsuarioDAO";

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public Usuario login(String correo, String contrasenia) {
        List<Articulo> listaArticulos = new ArrayList<>();
        Usuario usuarioLogeado = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuario",
                new String[]{"id_usuario", "nombre", "email", "telefono", "direccion","ubicacion"},
                "email = ? AND contrasena = ?",
                new String[]{correo, getMD5Hash(contrasenia)},
                null, null, null);

        boolean loginSuccessful = false;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                usuarioLogeado = crearUsuarioDesdeCursor(cursor);
            }
            cursor.close();
        }

        db.close();

        return usuarioLogeado;
    }

    public static String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario obtenerUsuarioPorId(long id) {
        Usuario usuario = null;
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("usuario", null, "id_usuario = ?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor.moveToFirst()) {
                usuario = crearUsuarioDesdeCursor(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al obtener coche por ID", e);
        }
        return usuario;
    }

    public boolean verificarSiUsuarioExiste(String correo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("usuario",
                new String[]{"id_usuario"},
                "email = ?",
                new String[]{correo},
                null, null, null);

        boolean existe = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return existe;
    }

    public Usuario crearCuenta(String correo, String contrasenia, String nombre, String ubicacion) {
        SQLiteDatabase dbWritable = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("email", correo);
        values.put("contrasena", getMD5Hash(contrasenia));
        values.put("ubicacion", ubicacion);

        long id = dbWritable.insertOrThrow("usuario", null, values);
        dbWritable.close();

        if (id != -1) {
            return obtenerUsuarioPorId(id);
        } else {
            Log.e(TAG, "Error al crear el usuario");
            return null;
        }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("nombre", usuario.getNombre());
        values.put("ubicacion", usuario.getUbicacion());

        int rows = db.update("usuario", values, "id_usuario = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();

        return rows > 0;
    }

    @SuppressLint("Range")
    private Usuario crearUsuarioDesdeCursor(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(cursor.getColumnIndex("id_usuario")));
        usuario.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
        usuario.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        usuario.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
        usuario.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
        usuario.setUbicacion(cursor.getString(cursor.getColumnIndex("ubicacion")));
        return usuario;
    }
}