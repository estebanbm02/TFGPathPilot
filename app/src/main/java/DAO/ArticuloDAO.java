package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import DB.DatabaseHelper;
import Model.Articulo;

public class ArticuloDAO {
    private DatabaseHelper dbHelper;
    private static final String TAG = "ArticuloDAO";

    public ArticuloDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public boolean anadirArticulo(Articulo articulo) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("nombre", articulo.getNombre());
            //values.put("descripcion", articulo.getDescripcion());
            values.put("tipo", articulo.getTipo());
            values.put("precio", articulo.getPrecio());
            values.put("imagen", articulo.getImagen());

            long id_articulo = db.insertOrThrow("articulo", null, values);
            return id_articulo != -1;
        } catch (Exception e) {
            Log.e(TAG, "Error al añadir artículo", e);
            return false;
        }
    }

    public boolean actualizarArticulo(Articulo articulo) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("nombre", articulo.getNombre());
            //values.put("descripcion", articulo.getDescripcion());
            values.put("tipo", articulo.getTipo());
            values.put("precio", articulo.getPrecio());
            values.put("imagen", articulo.getImagen());

            int rowsAffected = db.update("articulo", values, "id_articulo = ?", new String[]{String.valueOf(articulo.getId_articulo())});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error al actualizar artículo", e);
            return false;
        }
    }

    public boolean borrarArticulo(int id_articulo) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            int rowsAffected = db.delete("articulo", "id_articulo = ?", new String[]{String.valueOf(id_articulo)});
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e(TAG, "Error al borrar artículo", e);
            return false;
        }
    }

    public List<Articulo> listarArticulos() {
        List<Articulo> listaArticulos = new ArrayList<>();
        String[] columnas = {"id_articulo", "nombre", "tipo", "precio", "imagen"};
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("articulo", columnas, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                listaArticulos.add(crearArticuloDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al listar artículos", e);
        }
        return listaArticulos;
    }

    public List<Articulo> buscarArticulosPorNombre(String nombre) {
        List<Articulo> listaArticulos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("articulo",
                new String[]{"id_articulo", "nombre", "tipo", "precio", "imagen"},
                "nombre LIKE ?", new String[]{"%" + nombre + "%"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                listaArticulos.add(crearArticuloDesdeCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaArticulos;
    }

    public List<Articulo> buscarArticulosPorTipo(String tipo) {
        List<Articulo> listaArticulos = new ArrayList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query("articulo", null, "tipo = ?", new String[]{tipo}, null, null, null)) {
            while (cursor.moveToNext()) {
                listaArticulos.add(crearArticuloDesdeCursor(cursor));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al buscar artículos por tipo", e);
        }
        return listaArticulos;
    }

    private Articulo crearArticuloDesdeCursor(Cursor cursor) {
        Articulo articulo = new Articulo();
        articulo.setId_articulo(cursor.getInt(cursor.getColumnIndexOrThrow("id_articulo")));
        articulo.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
        //articulo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
        articulo.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
        articulo.setPrecio(cursor.getDouble(cursor.getColumnIndexOrThrow("precio")));
        articulo.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("imagen")));
        return articulo;
    }
}
