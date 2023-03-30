/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import HCP.ActiveEntity.TCustomer;
import HCP.ActiveEntity.TPorter;
import HCP.ActiveEntity.TReceptionist;
import HCP.CheckIn.ICheckIn;
import HCP.CheckIn.ICheckIn_Customer;
import HCP.CheckIn.ICheckIn_Porter;
import HCP.CheckIn.ICheckIn_Receptionist;
import HCP.CheckIn.MCheckIn;
import java.net.Socket;
import HCP.Log.ILog_CCP;
import HCP.Log.ILog_Customer;
import HCP.Outside.IOutside;
import HCP.Outside.IOutside_Customer;
import HCP.Outside.IOutside_Porter;
import HCP.Outside.MOutside;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.Message;
import Utils.IMessage;


/**
 *
 * @author omp
 */
public class TControlCentreProxy implements Runnable {
    
    int ttlCustomers;
    private final Socket socket;
    private final ILog_CCP mLogMessage;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private IOutside OutCustomer = MOutside.getInstance();
    private ICheckIn CustCheckin = null;
    
    private TControlCentreProxy(Socket socket, ILog_CCP mLogMessage) throws IOException {
        this.socket = socket;
        this.mLogMessage = mLogMessage;
        this.out = new ObjectOutputStream( this.socket.getOutputStream() );
        this.in = new ObjectInputStream( this.socket.getInputStream() );
        this.CustCheckin = MCheckIn.getInstance((ILog_Customer) mLogMessage);
        
    }
    public static Runnable getInstance(Socket socket, ILog_CCP mLogMessage) throws IOException {
        return new TControlCentreProxy(socket, mLogMessage);
    }
    
    private void createNewSimulation(int ttlCustomers, int tci, int tbr, int tbf){
       
        OutCustomer.nextSimulation(ttlCustomers);
        for ( int i=0; i< ttlCustomers; i++)
            new Thread( TCustomer.getInstance(i, (IOutside_Customer)OutCustomer, (ICheckIn_Customer) CustCheckin)).start();
    }
    
    private void callForCheckIn(){
          new Thread( TPorter.getInstance(0, (IOutside_Porter)OutCustomer, (ICheckIn_Porter) CustCheckin, ttlCustomers)).start();
          for ( int i=0; i< 3; i++)
            new Thread( TReceptionist.getInstance(i, (ICheckIn_Receptionist) CustCheckin)).start();
   
          
    }
    @Override
    public void run() {
        
        //Start of program run
     
        mLogMessage.ccp_idle();
       
         while(true) {
             System.out.println("   Thread  is waiting for a new message" );
            try {
               Message s = (Message) in.readObject();
               s.print("Simulation");
               
               if (s.code().equals("Next Simulation")){
                   int[] values = s.value();
                   createNewSimulation(values[0],values[1],values[2],values[3]);
                   ttlCustomers = values[0];
               };
               if (s.code().equals("CheckIN")){
                   callForCheckIn();
               };
          
               
            
             } catch (IOException ex) {
                Logger.getLogger(TControlCentreProxy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TControlCentreProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
       
    }
        
    
}
