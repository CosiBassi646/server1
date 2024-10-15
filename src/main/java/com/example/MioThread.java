package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class MioThread extends Thread implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private BufferedReader inScelta;

    public MioThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            inScelta = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Errore nell'inizializzazione degli stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String stringaRicevuta = "";
        String scelta = "";

        // Leggi la stringa inviata dal client
        try {
            stringaRicevuta = in.readLine();   
            scelta = inScelta.readLine(); 
            do {
                switch (scelta) {
                    case "1":// Trasforma la stringa in maiuscolo e la invia al client
                    stringaRicevuta = in.readLine();   
                    String stringaMaiuscola = stringaRicevuta.toUpperCase();
                    out.writeBytes(stringaMaiuscola + "\n"); // Invia la risposta con una nuova linea   
                    scelta = inScelta.readLine();   
                    break;
                
                    case "2"://trasfortma la stringa in minuscolo
                    stringaRicevuta = in.readLine();   
                    String stringaMinuscola = stringaRicevuta.toLowerCase();
                    out.writeBytes(stringaMinuscola + "\n");//invio risposta
                    scelta = inScelta.readLine();   
                    break;
              
                    case "3"://mostra la stringa letta al contrario
                    stringaRicevuta = in.readLine();   
                    String reversedStr = "";
                    for (int i = 0; i < stringaRicevuta.length(); i++) {
                    reversedStr = stringaRicevuta.charAt(i) + reversedStr;
                    }
                    out.writeBytes(reversedStr);
                    scelta = inScelta.readLine();   
                    break;

                    case "4"://conta il numero di caratteri che compongono la stringa
                    stringaRicevuta = in.readLine();   
                    int lungStringa = stringaRicevuta.length();
                    out.writeBytes("LUNGHEZZA DELLA STRINGA: " + lungStringa + "\n");   
                    scelta = inScelta.readLine();   
                    break;



                    case "0":
                        break;
                    
                    default:
                        break;
                }
                     
            } while (scelta.equals("0") == false);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il client: " + e.getMessage());
        } finally {
            try {
                socket.close(); // Chiudi il socket
                System.out.println("Connessione terminata");
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del socket: " + e.getMessage());
            }
        }
    }
}
