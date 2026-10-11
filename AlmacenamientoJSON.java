package gasolinera;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class AlmacenamientoJSON implements Almacenamiento {
    private final Path directorio = Path.of("datos");
    private final Path archivoClientes = directorio.resolve("clientes.json");
    private final Path archivoPagos = directorio.resolve("pagos.json");

    public AlmacenamientoJSON() {
        try {
            Files.createDirectories(directorio);
            if (Files.notExists(archivoClientes)) {
                Files.writeString(archivoClientes, "[]");
            }
            if (Files.notExists(archivoPagos)) {
                Files.writeString(archivoPagos, "[]");
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar ficheros JSON: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> cargarCliente() {
        return leerClientes();
    }

    @Override
    public List<Cliente> cargarPago() {
        return List.of();
    }

    @Override
    public List<Cliente> leerClientes() {
        List<Cliente> lista = new ArrayList<>();
        if (Files.notExists(archivoClientes)) return lista;
        
        try {
            String contenido = Files.readString(archivoClientes).trim();
            if (contenido.equals("[]") || contenido.isEmpty()) return lista;

            // LIMPIO LOS CORCHETES DEL JSON BASICO
            contenido = contenido.substring(1, contenido.length() - 1);
            String[] objetos = contenido.split("\\},\\s*\\{"); //GUARDARLO 

            Arrays.stream(objetos).forEach(obj -> {
                obj = obj.replace("{", "").replace("}", "").replace("\"", "");
                String[] lineas = obj.split(",");
                
                int id = 0;
                String nombre = "";
                String telefono = "";
                String matricula = "";

                for (String linea : lineas) {
                    String[] kv = linea.split(":");
                    if (kv.length == 2) {
                        String k = kv[0].trim();
                        String v = kv[1].trim();
                        if (k.equals("id")) id = Integer.parseInt(v);
                        if (k.equals("nombre")) nombre = v;
                        if (k.equals("telefono")) telefono = v;
                        if (k.equals("matricula")) matricula = v;
                    }
                }
                if (!nombre.isEmpty()) {
                    lista.add(new Cliente(id, nombre, telefono, matricula));
                }
            });
        } catch (Exception e) {
            System.out.println("Error al leer clientes.json: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean escribirCliente(Cliente cliente) {
        List<Cliente> lista = leerClientes();
        lista.add(cliente);

        StringBuilder sb = new StringBuilder("[\n");
        // Uso de lambda con forEach para recorrer la lista y montar el JSON
        lista.forEach(c -> {
            sb.append("  {\n")
              .append("    \"id\": ").append(c.getId()).append(",\n")
              .append("    \"nombre\": \"").append(c.getNombre()).append("\",\n")
              .append("    \"telefono\": \"").append(c.getTelefono()).append("\",\n")
              .append("    \"matricula\": \"").append(c.getMatricula()).append("\"\n")
              .append("  },\n");
        });

        if (!lista.isEmpty()) {
            sb.setLength(sb.length() - 2); // Quitar la última coma
            sb.append("\n");
        }
        sb.append("]");

        try {
            Files.writeString(archivoClientes, sb.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir el cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Pagos> leerPagos() {
        List<Pagos> lista = new ArrayList<>();
        if (Files.notExists(archivoPagos)) return lista;

        try {
            String contenido = Files.readString(archivoPagos).trim();
            if (contenido.equals("[]") || contenido.isEmpty()) return lista;

            contenido = contenido.substring(1, contenido.length() - 1);
            String[] objetos = contenido.split("\\},\\s*\\{");

            Arrays.stream(objetos).forEach(obj -> {
                obj = obj.replace("{", "").replace("}", "").replace("\"", "");
                String[] lineas = obj.split(",");

                int id = 0, idCliente = 0;
                LocalDate fecha = LocalDate.now();
                double importe = 0, litros = 0;
                String combustible = "";

                for (String linea : lineas) {
                    String[] kv = linea.split(":", 2);
                    if (kv.length == 2) {
                        String k = kv[0].trim();
                        String v = kv[1].trim();
                        if (k.equals("id")) id = Integer.parseInt(v);
                        if (k.equals("id_cliente")) idCliente = Integer.parseInt(v);
                        if (k.equals("fecha")) fecha = LocalDate.parse(v);
                        if (k.equals("importe")) importe = Double.parseDouble(v);
                        if (k.equals("litros")) litros = Double.parseDouble(v);
                        if (k.equals("combustible")) combustible = v;
                    }
                }
                if (id > 0) {
                    lista.add(new Pagos(id, idCliente, fecha, importe, litros, combustible));
                }
            });
        } catch (Exception e) {
            System.out.println("Error al leer pagos.json: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean escribirPagos(Pagos pago) {
        List<Pagos> lista = leerPagos();
        lista.add(pago);

        StringBuilder sb = new StringBuilder("[\n");
        lista.forEach(p -> {
            sb.append("  {\n")
              .append("    \"id\": ").append(p.getId()).append(",\n")
              .append("    \"id_cliente\": ").append(p.getId_cliente()).append(",\n")
              .append("    \"fecha\": \"").append(p.getFecha()).append("\",\n")
              .append("    \"importe\": ").append(p.getImporte()).append(",\n")
              .append("    \"litros\": ").append(p.getLitros()).append(",\n")
              .append("    \"combustible\": \"").append(p.getCombustible()).append("\"\n")
              .append("  },\n");
        });

        if (!lista.isEmpty()) {
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }
        sb.append("]");

        try {
            Files.writeString(archivoPagos, sb.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir el pago: " + e.getMessage());
            return false;
        }
    }
}