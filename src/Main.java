import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Almacenamiento almacenamiento = new Almacenamiento_EN_JSON();
        ClienteDAO clienteDAO = new ClienteDAO(almacenamiento);
        PagosDAO pagosDAO = new PagosDAO(almacenamiento);

        MigraCSVToJson migraCSVToJson = new Traductora();
        ClienteDAO clienteDAO1 = new ClienteDAO((Traductora) migraCSVToJson);
        PagosDAO pagosDAO1 = new PagosDAO((Traductora) migraCSVToJson);



        int opcion = 0;

        while (opcion != 6) {
            System.out.println("\n--- GESTION DE GASOLINERA ---");
            System.out.println("1. Alta de cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente");
            System.out.println("4. Procesar pago / repostaje");
            System.out.println("5. Consultar pagos");
            System.out.println("6. Salir");
            System.out.print("Elige una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    clienteDAO.AltaCliente();
                    break;
                case 2:
                    clienteDAO.ListarCliente();
                    break;
                case 3:
                    clienteDAO.BuscarCliente();
                    break;
                case 4:
                    pagosDAO.ProcesarPagoRepostaje();
                    break;
                case 5:
                    pagosDAO.ConsultarPagos();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida. Intentalo de nuevo.");
                    break;
            }
        }
    }
}