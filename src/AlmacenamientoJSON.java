import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class AlmacenamientoJSON  implements Almacenamiento {
    private Path Archivo_CLIENTES;
    private int JSON_OPEN;

    @Override
    public List<Cliente> leerClientes() {
        return List.of();
    }

    @Override
    public List<Cliente> cargarCliente() {
        return List.of();
    }

    @Override
    public List<Cliente> cargarPago() {
        return List.of();
    }

    @Override
    public boolean escribirCliente(Cliente cliente) {
        return false;
    }

    @Override
    public List<Pagos> leerPagos() {
        return List.of();
    }

    @Override
    public boolean escribirPagos(Pagos pagos) {
        return false;
    }


    public void guardarCliente(Cliente cliente) throws IOException {
        //1)Leer todos los clientes que hay
        List<Cliente> listaDeClientes = cargarCliente();

        //2)Añadir este cliente a la lista
        listaDeClientes.add(cliente);

        String clientes_en_string;
        //3)Reescribir el fichero entero
        try(BufferedWriter bf = Files.newBufferedWriter(Archivo_CLIENTES, StandardOpenOption.CREATE)) {
            bf.write(JSON_OPEN);
            clientes_en_string = listaDeClientes.stream().map(this::clienteTOJSONString).reduce(
                    (String s1, String s2) -> {return s1 + ",\n" + s2}
            ).orElse("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String clienteTOJSONString(Cliente cliente) {
        return "";
    }
}
