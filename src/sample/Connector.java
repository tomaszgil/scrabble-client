package sample;

import java.io.*;
import java.net.*;

public class Connector {

    private Socket clientSocket;
    public OutputStreamWriter outputStreamWriter;
    public InputStreamReader inputStreamReader;
    public BufferedReader bufferedReader;
    public int skipValue=0;

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

}
