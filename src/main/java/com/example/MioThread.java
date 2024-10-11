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
        String stringaRicevuta;

        try {
            do {
                // Leggi la stringa inviata dal client
                stringaRicevuta = in.readLine(); // Legge fino a un carattere di nuova linea

                if (stringaRicevuta == null || stringaRicevuta.equals("!")) {
                    break; // Esci se il client ha chiuso la connessione o ha inviato '!'
                }

                System.out.println("La stringa ricevuta è: " + stringaRicevuta);

                // Trasforma la stringa in maiuscolo e la invia al client
                String stringaMaiuscola = stringaRicevuta.toUpperCase();
                out.writeBytes(stringaMaiuscola + "\n"); // Invia la risposta con una nuova linea
            } while (true);
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
