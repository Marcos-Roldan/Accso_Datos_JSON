import java.util.*;

public interface Almacenamiento {

    List<Cliente> leerClientes();

    List<Cliente> cargarCliente();

    List<Cliente> cargarPago();

    boolean escribirCliente(Cliente cliente);

    List<Pagos> leerPagos();

    boolean escribirPagos(Pagos pagos);
}