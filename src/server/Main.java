package server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj numer portu(liczba):");
        Integer port = sc.nextInt();
        String status = "";
        String clientSentence ="";
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is working on port " + serverSocket.getLocalPort());
        while (!status.equals("n")) {
            System.out.println("Waiting for new client");
            Socket clientSocket = serverSocket.accept();
            try {
                System.out.println("Accepted connection request from :" + clientSocket.getInetAddress().getHostName() + ":" + clientSocket.getPort());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                byte[] messageByte = new byte[1000];
                while (true) {
                    int bytesRead = in.read(messageByte);
                    String messageString = new String(messageByte, 0, bytesRead);
                    System.out.println("Client " + clientSocket.getInetAddress() + ";" + clientSocket.getPort() + " SAYS: " + messageString);
                    out.write(messageByte, 0, bytesRead);
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("Client " + clientSocket.getInetAddress() + ";" + clientSocket.getPort() + " DISCONNECTED. " + e.getMessage());
                System.out.println("Jeśli chcesz żeby serwer byl dalej włączony wpisz \"t\" a jesli chcesz wylaczyc wpisz \"n\"  ");
                status = sc.next();
            }
        }
        if(status.equals("n")){
            serverSocket.close();
            System.out.println("Serwer zatrzymany");
        }
    }
}
