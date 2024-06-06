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
import Model.Coche;
import Model.Mantenimiento;

public class MantenimientoDAO {

    private DatabaseHelper dbHelper;
    private static final String TAG = "MantenimientoDAO";

    public MantenimientoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean anadirMantenimiento(Mantenimiento mantenimiento) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            String query = "SELECT 1 FROM " +
                    "(SELECT id_coche, fecha_alquiler FROM alquiler " +
                    "UNION ALL " +
                    "SELECT id_coche, fecha FROM mantenimiento) " +
                    "WHERE id_coche = ? AND fecha_alquiler = ?";

            Log.d(TAG, "Checking for maintenance on date: " + mantenimiento.getFecha_alquiler() + " for car ID: " + mantenimiento.getId_coche());

            Cursor cursor = db.rawQuery(query, new String[]{
                    String.valueOf(mantenimiento.getId_coche()),
                    mantenimiento.getFecha_alquiler()
            });

            if (cursor.moveToFirst()) {
                // Si se encuentra una coincidencia, no permitir la inserción del nuevo mantenimiento
                Log.d(TAG, "Car is already booked on this date.");
                cursor.close();
                return false;
            }

            cursor.close();

            // Insertar el nuevo mantenimiento si no se encontró una coincidencia
            ContentValues values = new ContentValues();
            values.put("id_usuario", mantenimiento.getId_usuario());
            values.put("id_coche", mantenimiento.getId_coche());
            values.put("tipo_mantenimiento", mantenimiento.getTipo_mantenimiento());
            values.put("fecha", mantenimiento.getFecha_alquiler()); // Asegúrate de que estás usando el nombre correcto aquí

            long id = db.insertOrThrow("mantenimiento", null, values);
            return id != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error al añadir el mantenimiento", e);
            return false;
        }
    }

    public List<Mantenimiento> listarMantenimientosUsuario(int idUsuario) {
        List<Mantenimiento> listaMantenimientosUsuario = new ArrayList<>();
        String query = "SELECT m.*" +
                "FROM mantenimiento AS m INNER JOIN propiedad AS u " +
                "ON m.id_coche = u.idcoche " +
                "WHERE u.idusuario = ?";

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)})) {
            while (cursor.moveToNext()) {
                listaMantenimientosUsuario.add(crearMantenimientoDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar coches", e);
        }
        return listaMantenimientosUsuario;
    }

    @SuppressLint("Range")
    private Mantenimiento crearMantenimientoDesdeCursor(Cursor cursor) {
        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId_usuario(cursor.getInt(cursor.getColumnIndex("id_usuario")));
        mantenimiento.setId_coche(cursor.getInt(cursor.getColumnIndex("id_coche")));
        mantenimiento.setTipo_mantenimiento(cursor.getString(cursor.getColumnIndex("tipo_mantenimiento")));
        mantenimiento.setFecha_alquiler(cursor.getString(cursor.getColumnIndex("fecha_alquiler")));

        return mantenimiento;
    }
}
