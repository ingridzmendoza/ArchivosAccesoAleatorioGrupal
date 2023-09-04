import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EliminarDatosVendedor {
    public static final String PATHDAT = "vendors-data.dat"; //archivo dat
    public static final String ARCHIVOTEMPORAL = "temporal.dat"; //archivo temporal
    public static void main(String[] args) {

        //Archivos para intercambiar en el futuro
        File original = new File(PATHDAT);
        File conCambio = new File(ARCHIVOTEMPORAL);

        //nombre vendedor que se desea eliminar
        Scanner scanner = new Scanner(System.in);
        System.out.print("Escribe el nombre del vendedor que quieras eliminar: ");
        String nombreVendedorEliminar = scanner.nextLine();

        //RandomAccessFile para entrar y eliminar
        try (RandomAccessFile entrar = new RandomAccessFile(PATHDAT, "rw");
             FileOutputStream salir = new FileOutputStream(ARCHIVOTEMPORAL)) {

            long posRead = 0;
            long posWrite = 0;
            int tamanioEntrar = Vendor.RECORD_LEN;
            long tamanioArchivo = entrar.length();
            byte[] buffer = new byte[tamanioEntrar];

            // Buscar, leer y eliminar vendedor que se quiera
            while (posRead < tamanioArchivo) {
                entrar.seek(posRead);
                entrar.read(buffer);

                String numero = new String(buffer, 0, 34, StandardCharsets.UTF_8).trim();

                if (!numero.equals(nombreVendedorEliminar)) {
                    salir.write(buffer);
                    posWrite += tamanioEntrar;
                }

                posRead += tamanioEntrar;
            }

            if (original.delete() && conCambio.renameTo(original)){
                System.out.println("Cambió");
            }else{
                System.out.println("No cambió");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }//end main
}
