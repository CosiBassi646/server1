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

    public MioThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Errore nell'inizializzazione degli stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String stringaRicevuta = null;
        String scelta = null;

        // Leggi la stringa inviata dal client
        try {
            do {
                scelta = in.readLine(); 
                switch (scelta) {
                    

                    case "1":// Trasforma la stringa in maiuscolo e la invia al client
                    stringaRicevuta = null;
                    stringaRicevuta = in.readLine();   
                    System.out.println(stringaRicevuta);
                    // stringaRicevuta = in.readLine();  
                    String stringaMaiuscola = stringaRicevuta.toUpperCase();
                    out.writeBytes(stringaMaiuscola + "\n"); // Invia la risposta con una nuova linea   
                    break;
                
                    case "2"://trasfortma la stringa in minuscolo
                    stringaRicevuta = null;
                    stringaRicevuta = in.readLine();   
                    String stringaMinuscola = stringaRicevuta.toLowerCase();
                    out.writeBytes(stringaMinuscola + "\n");//invio risposta
                    
                    break;
              
                    case "3"://mostra la stringa letta al contrario
                    stringaRicevuta = in.readLine();   
                    System.out.println(stringaRicevuta);
                    String reversedStr = "";
                    for (int i = 0; i < stringaRicevuta.length(); i++) {
                    reversedStr = stringaRicevuta.charAt(i) + reversedStr;
                    }
                    out.writeBytes(reversedStr + "\n");
                    break;

                    case "4"://conta il numero di caratteri che compongono la stringa
                    stringaRicevuta = in.readLine();   
                    int lungStringa = stringaRicevuta.length();
                    out.writeBytes("LUNGHEZZA DELLA STRINGA: " + lungStringa + "\n");   
                    break;



                    case "0":
                        break;
                    
                    default:
                        break;
                }
                     
            } while (!scelta.equals("0") );
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
