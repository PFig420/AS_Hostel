/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Outside;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MOutside implements IOutside {

    private int nCustomers = 0;
    private int ttlCustomers = 0;
    private final ReentrantLock rl;
    private final Condition cWalkArround;
    private final Condition cWaitTurn;
    
    private MOutside() {
        rl = new ReentrantLock();
        cWalkArround = rl.newCondition();
        cWaitTurn = rl.newCondition();
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
        try {
            //System.out.print("Porter in COmein");
            rl.lock();
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
}
