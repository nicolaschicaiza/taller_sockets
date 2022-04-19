import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorTcp {
  private ServerSocket server;
  private ArrayList<Socket> cliente;
  private Socket aux;

  public static void main(String[] args) throws IOException {
    ServidorTcp app = new ServidorTcp();
    app.init();
  }

  public void init() throws IOException {
    server = new ServerSocket(5000);
    cliente = new ArrayList<Socket>();
    int cont = 0;

    while (true) {
      System.out.println(
          "Esperando conexiones en "
              + server.getLocalSocketAddress()); // Direccion local del servidor -- IP address
      aux = server.accept(); // Crea el socket para el cliente

      cliente.add(aux); // Lo agrega al registro de socket de clientes

      cont++; // contador de registro
      System.out.println(
          "Cliente " + cont + " conectado desde " + aux.getRemoteSocketAddress()); // Muestra
    }
  }
}
