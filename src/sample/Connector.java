package sample;

import java.io.*;
import java.net.*;

import static sample.Main.connector;

public class Connector {

    private Socket clientSocket;
    public OutputStreamWriter outputStreamWriter;
    public InputStreamReader inputStreamReader;
    public BufferedReader bufferedReader;

    Connector(){
        try {
            clientSocket = new Socket("127.0.0.1", 12345);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream(), "UTF-8");
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String[] receiveMessage(int sizeOfBuffer) throws IOException{
        char bufor[] = new char[sizeOfBuffer];
        connector.inputStreamReader.read(bufor);

        String message = String.valueOf(bufor);
        String [] data = message.split("\\_");
        return data;
    }
}
