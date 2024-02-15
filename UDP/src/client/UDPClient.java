package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
	public static void main(String[] args)  {
		System.out.println("sasie le nom");
        Scanner sc = new Scanner(System.in);
        String nom = sc.nextLine();

		try {
	            DatagramSocket clientSocket = new DatagramSocket();

	            InetAddress serverAddress = InetAddress.getByName("localhost");
	            int serverPort = 5001;

	            Scanner scanner = new Scanner(System.in);

	            while (true) {
	                System.out.print("Entrez un message (ou 'exit' pour quitter) : ");
	                String message = nom+"  envoyer: "+scanner.nextLine();

	                if (message.equals("exit")) {
	                   break;
	                }

	                byte[] sendData = message.getBytes();
	                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
	                clientSocket.send(sendPacket);

	                byte[] receiveData = new byte[1024];
	                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	                clientSocket.receive(receivePacket);

	                String responseMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
	                System.out.println(responseMessage);
	            }

	            clientSocket.close();
	            scanner.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
		
		
	}

