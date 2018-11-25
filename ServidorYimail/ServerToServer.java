import java.net.*;
import java.io.*;

public class ServerToServer extends Thread {

	public String verifyCount(String count){
		return checkContact(count);
	}

	public void run() {
		System.out.println("llega a servidor");
	}
}