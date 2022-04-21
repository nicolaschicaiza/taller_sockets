import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClienteArchivos {

  public static final int SOCKET_PORT = 5005; // Definir puerto para el socket
  public static final String SERVER = "127.0.0.1"; // Dirección IP, en este caso se usa el localhost
  public static final String FILE_TO_RECEIVED =
      "./recibido.txt"; // Ruta y nombre del archivo que recibe la información
  // Si se ejecuta en el mismo equipo deben tener diferentes nombres, porque si no se reemplazara

  public static final int FILE_SIZE = 6022386; // Tamaño del fichero temporal codificado
  // Debe ser más grande que el archivo a descargar

  public static void main(String[] args) throws IOException {
    ClienteArchivos app = new ClienteArchivos();
    app.start();
  }

  public void start() throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Conectando...");

      // receive file
      byte[] mybytearray = new byte[FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray, 0, mybytearray.length);
      current = bytesRead;

      do {
        bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
        if (bytesRead >= 0) current += bytesRead;
      } while (bytesRead > -1);

      bos.write(mybytearray, 0, current);
      bos.flush();
      System.out.println(
          "Archivo " + FILE_TO_RECEIVED + " Descargado (" + current + " bytes leidos)");
    } finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }
}
