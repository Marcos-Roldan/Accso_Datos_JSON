import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ClienteDAO {

    private Almacenamiento almacenamiento;
    private MigraCSVToJson migraCSVToJson;

    public ClienteDAO(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public ClienteDAO(MigraCSVToJson migraCSVToJson) {
        this.migraCSVToJson = migraCSVToJson;
    }

    public void AltaCliente() {
        Scanner sc = new Scanner(System.in);
        List<Cliente> lista = almacenamiento.leerClientes();
        boolean confrimar = false;

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        while (nombre.isEmpty()) {
            System.out.println("No puedes dejar el campo vacio");
            System.out.print("Nombre: ");
            nombre = sc.nextLine().trim();
        }

        System.out.print("Telefono: ");
        String telefono = sc.nextLine().trim();
        while (telefono.isEmpty()) {
            System.out.println("No puedes dejar el campo vacio");
            System.out.print("Telefono: ");
            telefono = sc.nextLine().trim();
        }

        System.out.print("Matricula: ");
        String matricula = sc.nextLine().trim();
        while (matricula.isEmpty()) {
            System.out.println("No puedes dejar el campo vacio");
            System.out.print("Matricula: ");
            matricula = sc.nextLine().trim();
        }

        // COMPRUEBO SI LA MATRICULA EXISTE
        for (Cliente c : lista) {
            if (c.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println("Esa matricula ya esta registrada.");
                return;
            }
        }

        // Calcular el nuevo ID (maximo + 1)
        int nuevoId = 1;
        for (Cliente c : lista) {
            if (c.getId() >= nuevoId) {
                nuevoId = c.getId() + 1; //Total de cliente + 1 seria: List<Cliente> cliente = almacenamiento.leerClientes() y luego int nuevoID = clientes.size() + 1
            }
        }

        // Guardar cliente
        Cliente clienteNuevo = new Cliente(nuevoId, nombre, telefono, matricula.toUpperCase());
        if (almacenamiento.escribirCliente(clienteNuevo)) {
            System.out.println("Cliente registrado con ID " + nuevoId + ".");
        }

        System.out.println("¿Quieres confirmar el alta del cliente?");
        System.out.println("1. Si");
        System.out.println("2. No");

        int opcion = sc.nextInt();

        if(opcion == 2) {
            Path archivoTemp = Path.of("datos", "clientes.json");

            try {
                boolean borrado = Files.deleteIfExists(archivoTemp);
                if (borrado) {
                    System.out.println("Temporal eliminado.");
                }
            } catch (IOException e) {
                System.out.println("Error al borrar: " + e.getMessage());
            }
        } else if(opcion == 1) {
            System.out.println("Has confrimado el alta del cliente");
        } else {
            System.out.println("Opcion no valida");
        }

    }

    public void ListarCliente() {
        List<Cliente> lista = almacenamiento.leerClientes();

        if (lista.isEmpty()) {
            System.out.println("No hay clientes.");
            return;
        }

        // Ordenar con bucle tradicional por Nombre e ID
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                Cliente c1 = lista.get(i);
                Cliente c2 = lista.get(j);

                int comparacion = c1.getNombre().compareToIgnoreCase(c2.getNombre());

                if (comparacion > 0) { //De la A a la Z, si cambias el signo va de la Z a la A
                    lista.set(i, c2);
                    lista.set(j, c1);
                } else if (comparacion == 0) {
                    if (c1.getId() > c2.getId()) { //De menor a mayor
                        lista.set(i, c2);
                        lista.set(j, c1);
                    }
                }
            }
        }

        System.out.println("ID | NOMBRE | TELEFONO | MATRICULA");
        for (Cliente c : lista) {
            System.out.println(c.getId() + " | " + c.getNombre() + " | " + c.getTelefono() + " | " + c.getMatricula());
        }
    }

    public void BuscarCliente() {
        Scanner sc = new Scanner(System.in);
        List<Cliente> lista = almacenamiento.leerClientes();

        System.out.print("Texto que buscar: ");
        String texto = sc.nextLine().trim().toLowerCase();

        if (texto.isEmpty()) {
            System.out.println("El texto no puede estar vacio.");
            return;
        }

        List<Cliente> encontrados = new ArrayList<>();

        for (Cliente c : lista) {
            if (c.getNombre().toLowerCase().contains(texto) ||
                    c.getTelefono().toLowerCase().contains(texto) ||
                    c.getMatricula().toLowerCase().contains(texto)) {
                encontrados.add(c);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se han encontrado clientes.");
            return;
        }

        // Ordenar resultados
        for (int i = 0; i < encontrados.size() - 1; i++) {
            for (int j = i + 1; j < encontrados.size(); j++) {
                Cliente c1 = encontrados.get(i);
                Cliente c2 = encontrados.get(j);

                int comparacion = c1.getNombre().compareToIgnoreCase(c2.getNombre());

                if (comparacion > 0) {
                    encontrados.set(i, c2);
                    encontrados.set(j, c1);
                } else if (comparacion == 0) {
                    if (c1.getId() > c2.getId()) {
                        encontrados.set(i, c2);
                        encontrados.set(j, c1);
                    }
                }
            }
        }

        System.out.println("ID | NOMBRE | TELEFONO | MATRICULA");
        for (Cliente c : encontrados) {
            System.out.println(c.getId() + " | " + c.getNombre() + " | " + c.getTelefono() + " | " + c.getMatricula());
        }
    }
}