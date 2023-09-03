import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NuevoVendedor {
    public static void main(String[] args) throws ParseException, IOException {
        final String dataPath = "vendors-data.dat";
        final String csvPath = "vendors.csv";

        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        String nombreVendedor = sc.nextLine();

        System.out.println("Ingrese la fecha: ");
        String fechaVendedor = sc.nextLine();

        System.out.println("Ingrese la zona: ");
        String zonaVendedor = sc.nextLine();

        System.out.println("Ingrese las ventas mensuales: ");
        int ventasMensuales =  sc.nextInt();

        Vendor fechaNueva = new Vendor();

        fechaNueva.setFecha(fechaVendedor); //set para que fecha no regrese un valor nulo

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(fechaVendedor);

        BufferedReader br = new BufferedReader(new FileReader(csvPath));

        int fileSize = 0;

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            fileSize++;
        }
        br.close();

        RandomAccessFile randomAccessFile = new RandomAccessFile(csvPath, "rw");
        randomAccessFile.seek(randomAccessFile.length());


        String dataToAppend = "\n" + fileSize + "," + nombreVendedor+ "," + fechaVendedor + "," + zonaVendedor + "," + ventasMensuales;
        randomAccessFile.writeBytes(dataToAppend);
        randomAccessFile.close();
        CopyCSV.main(new String[]{});

    }
}
