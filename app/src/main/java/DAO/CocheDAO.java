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
import Model.Articulo;
import Model.Coche;
import Model.Prestamo;

public class CocheDAO {
    private DatabaseHelper dbHelper;
    private static final String TAG = "CocheDAO";

    public CocheDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean anadirCoche(Coche coche) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("marca", coche.getMarca());
            values.put("modelo", coche.getModelo());
            values.put("tipo", coche.getTipo());
            values.put("caballos", coche.getCaballos());
            values.put("ubicacion", coche.getUbicacion());
            values.put("precio_por_dia", coche.getPrecio_por_dia());
            values.put("imagen", coche.getImagen());

            long id = db.insertOrThrow("coche", null, values);
            return id != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error al añadir el coche", e);
            return false;
        }
    }

    public boolean actualizarCoche(Coche coche) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("marca", coche.getMarca());
            values.put("modelo", coche.getModelo());
            values.put("tipo", coche.getTipo());
            values.put("caballos", coche.getCaballos());
            values.put("ubicacion", coche.getUbicacion());
            values.put("precio_por_dia", coche.getPrecio_por_dia());
            values.put("imagen", coche.getImagen());

            int rowsAffected = db.update("coche", values, "id_coche = ?", new String[]{String.valueOf(coche.getId())});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error al actualizar coche", e);
            return false;
        }
    }

    public boolean borrarCoche(int id) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            int rowsAffected = db.delete("coche", "id_coche = ?", new String[]{String.valueOf(id)});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error al borrar coche", e);
            return false;
        }
    }

    public List<Coche> listarCoches() {
        List<Coche> listaCoches = new ArrayList<>();
        String[] columnas = {"id_coche", "marca", "modelo", "tipo", "caballos", "ubicacion", "precio_por_dia", "imagen"};
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("coche", columnas, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                listaCoches.add(crearCocheDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar coches", e);
        }
        return listaCoches;
    }

    public List<Coche> listarTodosLosCoches() {
        List<Coche> listaCoches = new ArrayList<>();
        String[] columnas = {"id_coche", "marca", "modelo", "tipo", "caballos", "ubicacion", "precio_por_dia", "imagen"};
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("coche", columnas, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                listaCoches.add(crearCocheDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar artículos", e);
        }
        return listaCoches;
    }

    public List<Coche> buscarCochesPorTipo(String tipo) {
        return buscarCoches("tipo = ?", new String[]{tipo});
    }

    public List<Coche> buscarCochesPorUbicacion(String ubicacion) {
        String selection = "ubicacion LIKE ?";
        String[] selectionArgs = new String[]{"%" + ubicacion + "%"};
        return buscarCoches(selection, selectionArgs);
    }

    private List<Coche> buscarCoches(String selection, String[] selectionArgs) {
        List<Coche> listaCoches = new ArrayList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("coche", null, selection, selectionArgs, null, null, null)) {
            while (cursor.moveToNext()) {
                listaCoches.add(crearCocheDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al buscar coches", e);
        }
        return listaCoches;
    }

    public List<Coche> buscarCochePorMarca(String marca) {
        List<Coche> listaCoches = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("coche",
                new String[]{"id_coche", "marca", "modelo", "tipo", "caballos", "ubicacion", "precio_por_dia", "imagen"},
                "marca LIKE ?", new String[]{"%" + marca + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                listaCoches.add(crearCocheDesdeCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaCoches;
    }

    public List<Coche> listarCochesUsuario(int idUsuario) {
        List<Coche> listaCochesUsuario = new ArrayList<>();
        String query = "SELECT c.*" +
                "FROM coche AS c INNER JOIN propiedad AS u " +
                "ON c.id_coche = u.idcoche " +
                "WHERE u.idusuario = ?";

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)})) {
            while (cursor.moveToNext()) {
                listaCochesUsuario.add(crearCocheDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar coches", e);
        }
        return listaCochesUsuario;
    }
    public List<Coche> listarCochesOtros(int idUsuario) {
        List<Coche> listaCochesUsuario = new ArrayList<>();
        String query = "SELECT c.*" +
                "FROM coche AS c INNER JOIN propiedad AS u " +
                "ON c.id_coche = u.idcoche " +
                "WHERE u.idusuario != ?";

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)})) {
            while (cursor.moveToNext()) {
                listaCochesUsuario.add(crearCocheDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar coches", e);
        }
        return listaCochesUsuario;
    }

    private Coche crearCocheDesdeCursor(Cursor cursor) {
        Coche coche = new Coche();
        coche.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_coche")));
        coche.setMarca(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
        coche.setModelo(cursor.getString(cursor.getColumnIndexOrThrow("modelo")));
        coche.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
        coche.setCaballos(cursor.getString(cursor.getColumnIndexOrThrow("caballos")));
        coche.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow("ubicacion")));
        coche.setPrecio_por_dia(cursor.getDouble(cursor.getColumnIndexOrThrow("precio_por_dia")));
        coche.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("imagen")));
        return coche;
    }

    public Coche obtenerCochePorId(long id) {
        Coche coche = null;
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("coche", null, "id_coche = ?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor.moveToFirst()) {
                coche = crearCocheDesdeCursor(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al obtener coche por ID", e);
        }
        return coche;
    }
}