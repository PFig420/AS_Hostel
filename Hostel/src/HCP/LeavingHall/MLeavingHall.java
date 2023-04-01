package HCP.LeavingHall;

import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MLeavingHall implements ILeavingHall{
    private final ReentrantLock rl;
    private final ReentrantLock rl2;
    private final ReentrantLock rlDoor;
    private final ILog_Customer mLogCustomer;
    private final Condition cManual;
    private final Condition cRoom;
    private final Condition cDoor;
    //private final Condition cWaiterAlert;
    private int curCustomers = 0;
    private int ttlCustomers = 0;
    
    private ReentrantLock rlSuspension;
    private final Condition cSuspension;
    private boolean suspend = false;
    
    
    
    
    private MLeavingHall(ILog_Customer mLogCustomer) {
        rl = new ReentrantLock();
        rl2 = new ReentrantLock();
        rlDoor = new ReentrantLock();
        rlSuspension = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
       
        cManual = rl2.newCondition();
        cRoom = rl.newCondition();
        cDoor = rlDoor.newCondition();
        cSuspension = rlSuspension.newCondition();
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
          if(suspend){
            try {
                rlSuspension.lock();
                cSuspension.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(MLeavingHall.class.getName()).log(Level.SEVERE, null, ex);
            }  finally {
                rlSuspension.unlock();
            }
        }
        curCustomers++;
        //System.out.println(curCustomers == ttlCustomers);
        if(curCustomers == ttlCustomers){
            
           
            rlDoor.lock();
                cDoor.signalAll();
            rlDoor.unlock();
        }
        try {
            //mLogCustomer.AwaitingToLeave(customerId);
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

    @Override
    public void porterWaitToOpenDoor(int porterId, int ttlCustomers){
        this.ttlCustomers = ttlCustomers;
       
        try {
            rlDoor.lock();
          
                cDoor.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(MLeavingHall.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rlDoor.unlock();
        }
        //mLogCustomer.WaitingToOpenDoor(porterId);
        rl.lock();
            cRoom.signalAll();
        rl.unlock();
    }

    @Override
    public void suspend() {
        suspend = true;
    }
     @Override
    public void restart() {
        suspend = false;
        rlSuspension.lock();
        cSuspension.signalAll();
        rlSuspension.unlock();
    }
}
