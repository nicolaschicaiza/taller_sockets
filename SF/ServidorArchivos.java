import java.io.BufferedInputStream; // Permite almacenar en búfer la entrada y admitir los métodos de marcar y restablecer
import java.io.File; // Permite recibir la ruta del fichero dentro de los directorios
import java.io.FileInputStream; // Obtiene los bytes de entrada de un archivo especificado
import java.io.IOException;
import java.io.OutputStream; // Define el flujo de salida de bytes y los envía algún sumidero que establezca una conexión
import java.net.ServerSocket; // Coloca el socket recibido en modo espera de solicitudes
import java.net.Socket; // Implementa sockets de cliente
import java.util.ArrayList;

public class ServidorArchivos {

  public static final int PUERTO_SOCKET = 3738; // Puerto definido para la servicio 
  public static final String ARCHIVO = "./enviado.txt"; // Ruta donde se encuentra el fichero a tx
  private ArrayList<Socket> cliente;

  public static void main(String[] args) throws IOException {
    // Arranca el programa 
    ServidorArchivos app = new ServidorArchivos();
    app.init();
  }

  public void init() throws IOException {
    // Inicializar variables
    FileInputStream obtenerBytes = null;
    BufferedInputStream buferAlmacenado = null;
    OutputStream salidaBytes = null;
    ServerSocket server = null;
    Socket sock = null;

    // Para detectar errores se emplea un controlador de excepciones 
    try {
      server = new ServerSocket(PUERTO_SOCKET); // Creación del socket 
      // Se ejecuta el funcionamiento del servicio
      while (true) {
        System.out.println("Esperando Conexion...");
        // Detección de errores en la ejecución
        try {
          sock = server.accept(); // Acepta la conexión solicitada
          System.out.println("Conexion Aceptada - Socket: " + sock);  

          // Envio de fichero
          File myFile = new File(ARCHIVO); // Recibir ruta del fichero para identificarlo
          byte[] mybytearray = new byte[(int) myFile.length()]; // Obtiene el tamaño del fichero para crear un vector de bytes
          obtenerBytes = new FileInputStream(myFile); // Recibe los bytes del fichero
          buferAlmacenado = new BufferedInputStream(obtenerBytes); // Almacena en búfer el fichero
          buferAlmacenado.read(mybytearray, 0, mybytearray.length); // Lectura de los bytes del fichero
          salidaBytes = sock.getOutputStream(); // Prepara de los bytes del fichero
          System.out.println("Enviando " + ARCHIVO + "(" + mybytearray.length + " bytes)"); 
          salidaBytes.write(mybytearray, 0, mybytearray.length); // Escribe los bytes para transmitir
          salidaBytes.flush(); // Vacía el flujo de salida y fuerza la escritura de los bytes
          System.out.println("Transferencia Completada.");
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          // Al finalizar hace:
          if (buferAlmacenado != null) buferAlmacenado.close(); // Cierra el almacenamiento
          if (salidaBytes != null) salidaBytes.close(); // Cierra la salida 
          if (sock != null) sock.close(); // Cierra la conexión
        }
      }
    } finally {
      if (server != null) server.close();  // Cierra el servidor
    }
  }
}
