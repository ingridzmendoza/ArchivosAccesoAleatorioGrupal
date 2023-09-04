import java.io.IOException;
import java.util.Scanner;
public class EncuentraZona {
    public static void main(String[] args) throws IOException {
        final String csvPath = "vendors.csv";
        Scanner input = new Scanner(System.in);
        System.out.println("Escriba el nombre de la Zona que busca: \n");
        String zona = input.nextLine();
        VendorCSVFile vendorCSVFile = new VendorCSVFile(csvPath);
        System.out.println("Los usuarios en esta Zona son:");
        System.out.println(vendorCSVFile.findZona(zona));
    }
}