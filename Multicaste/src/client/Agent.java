package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Agent {
	  final static String INET_ADDR = "230.0.0.1";
	    final static int PORT = 8547;
	    private static String name;
	    private Scanner scanner;
	 

	   
	   
	    void getInput(){
	        scanner = new Scanner(System.in);    
	System.out.print("Enter your username: ");
	name = scanner.nextLine();
	       
	    }
	   
	   
	   
	   
	   
	   
	   
	   
	    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws UnknownHostException {
	    Agent a = new Agent();
	    
	   
	        InetAddress address = InetAddress.getByName(INET_ADDR);

	        byte[] buf = new byte[256];

	        try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
	        a.getInput();
	       
	            clientSocket.joinGroup(address);      

	            while (true) {
	           
	                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
	                clientSocket.receive(msgPacket);
	                String msg = new String(buf, 0, buf.length);
	              
	                System.out.println("Vous avez 1 message recu : " + msg);
	               

	            }
	       

	        } catch (IOException ex) {

	            ex.printStackTrace();

	        }
	       
	        }
}
