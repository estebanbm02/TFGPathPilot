package Model;

public class Articulo {
    private int id_articulo;
    private String nombre;
    //private String descripcion;
    private String tipo;
    private double precio;
    private String imagen;

    public Articulo() {
    }

    public int getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(int id_articulo) {
        this.id_articulo = id_articulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id_articulo +
                ", nombre='" + nombre + '\'' +
                //", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
