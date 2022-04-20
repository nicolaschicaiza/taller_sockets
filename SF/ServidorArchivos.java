import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorArchivos {

  public static final int PUERTO_SOCKET = 5005;
  public static final String ARCHIVO = "./enviado.txt";

  public static void main(String[] args) throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    try {
      servsock = new ServerSocket(PUERTO_SOCKET);
      while (true) {
        System.out.println("Esperando Conexion...");
        try {
          sock = servsock.accept();
          System.out.println("Conexion Aceptada - Socket: " + sock);
          // enviar archivos
          File myFile = new File(ARCHIVO);
          byte[] mybytearray = new byte[(int) myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray, 0, mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Enviando " + ARCHIVO + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray, 0, mybytearray.length);
          os.flush();
          System.out.println("Transferencia Completada.");
        } finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock != null) sock.close();
        }
      }
    } finally {
      if (servsock != null) servsock.close();
    }
  }
}
