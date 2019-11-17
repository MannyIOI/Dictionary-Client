/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import dictionaryclient.Utility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class Client extends Thread{
    Scanner scn = new Scanner(System.in);
    String tosend;
    Socket s;
    public DataInputStream dis;
    public DataOutputStream dos;
    
    private static Client client;
    
    public static Client getInstance(){
        if(client == null){
            client = new Client();
            client.start();
        }
        return client;
    }
    
    @Override
    public void run(){
        try{
            InetAddress ip = InetAddress.getByName(Utility.SERVER_ADDRESS);
            
            s = new Socket(ip, Utility.PORT);
            // obtaining input and out streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
//            
//            System.out.println("sending request ... " + dos);
//            dos.writeUTF(this.tosend);
//            String received = dis.readUTF();
//            System.out.println(received);
//        
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String sendRequest(String toSend) throws IOException{
        this.dos.writeUTF(toSend);
        return dis.readUTF();
    }
    
    public void close(){
        try{
            client = null;
            dos.writeUTF("Exit");
            s.close();
            dos.close();
            dis.close();
        }
        catch(Exception ex){
            
        }
    }
}
