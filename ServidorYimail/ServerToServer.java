import java.net.*;
import java.io.*;

public class ServerToServer extends Thread {

	public String verifyCount(String count){
		DNSes contact = new DNSes();
		return contact.checkContact(count);
	}

	public void run() {
		System.out.println("llega a servidor");
	}
}