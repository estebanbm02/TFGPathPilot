package Model;

public class Mantenimiento {
    private int id_usuario;
    private int id_coche;
    private String tipo_mantenimiento;
    private String fecha_alquiler;

    public Mantenimiento() {

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

    public String getTipo_mantenimiento() {
        return tipo_mantenimiento;
    }

    public void setTipo_mantenimiento(String tipo_mantenimiento) {
        this.tipo_mantenimiento = tipo_mantenimiento;
    }

    public String getFecha_alquiler() {
        return fecha_alquiler;
    }

    public void setFecha_alquiler(String fecha_alquiler) {
        this.fecha_alquiler = fecha_alquiler;
    }
}
