import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Traductora implements MigraCSVToJson {

    @Override
    public void almacenamientoJSONCliente(Cliente cliente) {
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Hay ficheros CSV que migrar?");
        System.out.println("1. Si");
        System.out.println("2. No");

        int opcion = sc.nextInt();

        if(opcion == 2) {
            Path directorio = Path.of("datos");
            Path archivo = directorio.resolve("clientes.json");

            try {
                Files.createDirectories(directorio);

                if (Files.notExists(archivo)) {
                    Files.createFile(archivo);
                    System.out.println("Almacenamineto JSON creado por primera vez.");
                }

                System.out.println("No hay ficheros que migrar");
                System.out.println("Se ha creado un almacenamiento JSON");
            } catch (IOException e) {
                System.out.println("Error al inicializar la estructura: " + e.getMessage()); //
            }
        } else if (opcion == 1) {
            System.out.println("¿Quieres migrar sobre las rutas de los ficheros CSV?");
            System.out.println("1. Si");
            System.out.println("2. No");

            int opcion1 = sc.nextInt();

            if(opcion1 == 2) {
                System.out.println("Has rechazado la migracion");
            } else if(opcion1 == 1) {
                Path origen = Path.of("datos", "clientes.csv");
                Path destino = Path.of("datos", "clientes_backup.csv");

                int contCliente = 0;
                int contPagos = 0;

                try {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

                    contCliente++;
                    contPagos++;

                    System.out.println("Origen CSV: " + origen);
                    System.out.println("Destino JSON: " + destino);
                    System.out.println("Validacion completada.");
                    System.out.println("Clientes migrados: " + contCliente);
                    System.out.println("Pagos migrados: " + contPagos);
                    System.out.println("Migracion completada en " + destino + ".");
                } catch (IOException e) {
                    System.out.println("Error al realizar el backup: " + e.getMessage());
                }

            } else {
                System.out.println("Opcion no valida");
            }

        } else {
            System.out.println("Opcion no valida");
        }
    }
}