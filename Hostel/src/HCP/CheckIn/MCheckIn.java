/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.CheckIn;

import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MCheckIn implements ICheckIn {

    private final ReentrantLock rl;
    private final ILog_Customer mLogCustomer;
    private final Condition cIsFull;
    private final Condition cWakeUp;
    private final int size = 6;
    private int head = 0;
    private int tail = 0;
    private int wakeUp = 0;
    private int count = 0;
    
    
    private MCheckIn(ILog_Customer mLogCustomer) {
        rl = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
        cIsFull = rl.newCondition();
        cWakeUp = rl.newCondition();
    }
    /**
     *
     * @param mLogCustomer
     * @return
     */
    public static ICheckIn getInstance(ILog_Customer mLogCustomer) {
        return new MCheckIn(mLogCustomer);
    }
    @Override
    public void inQueue(int customerId){
        int order;
        try {
            rl.lock();
            mLogCustomer.meh_inQueue(customerId);
            while ( isFull() ) {
                Message.isFull(id);
                cIsFull.await();
            }
            Message.in( head, count, id);
            count++;
            order = head++;
            while ( wakeUp == 0 || order != tail )
                cWakeUp.await();
            wakeUp--;
            tail++;
            if ( wakeUp > 0 && isNotEmpty() )
                cWakeUp.signalAll();
            if ( isFull() )
                cIsFull.signalAll();
            count--;
            Message.out(order, count, id);
        } finally {
            rl.unlock();
        }
    }
    /**
     *
     * @param receptionistId
     */
    @Override
    public void nextCustomer(int receptionistId) {
        try {
            rl.lock();
            cWakeUp.signalAll();
            Message.signal(wakeUp);
            wakeUp++;
       } catch( Exception ex ){}
        finally {
            rl.unlock();
        }
    }
    @Override
    public void callCustomers(int porterId) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'callCustomers'");
        
    }
    private boolean isFull() {
        return size == count;
    }
    private boolean isNotEmpty() {
        return count != 0;
    }
}
