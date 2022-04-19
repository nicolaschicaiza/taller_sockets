import java.io.IOException;
import java.net.Socket;

public class ClienteTcp {
  private Socket socket;
  String ip = "0.0.0.0";
  int port = 5000;

  public static void main(String[] args) throws IOException {
    ClienteTcp app = new ClienteTcp();
    app.start();
  }

  public void start() throws IOException {
    System.out.println("[Client] Iniciando cliente TCP");
    socket = new Socket(ip, port);
    System.out.println("[Client] Cliente TCP conectado");

    socket.close();
  }
}
