package Model;

import java.util.Date;

public class Prestamo {
    private int id_usuario;
    private int id_coche;
    private String fecha_alquiler;

    public Prestamo() {

    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_coche() {
        return id_coche;
    }
    public void setId_coche(int id_coche) {
        this.id_coche = id_coche;
    }
    public String getFecha_alquiler() {
        return fecha_alquiler;
    }
    public void setFecha_alquiler(String fecha_alquiler) {
        this.fecha_alquiler = fecha_alquiler;
    }
}