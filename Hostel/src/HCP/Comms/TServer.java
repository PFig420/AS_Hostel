/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import HCP.Log.ILog_CCP;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author omp
 */
public class TServer implements Runnable{
    
    private static Socket clientSocket;
    private final static int PORT = 8082;
    private static int threadId = 1;
    private ServerSocket serverSocket;
    private final ILog_CCP mLogMessage;

    public static Runnable getInstance(int port, ILog_CCP mLogMessage) {
        return new TServer(8081, mLogMessage);
    }
    private TServer(int port, ILog_CCP mLogMessage) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {}
        this.mLogMessage = mLogMessage;
    }
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket( PORT );
        } catch (IOException ex) {
            Logger.getLogger(TServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println( "Server is listening on port: " + PORT );
      
       while( true ) {

            try {
                System.out.println( "Server is accepting a new connection" );
                clientSocket = serverSocket.accept();
               
                new Thread( TControlCentreProxy.getInstance(clientSocket, mLogMessage)).start();
            } catch (IOException ex) {
                Logger.getLogger(TServer.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       }
    }
    
}
