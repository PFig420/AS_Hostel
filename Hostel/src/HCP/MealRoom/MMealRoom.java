package HCP.MealRoom;


import HCP.ActiveEntity.TWaiter;
import HCP.CheckIn.ICheckIn;
import HCP.CheckIn.MCheckIn;
import HCP.Log.ILog_Customer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MMealRoom implements IMealRoom{
    private final int tableSize = 9;
    private final ReentrantLock rl;
    private final ReentrantLock rl2;
    private final ReentrantLock rl3;
    private final ILog_Customer mLogCustomer;
    private final Condition cManual;
    private final Condition cTable;
    private final Condition cWaiterAlert;
    private boolean manual = false;
    private boolean secondFloorClear = false;
    private int curCustomers = 0;
    private int ttlCustomers = 0;
    private ICheckIn mCheckIn;
    private int tbf;
   
    
    
    private MMealRoom(ILog_Customer mLogCustomer, ICheckIn mCheckIn) {
        rl = new ReentrantLock();
        rl2 = new ReentrantLock();
        rl3 = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
        this.mCheckIn = mCheckIn;
        cManual = rl2.newCondition();
        cTable = rl.newCondition();
        cWaiterAlert = rl3.newCondition();
    }
    /**
     *
     * @param mLogCustomer
     * @return
     */
    public static IMealRoom getInstance(ILog_Customer mLogCustomer,  ICheckIn mCheckIn) {
        return new MMealRoom(mLogCustomer, mCheckIn);
    }
    
    @Override
    public void setttlCustomers(int ttlCustomers){
        this.ttlCustomers = ttlCustomers;
    }
    
    public void sitAtTable(int customerId){
        curCustomers++;
        if(curCustomers%9 == 0 || ttlCustomers - curCustomers == 0){
            try {
           
            rl3.lock();
                
                cWaiterAlert.signalAll();
            } catch( Exception ex ){}
            finally {
                rl3.unlock();
            }
        }
        try {
            mLogCustomer.satDown(customerId);
            rl.lock();
                cTable.await();
                
        
        } catch( Exception ex ){}
        finally {
           
            rl.unlock();
        }
    }
    @Override
    public void deliverFood(int waiterId) {
        
        try {
            
            rl3.lock();
                cWaiterAlert.await();
        } catch( Exception ex ){}
        finally {
            rl3.unlock();
        }
        try {
            mLogCustomer.waiterReadyToDeliverFood(waiterId);
            rl.lock();
          
                cTable.signalAll();
        } finally {
            rl.unlock();
             try {
                    rl3.lock();
                        cWaiterAlert.await();
                } catch( Exception ex ){}
                finally { 
                    rl3.unlock();
                    if (ttlCustomers != 0){
                        deliverFood(waiterId);
                    }
                   
                }
        }
    }

    @Override
    public void getBreakfast(int customerId) {
          try {
            
            mLogCustomer.gotBreakfast(customerId);
            rl.lock();
                Thread.sleep(tbf);
               
                //cSleep.await();
          
        } catch( Exception ex ){}
        finally {
            curCustomers--;
            ttlCustomers--;
            rl.unlock();
            if(curCustomers == 0 && ttlCustomers != 0){
                if(secondFloorClear){
                    if(manual){
                        try {
                            rl2.lock();
                            cManual.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MCheckIn.class.getName()).log(Level.SEVERE, null, ex);
                        }  finally {
                            rl2.unlock();
                        }
                    }
                    mCheckIn.wakeUpFloor3();
                }
                else{
                    if(manual){
                        
                        try {
                            rl2.lock();
                            cManual.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MCheckIn.class.getName()).log(Level.SEVERE, null, ex);
                        }  finally {
                            rl2.unlock();
                        }
                    }
                    mCheckIn.wakeUpFloor2();
                    secondFloorClear = true;
                }
                 
               
            }
           
            
        }  
    }

    @Override
    public void settbf(int tbf) {
        this.tbf = tbf;
    }
    
    @Override
    public void setMode() {
        System.out.println("MODE IS SET");
        manual = true;     
    }
    
    public void advanceToNextStep() {
       
       
        rl2.lock();
            cManual.signalAll();
        rl2.unlock();
         
    }

}
