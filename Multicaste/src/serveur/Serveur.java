package serveur;
import java.io.IOException;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.UnknownHostException;

import java.util.Scanner;
public class Serveur {
	 final static String GROUP_BROADCAST_ADDRESS = "230.0.0.1";

	    

	    final static int PORT = 8547;  

	    

	    public static void main(String[] args) throws UnknownHostException, InterruptedException {

	    	 		

		   

		    InetAddress addr = InetAddress.getByName(GROUP_BROADCAST_ADDRESS);

		    

			Scanner txt = new Scanner(System.in);

	        String stay ="oui";

	        String msg;

		  

		    

		    try (DatagramSocket serverSocket = new DatagramSocket()) {

		        

		    	while (true){

				    

	                System.out.println("Saisir le texte � envoyer aux clients connect�s ('non' pour quitter) : ");

	                msg = txt.nextLine();              

	                stay = msg;

	                if (stay.equalsIgnoreCase("non")) break;

		

		        

		            DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, addr, PORT);

		            

		          

		            serverSocket.send(msgPacket);

		           

		            

		            System.out.println("Message envoy� : " + msg+"\n\n");

		            Thread.sleep(500);

		        }

		    	System.out.println("FIN DIFFUSION !!!");

		

		    } catch (IOException ex) {	

		        ex.printStackTrace();	

		    }

	    }
}
