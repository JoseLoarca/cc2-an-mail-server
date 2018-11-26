package Main;

public class ServerToServer extends Thread {

	public String verifyCount(String count){
		DNSes sn = new DNSes();
		return sn.checkContact(count);
	}

	public void run() {
		System.out.println("llega a servidor");
	}
}