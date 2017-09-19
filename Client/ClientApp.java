import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/*
 * Two screens - Server/Loggin Screen
 * Messaging screen
 * Server/Loggin Screen passes info to Messaging screen through Client App. 
 * 
 * 
 */

/*
 * This Class will not only be the starting point of the program... but also act as a "holder" for the IP and Port info
 * as well as login information
 */

public class ClientApp {
	private String port;
	private int portnum;
	private Socket server;
	private String IP = "localhost";
	private PrintWriter out;
	private BufferedReader in;
	private Scanner input;
	public ClientApp() {
		// super("ClientApp");
		 input = new Scanner(System.in);
		 System.out.println("Press enter to use default values.");
		 System.out.println("Please enter the IP Address/Domain name of server: ");
		 IP = input.nextLine();
		 if(IP.length()<=2)
			 IP = "localhost";
		 System.out.println("Please enter the port of the server(DEFAULT:54000): ");
		 port = input.nextLine();
		 try{
			 portnum = Integer.parseInt(port);
		 }catch(java.lang.NumberFormatException e){
			 System.err.println("Error... a character was entered using default port of 54000");
			 portnum = 54000;
		 }

		 
		try {
			server = new Socket(InetAddress.getByName(IP), portnum);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		if(server==null){
			System.err.println("Apparently we can't get a connection... go figure");
			return;
		}
		try {
			runClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class updateConsole extends Thread {
		@Override
		public void run() {
			while(true){
				try {
					String x = in.readLine();
					if(x==null){
						break;
					}
					System.out.println(x);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
							
	}
	private void runClient() throws IOException {
			in = new BufferedReader(new InputStreamReader(server.getInputStream())); //incoming messages
		    out = new PrintWriter(server.getOutputStream(), true); //outgoing messages
			updateConsole x = new updateConsole();
			x.start();
			while(true){
					String t = input.nextLine();
					out.println(t);
			}
			
	}

	public static void main(String[] args) {
		new ClientApp();
	}

}
