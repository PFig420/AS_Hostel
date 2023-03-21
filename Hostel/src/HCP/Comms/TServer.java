/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import HCP.Log.ILog_CCP;
/**
 *
 * @author omp
 */
public class TServer implements Runnable{

    private ServerSocket serverSocket;
    private Socket socket;
    private final ILog_CCP mLogMessage;

    public static Runnable getInstance(int port, ILog_CCP mLogMessage) {
        return new TServer(port, mLogMessage);
    }
    private TServer(int port, ILog_CCP mLogMessage) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {}
        this.mLogMessage = mLogMessage;
    }
    @Override
    public void run() {
      
       while( true ) {
           /*System.out.print("A");
           try {
               socket = serverSocket.accept();
           } catch (IOException ex) {}
           System.out.print("AAAAAAAAAAAAAAA");*/
           new Thread( TControlCentreProxy.getInstance(socket, mLogMessage)).start();
       }
    }
    
}
