package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import DB.DatabaseHelper;
import Model.Prestamo;

public class PrestamoDAO {
    private DatabaseHelper dbHelper;
    private static final String TAG = "PrestamoDAO";

    public PrestamoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public List<Prestamo> listarPrestamos(int idUsuario) {
        List<Prestamo> listaPrestamos = new ArrayList<>();
        String query = "SELECT c.*, u.fecha_devolucion " +
                "FROM coche AS c INNER JOIN usuariocoche AS u " +
                "ON c.id_coche = u.id_coche " +
                "WHERE u.id_usuario = ?";

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)})) {
            while (cursor.moveToNext()) {
                listaPrestamos.add(crearPrestamoDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar prestamos", e);
        }
        return listaPrestamos;
    }

    public boolean anadirPrestamo(Prestamo prestamo) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            String query = "SELECT 1 FROM " +
                    "(SELECT id_coche, fecha_alquiler FROM alquiler " +
                    "UNION ALL " +
                    "SELECT id_coche, fecha FROM mantenimiento) " +
                    "WHERE id_coche = ? AND fecha_alquiler = ?";

            Log.d(TAG, "Checking for maintenance on date: " + prestamo.getFecha_alquiler() + " for car ID: " + prestamo.getId_coche());

            Cursor cursor = db.rawQuery(query, new String[]{
                    String.valueOf(prestamo.getId_coche()),
                    prestamo.getFecha_alquiler()
            });

            if (cursor.moveToFirst()) {
                // Si se encuentra una coincidencia, no permitir la inserción del nuevo prestamo
                cursor.close();
                return false;
            }

            cursor.close();

            // Insertar el nuevo prestamo si no se encontró una coincidencia
            ContentValues values = new ContentValues();
            values.put("id_usuario", prestamo.getId_usuario());
            values.put("id_coche", prestamo.getId_coche());
            values.put("fecha_alquiler", prestamo.getFecha_alquiler());

            long id = db.insertOrThrow("alquiler", null, values);
            return id != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error al añadir el prestamo", e);
            return false;
        }
    }

    @SuppressLint("Range")
    private Prestamo crearPrestamoDesdeCursor(Cursor cursor) {
        Prestamo prestamo = new Prestamo();
        prestamo.setId_coche(cursor.getInt(cursor.getColumnIndex("id_coche")));
        prestamo.setId_usuario(cursor.getInt(cursor.getColumnIndex("id_usuario")));
        prestamo.setFecha_alquiler(cursor.getString(cursor.getColumnIndex("fecha_alquiler")));

        return prestamo;
    }


}