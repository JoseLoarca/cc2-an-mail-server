import java.net.*;
import java.io.*;

public class ServerToServer extends Thread {
	Socket server = null;
    // InetAddress a = null;
    SocketAddress a = null;
    String ingreso = null;

	public ServerToServer(Socket server) {
        this.server = server;
        try {
            a = this.server.getRemoteSocketAddress();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            a = null;
        }
    }
	public void run() {
		System.out.println(a);
		System.out.println("llega a servidor");
	}
}