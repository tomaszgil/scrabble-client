package sample;

import sample.utils.ServerCommunicator;

import java.io.*;
import java.net.*;

import static sample.Main.connector;

public class Connector {

    private Socket clientSocket;
    public OutputStreamWriter outputStreamWriter;
    public InputStreamReader inputStreamReader;
    public BufferedReader bufferedReader;
    public ServerCommunicator serverCommunicator;


    Connector(){
        try {
            clientSocket = new Socket("127.0.0.1", 12345);
            outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(clientSocket.getInputStream(), "UTF-8");
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverCommunicator = new ServerCommunicator();
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
        char buffer[] = new char[sizeOfBuffer];
        connector.inputStreamReader.read(buffer);
        if(buffer[0]==' '){
            System.out.println("kappa");
        }
        String message = String.valueOf(buffer);
        String [] data = message.split("\\_");
        return data;

    }

    public String[] receiveMessage(int sizeOfBuffer, int numberOfUsers) throws IOException{
        char buffer[] = new char[sizeOfBuffer];
        connector.inputStreamReader.read(buffer);

        String message = String.valueOf(buffer);
        String [] data = message.split("\\_");
        String [] user_info = new String[numberOfUsers*2];
        for(int i=0; i<numberOfUsers; i++)
        {
            user_info[i]=data[i];
            System.out.println(data[i]);
        }

        return user_info;

    }
}
