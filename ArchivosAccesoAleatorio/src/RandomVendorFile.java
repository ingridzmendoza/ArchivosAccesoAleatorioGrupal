import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomVendorFile {
    private String fileName;
    public RandomVendorFile(String fileName) {
        this.fileName = fileName;
    }

    public long write(Vendor v) {
        RandomAccessFile out = null;
        long position = 0;
        byte buffer[] = null;

        try {
            out = new RandomAccessFile(fileName, "rws");
            position = out.length(); //bytes en archivo
            out.seek(position); //ir a ultimo byte

            out.writeInt(v.getCodigo());

            // escribir los bytes que se requieren para imprimir el nombre
            buffer = v.getNombre().getBytes();
            out.write(buffer);

            // convertir de Date a long
            long dob = v.getFecha().getTime();
            out.writeLong(dob);

            // escribir los bytes que se requieren para imprimir la zona
            buffer = v.getZona().getBytes();
            out.write(buffer);

            //incluir ventas mensuales
            out.writeInt(v.getVentas());

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(RandomVendorFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }

    public Vendor read(long position) {
        RandomAccessFile out = null;
        long bytes = 0;
        byte buffer[] = null;
        Vendor vendor = null;
        try {
            out = new RandomAccessFile(fileName, "rws");
            out.seek(position);

            int codigo = out.readInt();
            byte[] nameBytes = new byte[Vendor.MAX_NAME];
            out.read(nameBytes);

            long dateBytes = out.readLong();
            byte[] zonaBytes = new byte[Vendor.MAX_ZONE];
            out.read(zonaBytes);

            int ventasMes = out.readInt();
            vendor = new Vendor(codigo, new String(nameBytes), new Date(dateBytes),
                    new String(zonaBytes), ventasMes);
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(RandomVendorFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vendor;
    }

    public void read(Vendor vendors[]) {
        RandomAccessFile out = null;
        long bytes = 0;
        byte buffer[] = null;
        Vendor vendor = null;
        try {
            out = new RandomAccessFile(fileName, "rws");
            for (int i = 0; i < vendors.length; i++) {
                int codigo = out.readInt();
                byte[] nameBytes = new byte[Vendor.MAX_NAME];
                out.read(nameBytes);

                long dateBytes = out.readLong();
                byte[] zonaBytes = new byte[Vendor.MAX_ZONE];
                out.read(zonaBytes);

                int ventasMes = out.readInt();
                vendors[i] = new Vendor(codigo, new String(nameBytes), new Date(dateBytes),
                        new String(zonaBytes), ventasMes);
            }
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(RandomVendorFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //printout consulta de ventas
    public static void main(String[] args) {
        final String dataPath = "vendors-data.dat";
        RandomVendorFile randomFile = new RandomVendorFile(dataPath);

        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese el registro con venta a consultar:");
        int n = input.nextInt();

        int pos = (n * Vendor.RECORD_LEN) - Vendor.RECORD_LEN;
        long t1 = System.currentTimeMillis();
        Vendor p = randomFile.read(pos);
        long t2 = System.currentTimeMillis();

        System.out.println(p); //imprime registro en posicion p
        System.out.println("Tiempo de ejecucion:" + (t2 - t1)); //tiempo de ejecucion

    }

}
