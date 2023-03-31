/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Outside;

import HCP.CheckIn.MCheckIn;
import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author omp
 */
public class MOutside implements IOutside {

    private int nCustomers = 0;
    private int ttlCustomers = 0;
    private final ReentrantLock rl;
    private final ReentrantLock rl2;
    private final Condition cManual;
    private final Condition cWalkArround;
    private final Condition cWaitTurn;
   
    private boolean manual = false;
    
    private MOutside() {
        rl = new ReentrantLock();
        rl2 = new ReentrantLock();
        cWalkArround = rl.newCondition();
        cWaitTurn = rl.newCondition();
        cManual = rl2.newCondition();
        
    }
    public static IOutside getInstance() {
        return new MOutside();
    }
    public void nextSimulation(int ttlCustomers) {
        try {
            rl.lock();
            this.ttlCustomers = ttlCustomers;
            cWalkArround.signalAll();
        } finally {
            rl.unlock();
            
        }
    }
    @Override
    public void walkArround(int customerId){
        try {
            //System.out.print("Walking around: customer " + customerId+"\n");
            rl.lock();
            while( ttlCustomers == 0 )
                cWalkArround.await();
            ttlCustomers--;
           
        } catch (InterruptedException ex) {
        } finally {
   
            rl.unlock();
        }   
    }
    @Override
    public void comeIn(int nCustomers) {
         if(manual){
             
                try {
                    rl2.lock();
                    System.out.println("Here");
                    cManual.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MCheckIn.class.getName()).log(Level.SEVERE, null, ex);
                }  finally {
                    rl2.unlock();
                }
            
        }
        try {
           
            rl.lock();
            System.out.print("Porter in COmein");
            this.nCustomers = nCustomers;
            cWaitTurn.signalAll();
        } finally {
            rl.unlock();
        }
    }
    /**
     *
     * @param customerId
     */
    @Override
    public void waitTurn(int customerId) {
         
       
        try {
            
            rl.lock();
            while ( nCustomers == 0 ) {
                try {
                    cWaitTurn.await();
                } catch (InterruptedException ex){}
            }
            nCustomers--;
        } finally {
            rl.unlock();
        }
       
    }
    
     @Override
    public void setMode() {
        System.out.println("YO");
        manual = true;
        
    }

    @Override
    public void advanceToNextStep() {
        
        rl2.lock();
        cManual.signalAll();
        rl2.unlock();
    }
}
