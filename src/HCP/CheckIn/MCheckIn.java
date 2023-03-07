/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.CheckIn;

import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MCheckIn implements ICheckIn {

    private final ReentrantLock rl;
    private final ILog_Customer mLogCustomer;
    
    private MCheckIn(ILog_Customer mLogCustomer) {
        rl = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
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
        try {
            rl.lock();
            mLogCustomer.meh_inQueue(customerId);
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
    }
}
