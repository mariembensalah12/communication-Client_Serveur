package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
	  public static void main(String[] args) {
	        try (Socket socket = new Socket("localhost", 5555)) {
	            System.out.println("Connecté au serveur de chat.");
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	            System.out.print("Entrer votre nom : ");
	            Scanner scanner = new Scanner(System.in);
	            String nomClient = scanner.nextLine();
	            out.println(nomClient);

	            Thread lectureMessages = new Thread(new LectureMessages(in));
	            lectureMessages.start();

	            while (true) {
	                String message = scanner.nextLine();
	                out.println(message);
	            }
	        } catch (IOException e) {
	            System.err.println("Erreur du client : " + e.getMessage());
	        }
	    }

	    private static class LectureMessages implements Runnable {
	        private BufferedReader in;

	        public LectureMessages(BufferedReader in) {
	            this.in = in;
	        }

	        public void run() {
	            try {
	                String message;
	                while ((message = in.readLine()) != null) {
	                    if (message.startsWith("BIENVENUE")) {
	                        System.out.println(message);
	                    } else if (message.startsWith("ARRIVEE")) {
	                        String[] parts = message.split(" ");
	                        String nouveauClient = parts[1];
	                        System.out.println(nouveauClient + " a rejoint la discussion.");
	                    } else if (message.startsWith("DEPART")) {
	                        String[] parts = message.split(" ");
	                        String clientQuitte = parts[1];
	                        System.out.println(clientQuitte + " a quitté la discussion.");
	                    } else {
	                        System.out.println(message);
	                    }
	                }
	            } catch (IOException e) {
	                System.err.println("Erreur de lecture des messages : " + e.getMessage());
	            }
	        }
	    }
}
