import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Vendor {
    public static final int MAX_NAME = 35;
    public static final int MAX_ZONE = 15;

    public static final int RECORD_LEN = 66;

    private int codigo;      // 4 bytes
    private String nombre;   // 35 bytes
    private Date fecha;      // 8 bytes
    private String zona;     // 15 bytes
    private int ventas; // 4 bytes


    public Vendor(int codigo, String nombre, Date fecha, String zona, int mensual) {
        super();
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha = fecha;
        this.zona = zona;
        this.ventas = mensual;
    }


    public Vendor() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getZona() {
        return zona;
    }
    public int getVentas() {
        return ventas;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public void setNombre(String nombre) {
        int len = nombre.length();
        if( len < MAX_NAME ) {
            StringBuilder sb = new StringBuilder(nombre);
            int count = MAX_NAME - len;

            for(int i = 0;  i<count; i++) {
                sb.append(' ');
            }
            this.nombre = sb.toString();
        } else {
            this.nombre = nombre;
        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setFecha(String fecha) {
        Scanner parser = new Scanner( new StringReader(fecha) );
        parser.useDelimiter("/");

        /*GregorianCalendar espera recibir un mes en base a 0
        Ejemplo: Enero 0, Febrero 1, Marzo 2, y así.
        Por eso se implementa una resta.
        */
        int month = parser.nextInt()- 1;
        int day = parser.nextInt();
        int year = parser.nextInt();

        GregorianCalendar calendar = new GregorianCalendar(year,month, day);

        this.fecha = calendar.getTime();
    }

    public void setZona(String zona) {
        int len = zona.length();
        if( len < MAX_ZONE ) {
            StringBuilder sb = new StringBuilder(zona);
            int count = MAX_ZONE - len;
            for(int i = 0;  i < count; i++) {
                sb.append(' ');
            }
            this.zona = sb.toString();
        } else {
            this.zona = zona;
        }
    }

    public String fechaString() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return f.format(fecha);
    }
    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        // regresa una cadena en formato CSV
        return codigo + "," + nombre + "," + f.format(fecha) + "," + zona + "," + ventas;
    }
}
