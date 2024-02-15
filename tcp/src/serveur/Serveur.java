package serveur;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
public class Serveur {
	private static final int PORT = 5555;
    private static final Map<String, PrintWriter> clients = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en attente de connexions...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion.");

                Thread clientThread = new Thread(new GestionClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }

    private static class GestionClient implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private String nomClient;

        public GestionClient(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                nomClient = in.readLine();
                System.out.println(nomClient + " a rejoint la discussion.");

                envoyerATous("ARRIVEE " + nomClient);

                clients.put(nomClient, out);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/msg")) {
                        envoyerMessagePrive(message);
                    } else {
                        envoyerATous(nomClient + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur de gestion du client : " + e.getMessage());
            } finally {
                if (nomClient != null) {
                    System.out.println(nomClient + " a quitté la discussion.");
                    clients.remove(nomClient);
                    envoyerATous("DEPART " + nomClient);
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void envoyerATous(String message) {
            for (PrintWriter client : clients.values()) {
                client.println(message);
            }
        }

        private void envoyerMessagePrive(String message) {
            String[] parts = message.split(" ");
            if (parts.length >= 3) {
                String destinataire = parts[1];
                String contenuMessage = message.substring(parts[0].length() + parts[1].length() + 2);
                if (clients.containsKey(destinataire)) {
                    clients.get(destinataire).println("(Message privé de " + nomClient + ") " + contenuMessage);
                    out.println("(À " + destinataire + ") " + contenuMessage);
                } else {
                    out.println("Utilisateur non trouvé : " + destinataire);
                }
            } else {
                out.println("Usage : /msg <destinataire> <message>");
            }
        }
    }
}
