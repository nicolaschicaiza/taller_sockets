import java.io.BufferedOutputStream; // Permite almacenar en búfer la salida y admitir los métodos de marcar y restablecer
import java.io.FileOutputStream; // Retorna los bytes recibidos en un archivo especifico
import java.io.IOException;
import java.io.InputStream; // Define el flujo de entrada de bytes
import java.net.Socket;

public class ClienteArchivos {

  public static final int PUERTO_SOCKET = 3738; // Definir puerto para el socket
  public static final String SERVIDOR = "127.0.0.1"; // Dirección IP del servidor, en este caso se usa el localhost
  public static final String ARCHIVO = "./recibido.txt"; // Ruta y nombre del archivo que recibe la información
  // Si se ejecuta en el mismo equipo deben tener diferentes nombres, porque si no se reemplazará

  public static final int TAMANYO_ARCHIVO = 40022386; // Tamaño del fichero temporal codificado
  // Debe ser más grande que el archivo a descargar

  public static void main(String[] args) throws IOException {
    // Arranca el programa
    ClienteArchivos app = new ClienteArchivos();
    app.start();
  }

  public void start() throws IOException {
    // Inicializa las variables
    int lecturaBytes;
    int byteActual = 0;
    FileOutputStream retornarBytes = null;
    BufferedOutputStream buferAlmacenado = null;
    Socket sock = null;

    // Para detectar errores se emplea un controlador de excepciones 
    // Ejecución del programa
    try {
      sock = new Socket(SERVIDOR, PUERTO_SOCKET); // Crea el socket con la dirección IP y el puerto
      System.out.println("Conectando...");
      byte[] mybytearray = new byte[TAMANYO_ARCHIVO]; // Crea un vector de bytes con el tamaño estipulado 
      InputStream entradaBytes = sock.getInputStream(); // Prepara los bytes recibidos
      retornarBytes = new FileOutputStream(ARCHIVO); // Retorna los bytes recibidos al fichero especificado
      buferAlmacenado = new BufferedOutputStream(retornarBytes); // Almacena los bytes de bufer en el fichero
      lecturaBytes = entradaBytes.read(mybytearray, 0, mybytearray.length); // Lee los bytes de entrada
      byteActual = lecturaBytes;

      // Descarga de bytes en el fichero establecido
      do {
        lecturaBytes = entradaBytes.read(mybytearray, byteActual, (mybytearray.length - byteActual));
        if (lecturaBytes >= 0) byteActual += lecturaBytes;
      } while (lecturaBytes > -1);

      buferAlmacenado.write(mybytearray, 0, byteActual); // Escribe los bytes recibidos 

      buferAlmacenado.flush(); // Vacía el flujo de entrada y fuerza la escritura de los bytes

      System.out.println("File " + ARCHIVO + " downloaded (" + byteActual + " bytes read)");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // Al finalizar hace:
      if (retornarBytes != null) retornarBytes.close(); // Cierra retorno de bytes
      if (buferAlmacenado != null) buferAlmacenado.close(); // Cierra almacenamiento
      if (sock != null) sock.close(); // Cierra la conexión
    }
  }
}
