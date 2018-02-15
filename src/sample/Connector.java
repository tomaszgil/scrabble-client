package sample;

import java.io.*;
import java.net.*;

public class Connector {

    private Socket clientSocket;

    Connector(){
        try {
            clientSocket = new Socket("127.0.0.1", 12345);
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
