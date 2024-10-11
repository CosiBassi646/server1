package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("server avviato!");
        ServerSocket s1 = new ServerSocket(3000);
        Socket s = s1.accept(); 
        System.out.println("un client si è collegato");

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        
        

        do{
            String stringaRicevuta = in.readLine();
            System.out.println("La stringa ricevuta è: " + stringaRicevuta);
            if(stringaRicevuta.equals("!")){
                break;
            }
            String stringaMaiuscola = stringaRicevuta.toUpperCase();
            out.writeBytes(stringaMaiuscola + "\n");
        }while(true);

        s.close();
        s1.close();

        
    }
}