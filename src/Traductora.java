import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Traductora implements MigraCSVToJson {

    @Override
    public void almacenamientoJSON(Cliente cliente) {
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
                    System.out.println("Fichero creado por primera vez.");
                }
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
                Path origen = Path.of("datos", "clientes.csv"); //[cite: 8, 9]
                Path destino = Path.of("datos", "clientes_backup.csv"); //[cite: 8, 9]

                try {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING); //
                    System.out.println("Backup realizado correctamente.");
                } catch (IOException e) {
                    System.out.println("Error al realizar el backup: " + e.getMessage()); //
                }

            } else {
                System.out.println("Opcion no valida");
            }

        } else {
            System.out.println("Opcion no valida");
        }
    }

    @Override
    public void almacenamientoJSON(Pagos pagos) {
        Scanner sc = new Scanner(System.in);

        System.out.println("¿Hay ficheros CSV que migrar?");
        System.out.println("1. Si");
        System.out.println("2. No");

        int opcion = sc.nextInt();

        if(opcion == 2) {
            Path directorio = Path.of("datos");
            Path archivo = directorio.resolve("pagos.json");

            try {
                Files.createDirectories(directorio);

                if(Files.notExists(archivo)) {
                    Files.createFile(archivo);
                    System.out.println("Fichero creado por primera vez");
                }
            } catch (IOException e) {
                System.out.println("Error al inicializar la estructura " + e.getMessage());
            }
        } else if (opcion == 1) {

            System.out.println("¿Quieres migrar sobre las rutas de los ficheros CSV?");
            System.out.println("1. Si");
            System.out.println("2. No");

            int opcion1 = sc.nextInt();

            if(opcion1 == 2) {
                System.out.println("Has rechazado la migracion");
            } else if(opcion1 == 1) {
                Path origen = Path.of("datos", "clientes.csv"); //[cite: 8, 9]
                Path destino = Path.of("datos", "clientes_backup.csv"); //[cite: 8, 9]

                try {
                    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING); //
                    System.out.println("Backup realizado correctamente.");
                } catch (IOException e) {
                    System.out.println("Error al realizar el backup: " + e.getMessage()); //
                }

            } else {
                System.out.println("Opcion no valida");
            }

        } else {
            System.out.println("Opcion no valida");
        }
    }
}