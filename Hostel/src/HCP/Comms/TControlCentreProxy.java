/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Comms;

import HCP.ActiveEntity.TCustomer;
import HCP.ActiveEntity.TPorter;
import HCP.ActiveEntity.TReceptionist;
import HCP.ActiveEntity.TWaiter;
import HCP.CheckIn.ICheckIn;
import HCP.CheckIn.ICheckIn_Customer;
import HCP.CheckIn.ICheckIn_Porter;
import HCP.CheckIn.ICheckIn_Receptionist;
import HCP.CheckIn.MCheckIn;
import HCP.LeavingHall.ILeavingHall;
import HCP.LeavingHall.ILeavingHall_Customer;
import HCP.LeavingHall.ILeavingHall_Porter;
import HCP.LeavingHall.MLeavingHall;
import java.net.Socket;
import HCP.Log.ILog_CCP;
import HCP.Log.ILog_Customer;
import HCP.MealRoom.IMealRoom;
import HCP.MealRoom.IMealRoom_Customer;
import HCP.MealRoom.IMealRoom_Waiter;
import HCP.MealRoom.MMealRoom;
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
import java.util.ArrayList;


/**
 *
 * @author omp
 */
public class TControlCentreProxy implements Runnable {
    
    private int ttlCustomers;
    private final Socket socket;
    private final ILog_CCP mLogMessage;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private IOutside OutCustomer ;
    private ICheckIn CustCheckin = null;
    private IMealRoom mMealRoom = null;
    private final ILeavingHall mLeavingHall;
    private int floorsChecked = 0;
    private int tci,tbr,tbf;
  
    
    private TControlCentreProxy(Socket socket, ILog_CCP mLogMessage) throws IOException {
        this.socket = socket;
        this.mLogMessage = mLogMessage;
        this.out = new ObjectOutputStream( this.socket.getOutputStream() );
        this.in = new ObjectInputStream( this.socket.getInputStream() );
        this.OutCustomer =MOutside.getInstance((ILog_Customer) mLogMessage);
        this.CustCheckin = MCheckIn.getInstance((ILog_Customer) mLogMessage);
        this.mMealRoom = MMealRoom.getInstance((ILog_Customer) mLogMessage, this.CustCheckin);
        this.mLeavingHall = MLeavingHall.getInstance((ILog_Customer) mLogMessage);
        //this.mLogMessage.ccp_idle();
        
    }
    public static Runnable getInstance(Socket socket, ILog_CCP mLogMessage) throws IOException {
        return new TControlCentreProxy(socket, mLogMessage);
    }
    
    private void createNewSimulation(int ttlCustomers, int tci, int tbr, int tbf){
        this.tci = tci;
        this.tbr = tbr;
        this.tbf = tbf;
        mLogMessage.sendRun();
        OutCustomer.nextSimulation(ttlCustomers);
        mMealRoom.setttlCustomers(ttlCustomers);
        CustCheckin.settci(this.tci);
        CustCheckin.settbr(this.tbr);
        mMealRoom.settbf(this.tbf);
        for ( int i=0; i< ttlCustomers; i++)
            new Thread( TCustomer.getInstance(i, (IOutside_Customer)OutCustomer, (ICheckIn_Customer) CustCheckin, (IMealRoom) mMealRoom, (ILeavingHall_Customer) mLeavingHall)).start();
    }
    
    private void callForCheckIn(int[] value){
         if(value[0] == 1){
            
              CustCheckin.setMode();
              OutCustomer.setMode();
          }
         
          new Thread( TPorter.getInstance(0, (IOutside_Porter)OutCustomer, (ICheckIn_Porter) CustCheckin, ttlCustomers, (ILeavingHall_Porter) mLeavingHall)).start();
          for ( int i=0; i< 3; i++)
            new Thread( TReceptionist.getInstance(i, (ICheckIn_Receptionist) CustCheckin)).start();
          
         
    }
    private void callForCheckOut(int[] value){
         if(value[0] == 1){
            
              //CustCheckin.setMode();
              OutCustomer.setMode();
              mMealRoom.setMode();
          }
          /*new Thread( TPorter.getInstance(0, (IOutside_Porter)OutCustomer, (ICheckIn_Porter) CustCheckin, ttlCustomers)).start();
          for ( int i=0; i< 3; i++)
            new Thread( TReceptionist.getInstance(i, (ICheckIn_Receptionist) CustCheckin)).start();*/
          
        
        CustCheckin.wakeUpFloor1(mMealRoom, ttlCustomers);
        
      
    }
    private void suspend(){
        OutCustomer.suspend();
        CustCheckin.suspend();
        mMealRoom.suspend();
        mLeavingHall.suspend();
    }
    private void restart(){
        OutCustomer.restart();
        CustCheckin.restart();
        mMealRoom.restart();
        mLeavingHall.restart();
    }
    @Override
    public void run() {
        
        //Start of program run
     
        mLogMessage.ccp_idle();
       
         while(true) {
             //System.out.println("   Thread  is waiting for a new message" );
            try {
               Message s = (Message) in.readObject();
               //s.print("Simulation");
               
               if (s.code().equals("Next Simulation")){
                   int[] values = s.value();
                   createNewSimulation(values[0],values[1],values[2],values[3]);
                   ttlCustomers = values[0];
               };
               if (s.code().equals("CheckIN")){
                   callForCheckIn(s.value());
               };
                if (s.code().equals("Step")){
                  CustCheckin.advanceToNextStep();
                  OutCustomer.advanceToNextStep();
                  mMealRoom.advanceToNextStep();
               };
                if (s.code().equals("CheckOUT")){
                   callForCheckOut(s.value());
               };
               if (s.code().equals("Suspend")){
                   suspend();
               };
               if (s.code().equals("Resume")){
                   restart();
               };
              
          
               
            
             } catch (IOException ex) {
                Logger.getLogger(TControlCentreProxy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TControlCentreProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
       
    }
        
    
}
