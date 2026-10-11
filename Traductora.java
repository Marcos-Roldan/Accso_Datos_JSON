package gasolinera;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;

public class Traductora implements MigraCSVToJson {

    public void ejecutarMigracionSiEsNecesario() {
        Path directorio = Path.of("datos");
        Path archivoClientesJson = directorio.resolve("clientes.json");
        Path archivoPagosJson = directorio.resolve("pagos.json");

        // Si ya existe JSON, arranca directamente sin molestar al usuario
        if (Files.exists(archivoClientesJson) || Files.exists(archivoPagosJson)) {
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("¿Hay ficheros CSV que migrar?");
        System.out.println("1. Si");
        System.out.println("2. No");
        System.out.print("Elige una opcion: ");

        int opcion;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            opcion = 2;
        }

        if (opcion == 1) {
            try {
                Files.createDirectories(directorio);
            } catch (IOException e) {
                System.out.println("Error al crear el directorio: " + e.getMessage());
                return;
            }

            // Leemos del formato CSV antiguo
            Almacenamiento_EN_CSV csvStorage = new Almacenamiento_EN_CSV();
            List<Cliente> clientes = csvStorage.leerClientes();
            List<Pagos> pagos = csvStorage.leerPagos();

            // Guardamos en JSON usando lambdas (.forEach)
            AlmacenamientoJSON jsonStorage = new AlmacenamientoJSON();
            clientes.forEach(jsonStorage::escribirCliente);
            pagos.forEach(jsonStorage::escribirPagos);

            System.out.println("Validacion completada.");
            System.out.println("Clientes migrados: " + clientes.size());
            System.out.println("Pagos migrados: " + pagos.size());
            System.out.println("Migracion completada en la carpeta 'datos'.");

        } else {
            try {
                Files.createDirectories(directorio);
                if (Files.notExists(archivoClientesJson)) {
                    Files.writeString(archivoClientesJson, "[]");
                }
                if (Files.notExists(archivoPagosJson)) {
                    Files.writeString(archivoPagosJson, "[]");
                }
                System.out.println("Se ha creado un almacenamiento JSON vacio.");
            } catch (IOException e) {
                System.out.println("Error al inicializar JSON: " + e.getMessage());
            }
        }
    }

    @Override
    public void almacenamientoJSONCliente(Cliente cliente) {
        // Requerido por la interfaz
    }
}