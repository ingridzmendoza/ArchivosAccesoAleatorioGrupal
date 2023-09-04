import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ModificarDatosUsuario {
    public static final String PATHCSV = "vendors.csv"; //csv file

    public static VendorCSVFile vendorCSVFile = new VendorCSVFile(PATHCSV); //clase VendorCSVFile
    public static void main(String[] args) throws ParseException, FileNotFoundException {

        //Scanner id de vendedor que se desea modificar
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escribe el numero del vendedor: ");
        int numVendedor = scanner.nextInt();

        //valores anteriores del vendedor que se eligió
        System.out.println("Valores anteriores del empleado " + numVendedor + ":" );
        Vendor datos = vendorCSVFile.find(numVendedor);
        System.out.println(datos);

        //menú
        System.out.println("1. Cambiar Nombre || 2. Fecha || 3. Zona ");
        int eleccionQHacer = scanner.nextInt();

        //Para la fecha
        scanner.nextLine();
        String juntarValores = String.valueOf(numVendedor) + ",";
        String fechaDeSalida;
        SimpleDateFormat entradaFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat salidaFecha = new SimpleDateFormat("MM/dd/yyyy");

        //Switch que tendrá el menú
        switch (eleccionQHacer) {
            case 1 -> { //cambiar nombre
                System.out.print("Escribir nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                System.out.println("Nombre cambió a "+ nuevoNombre);
                Date date = entradaFecha.parse(String.valueOf(datos.getFecha()));
                fechaDeSalida = salidaFecha.format(date);
                juntarValores += nuevoNombre + "," + fechaDeSalida + "," + datos.getZona();
            }
            case 2 -> { //Cambiar fecha
                System.out.print("Escribir fecha con formato MM/dd/yyyy: ");
                String fecha = scanner.nextLine();
                System.out.println("Fecha cambió a " + fecha);
                juntarValores += datos.getNombre() + "," + fecha + "," + datos.getZona();
            }
            case 3 -> { //Cambiar zona
                System.out.print("Escribir zona: ");
                String zona = scanner.nextLine();
                System.out.println("Zona cambió a "+ zona);
                Date date1 = entradaFecha.parse(String.valueOf(datos.getFecha()));
                fechaDeSalida = salidaFecha.format(date1);
                juntarValores += datos.getNombre() + "," + fechaDeSalida + "," + zona;
            }
        }
        juntarValores += "\n"; //Saltar de fila

        StringBuilder contenidoCsv = new StringBuilder();

        //Leer el archivo csv
        try(BufferedReader br = new BufferedReader(new FileReader(PATHCSV))){
            String linea;
            int empiezaLinea = 1;

            //Intercambiar / modificar los datos
            while ((linea = br.readLine()) != null){

                if (empiezaLinea == (numVendedor + 1)){

                    contenidoCsv.append(juntarValores);
                    empiezaLinea++;
                } else {

                    contenidoCsv.append(linea).append(System.lineSeparator());
                    empiezaLinea++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Escribir los datos en el CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATHCSV))) {
            bw.write(contenidoCsv.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Ponerlos en el .dat
        CopyCSV.main(new String[]
                {});

    }//end main
}
