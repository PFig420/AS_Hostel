package HCP.Bedroom;

import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MBedroom implements IBedroom {
    private final int nBeds = 3;
    private final int nBaths = 1;
    private int customersInRoom = 0;
    private int roomNumber;
    private int floorNumber;
    private int tbr;
    
    
    
    private final ReentrantLock rl;
    private final ILog_Customer mLogCustomer;
    private final Condition cIsFull;
    private final Condition cSleep;
    
    private MBedroom(ILog_Customer mLogCustomer, int id, int floorNum) {
        rl = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
        cIsFull = rl.newCondition();
        cSleep = rl.newCondition();
        this.roomNumber = id;
        this.floorNumber = floorNum;
    }
    /**
     *
     * @param mLogCustomer
     * @return
     */
    public static IBedroom getInstance(ILog_Customer mLogCustomer, int id, int floorNum) {
        return new MBedroom(mLogCustomer, id, floorNum);
    }
    
    public void newCustomer(){
        this.customersInRoom = this.customersInRoom + 1;
    }
    
    public void goToSleep(int customerId){
        try {
            
            mLogCustomer.custAsleep(customerId, roomNumber, floorNumber);
            rl.lock();
            
                cSleep.await();
          
        } catch( Exception ex ){}
        finally {
            rl.unlock();
             
        }
    }
    @Override
    public void goToBathroom(int customerId) {
        try {
            
            mLogCustomer.goToBath(customerId, roomNumber, floorNumber);
            rl.lock();
                Thread.sleep(tbr);
                customersInRoom--;
                //cSleep.await();
          
        } catch( Exception ex ){}
        finally {
            
            rl.unlock();
            
        }
        
    }
    @Override
    public void wakeUp() {
          try {
            rl.lock();
                cSleep.signalAll();
       } catch( Exception ex ){}
        finally {
            rl.unlock();
        }
    }
   
    
    
    
    
    
    
    @Override
    public String toString(){
        
        return "Floor "+ floorNumber+", Room "+ roomNumber;
        
    }

    @Override
    public boolean isFull() {
       return nBeds == customersInRoom;
    }
    
    @Override
    public void settbr(int tbr){
        this.tbr = tbr;
    }


}
