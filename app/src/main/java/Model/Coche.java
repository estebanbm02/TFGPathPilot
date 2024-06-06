package Model;

import android.os.Bundle;

import java.util.List;

public class Coche {
    private int id;
    private String marca;
    private String modelo;
    private String tipo;
    private String caballos;
    private String ubicacion;
    private double precio_por_dia;
    private String imagen;

    public Coche() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaballos() {
        return caballos;
    }

    public void setCaballos(String caballos) {
        this.caballos = caballos;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecio_por_dia() {
        return precio_por_dia;
    }

    public void setPrecio_por_dia(double precio_por_dia) {
        this.precio_por_dia = precio_por_dia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("marca", marca);
        bundle.putString("modelo", modelo);
        bundle.putString("tipo", tipo);
        bundle.putString("caballos", caballos);
        bundle.putString("ubicacion", ubicacion);
        bundle.putDouble("precio_por_dia", precio_por_dia);
        bundle.putString("imagen", imagen);

        return bundle;
    }

}
