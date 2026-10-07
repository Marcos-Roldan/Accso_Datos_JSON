import java.time.LocalDate;

public class Pagos {

    //ATRIBUTOS
    private int id; //Entero positivo
    private int id_cliente; //Debe tener cliente asignado
    private LocalDate fecha; //Fecha real
    private double importe; //Mayor que cero con maximo de dos decimales
    private double litros; //Mayor que cero con maximo de dos decimales
    private String combustible; //Obligatorio

    //CONSTRUTOR
    public Pagos(int id, int id_cliente, LocalDate fecha, double importe, double litros, String combustible) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.fecha = fecha;
        this.importe = importe;
        this.litros = litros;
        this.combustible = combustible;
    }

    //GETTER Y SETTER
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    //STRING
    @Override
    public String toString() {
        return "id: " + id + ", id_cliente: " + id_cliente + ", fecha: " + fecha + ", importe: " + importe + ", litros: " + litros + ", combustible: " + combustible;

    }
}