package HCP.LeavingHall;

import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MLeavingHall implements ILeavingHall{
    private final ReentrantLock rl;
    private final ReentrantLock rl2;
    private final ILog_Customer mLogCustomer;
    private final Condition cManual;
    private final Condition cRoom;
    //private final Condition cWaiterAlert;
    private int curCustomers = 0;
    private MLeavingHall(ILog_Customer mLogCustomer) {
        rl = new ReentrantLock();
        rl2 = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
       
        cManual = rl2.newCondition();
        cRoom = rl.newCondition();
        //cWaiterAlert = rl3.newCondition();
    }
    /**
     *
     * @param mLogCustomer
     * @return
     */
    public static ILeavingHall getInstance(ILog_Customer mLogCustomer) {
        return new MLeavingHall(mLogCustomer);
    }

    @Override
    public void waitToLeave(int customerId) {
        curCustomers++;
        try {
            mLogCustomer.AwaitingToLeave(customerId);
            rl.lock();
          
                cRoom.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(MLeavingHall.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void comeOut(int count) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
