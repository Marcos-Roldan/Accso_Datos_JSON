import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Almacenamiento_EN_CSV implements Almacenamiento {

    private final String archivoClientes = "clientes.csv";
    private final String archivoPagos = "pagos_repostajes.csv";

    @Override
    public List<Cliente> leerClientes() {
        List<Cliente> lista = new ArrayList<>();
        File file = new File(archivoClientes);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(","); //Cambiamos la ordenacion a: ,
                    if (datos.length >= 4) {
                        int id = Integer.parseInt(datos[0].trim());
                        String nombre = datos[1].trim();
                        String telefono = datos[2].trim();
                        String matricula = datos[3].trim();

                        lista.add(new Cliente(id, nombre, telefono, matricula));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el fichero de clientes.");
        }

        return lista;
    }

    @Override
    public boolean escribirCliente(Cliente cliente) {
        //1. LEER EL FICHERO
        //2. AÑADIR EL CLIENTE
        //3. SOBREESCRIBIR EL CLIENTE
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientes, true))) {
            String linea = cliente.getId() + "," + cliente.getNombre() + "," + cliente.getTelefono() + "," + cliente.getMatricula();
            bw.write(linea);
            bw.newLine();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar el cliente.");
            return false;
        }
    }

    @Override
    public List<Pagos> leerPagos() {
        List<Pagos> lista = new ArrayList<>();
        File file = new File(archivoPagos);

        if (!file.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 6) {
                        int id = Integer.parseInt(datos[0].trim());
                        int idCliente = Integer.parseInt(datos[1].trim());
                        LocalDate fecha = LocalDate.parse(datos[2].trim());
                        double importe = Double.parseDouble(datos[3].trim());
                        double litros = Double.parseDouble(datos[4].trim());
                        String combustible = datos[5].trim();

                        lista.add(new Pagos(id, idCliente, fecha, importe, litros, combustible));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer el fichero de pagos.");
        }

        return lista;
    }

    @Override
    public boolean escribirPagos(Pagos pagos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoPagos, true))) {
            String linea = pagos.getId() + "," + pagos.getId_cliente() + "," + pagos.getFecha() + "," + pagos.getImporte() + "," + pagos.getLitros() + "," + pagos.getCombustible();
            bw.write(linea);
            bw.newLine();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar el pago.");
            return false;
        }
    }
}