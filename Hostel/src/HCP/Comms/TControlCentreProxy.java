/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import java.net.Socket;
import HCP.Log.ILog_CCP;

/**
 *
 * @author omp
 */
public class TControlCentreProxy implements Runnable {

    private final Socket socket;
    private final ILog_CCP mLogMessage;
    
    private TControlCentreProxy(Socket socket, ILog_CCP mLogMessage) {
        System.out.println("IN CONTROLCENTRE PROXY");
        this.socket = socket;
        this.mLogMessage = mLogMessage;
    }
    public static Runnable getInstance(Socket socket, ILog_CCP mLogMessage) {
        return new TControlCentreProxy(socket, mLogMessage);
    }
    @Override
    public void run() {
        mLogMessage.ccp_idle();
        while(true) {
            
        }
    }
    
}
