package projet0;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class UDPServeur {
	public static void main(String[] args) throws IOException {
	    DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt("5001"));
		List<ClientHandler> clients = new ArrayList<>();
	    System.out.println("Server Started. Listening for Clients on port 5001" + "...");
	    // Assume messages are not over 1024 bytes
	    byte[] receiveData = new byte[1024];
	    DatagramPacket receivePacket;
	    while (true) {
	      // Server waiting for clients message
	       receivePacket = new DatagramPacket(receiveData, receiveData.length);
	       serverSocket.receive(receivePacket);
	      // Get the client's IP address and port
	      InetAddress IPAddress = receivePacket.getAddress();
	      int port = receivePacket.getPort();
	      // Convert Byte Data to String
	      String clientMessage = new String(receivePacket.getData(),0,receivePacket.getLength());
	      // Print the message with log header
	      
	      System.out.println("[IP: " + IPAddress + " ,Port: " + port +"]  " + clientMessage);
	   // Répondre au client
          //String serverResponse = "Message du serveur : Merci pour le message!";
          //byte[] sendData = serverResponse.getBytes();
          //DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
          //serverSocket.send(sendPacket);
          // Créer un thread pour gérer la communication avec le client 
	      String nom=Integer.toString(port);

          ClientHandler clientHandler = new ClientHandler(serverSocket, receivePacket, clientMessage,clients );
          Thread thread = new Thread(clientHandler);
          clients.add(clientHandler);
          thread.start();
      
	    }
	    
	  }
	 static class ClientHandler implements Runnable {
	        private DatagramSocket serverSocket;
	        private DatagramPacket receivePacket;
	        String clientMessage ;
	        List<ClientHandler> clients;
	       

	        public ClientHandler(DatagramSocket serverSocket, DatagramPacket receivePacket,String mesg,List<ClientHandler> clients) {
	           
	        	this.serverSocket = serverSocket;
	            this.receivePacket = receivePacket;
	            clientMessage=mesg;
	            this.clients=clients;
	          
	        }

	        @Override
	        public void run() {
	            try {
	                InetAddress clientAddress = receivePacket.getAddress();
	                int clientPort = receivePacket.getPort();

	                // Répondre au client
	                //String serverResponse = "Message du serveur : Merci pour le message!";
	                for (ClientHandler client : clients) {
	                    if (client.receivePacket.getPort()!=clientPort) {
	                byte[] sendData =clientMessage .getBytes();
	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.receivePacket.getAddress(), client.receivePacket.getPort());
	                serverSocket.send(sendPacket);}}
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
