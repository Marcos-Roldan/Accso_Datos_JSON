//NOTAS ALUMNOS
//MOSTRAR QUIEN TIENE MEJOR NOTA Y LA PEOR NOTA

package notas_alumnos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Notas_Alumnos {
    public static void main(String[] args) {
       
      String rutaFicheros = "notas.txt";
      
      int totalAlumnos = 0;
      double sumaNotas = 0.0;
      
      double notaMaxima = Double.NEGATIVE_INFINITY;
      double notaMinima = Double.POSITIVE_INFINITY;
      
      String alumnoMax = "";
      String alumnoMin = "";
      
        try(BufferedReader br = new BufferedReader(new FileReader(rutaFicheros))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {                
                linea = linea.trim();
                if(linea.isEmpty()) {
                    continue;
                }
                    
                    String[] partes = linea.split(",");
                    
                    String nombre = partes[0].trim();
                    double nota = Double.parseDouble(partes[1].trim());
                    
                    sumaNotas = sumaNotas + nota;
                    totalAlumnos++;
                    
                    if(nota > notaMaxima) {
                        notaMaxima = nota;
                        alumnoMax = nombre;
                    }
                    
                    if(nota < notaMinima) {
                        notaMinima = nota;
                        alumnoMin = nombre;
                    }
                
                if(totalAlumnos > 0) {
                    double media = sumaNotas / totalAlumnos;
                    
                    System.out.println("RESULTADO ALUMNOS: ");
                    System.out.println("Total de alumnos: " + totalAlumnos);
                    System.out.println("Nota media del grupo: " + media);
                    System.out.println("Nota Maxima: " + notaMaxima + ", Nombre: "+ alumnoMax);
                    System.out.println("Nota Minima: " + notaMinima + ", Nombre: " + alumnoMin);
                } else {
                    System.out.println("El fichero no contiene registros validos");
                }
            }
            
        } catch (IOException ex) {
            System.out.println("Error al leer el fichero "+ ex.getMessage());
        } catch(NumberFormatException e) {
            System.out.println("Erro en el formato de los datos/notas " + e.getMessage());
        }
        
    } 
}