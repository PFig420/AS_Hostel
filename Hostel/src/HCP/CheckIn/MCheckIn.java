/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.CheckIn;

import HCP.ActiveEntity.TWaiter;
import HCP.Bedroom.IBedroom;
import HCP.Bedroom.MBedroom;
import HCP.Log.ILog_Customer;
import HCP.MealRoom.IMealRoom;
import HCP.MealRoom.IMealRoom_Waiter;
import Utils.Message;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author omp
 */
public class MCheckIn implements ICheckIn {

   
    private ReentrantLock rl;
    private final ReentrantLock rl2;
    private final ILog_Customer mLogCustomer;
    private final Condition cIsFull;
    private final Condition cWakeUp;
    private final Condition cRecepcionist;
    private final Condition cManual;
    private IMealRoom mMealRoom = null;
    private int mrCustomers = 0;
    private boolean manual = false;
    private final int size = 6;
    private int head = 0;
    private int tail = 0;
    private int wakeUp = 0;
    private int count = 0;
    
    private int justGotRoom = 0;
    private IBedroom[] floor1 = {null, null, null};
    private IBedroom[] floor2 = {null, null, null};
    private IBedroom[] floor3 = {null, null, null};
    
    
    private MCheckIn(ILog_Customer mLogCustomer) {
        rl = new ReentrantLock();
        rl2 = new ReentrantLock();
        this.mLogCustomer = mLogCustomer;
        cIsFull = rl.newCondition();
        cWakeUp = rl.newCondition();
        cRecepcionist = rl.newCondition();
        cManual = rl2.newCondition();
        for (int i = 0; i < 3; i++) {
            floor1[i] = MBedroom.getInstance((ILog_Customer)mLogCustomer, i, 1);
            floor2[i] = MBedroom.getInstance((ILog_Customer)mLogCustomer, i, 2);
            floor3[i] = MBedroom.getInstance((ILog_Customer)mLogCustomer, i, 3);
        }

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
        rl.lock();
        mLogCustomer.meh_inQueue(customerId);
        while ( isFull() ) {
            try {
                 if(isFull()){
                        mLogCustomer.isFull(tail);
                 }
                
                cIsFull.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(MCheckIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        mLogCustomer.in( head, count, customerId);
        count++;
        order = head++;
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
        while ( wakeUp == 0 || order != tail )
            try {
                cWakeUp.await();
            } catch (InterruptedException ex) {
                Logger.getLogger(MCheckIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        wakeUp--;
        tail++;
        if ( wakeUp > 0 && isNotEmpty() )
            cWakeUp.signalAll();
        if ( isFull() )
            cIsFull.signalAll();
        count--;
        mLogCustomer.out(order, count, customerId);
        /*for(int k=0; k<3; k++){
        System.out.println(floor1[k].toString());
        };*/
        rl.unlock();
    }
    /**
     *
     * @param receptionistId
     */
    @Override
    public void nextCustomer(int receptionistId) {
        try {
            mLogCustomer.recepcionist(receptionistId);
            rl.lock();
            
            cWakeUp.signalAll();
            //Message.signal(wakeUp);
            wakeUp++;
       } catch( Exception ex ){}
        finally {
            rl.unlock();
                try {
                    rl.lock();
                    cRecepcionist.await();
                } catch( Exception ex ){}
                finally { 
                    rl.unlock();
                    if (count != 0){
                        nextCustomer(receptionistId);
                    }
                   
                }
            
        }
    }
   
    private boolean isFull() {
        return size == count;
    }
    private boolean isNotEmpty() {
        return count != 0;
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public IBedroom assignRoomToCustomer(int customerId) {
        try {
            //mLogCustomer.recepcionist(receptionistId);
            rl.lock();
            
            for (int i = 0; i < 3; i++) {
               if(!floor1[i].isFull()){ // se o quarto ainda tem camas vazias
                   justGotRoom += 1;
                   floor1[i].newCustomer();
                   if (justGotRoom == 3){
                       cRecepcionist.signalAll();
                       justGotRoom = 0;
                   }
                   return floor1[i];
               }
            }
            for (int i = 0; i < 3; i++) {
               if(!floor2[i].isFull()){ // se o quarto ainda tem camas vazias
                   justGotRoom += 1;
                   floor2[i].newCustomer();
                    if (justGotRoom == 3){
                       cRecepcionist.signalAll();
                       justGotRoom = 0;
                   }
                   return floor2[i];
               }
            }
            for (int i = 0; i < 3; i++) {
               if(!floor3[i].isFull()){ // se o quarto ainda tem camas vazias
                   justGotRoom += 1;
                   floor3[i].newCustomer();
                    if (justGotRoom == 3){
                       cRecepcionist.signalAll();
                       justGotRoom = 0;
                   }
                   return floor3[i];
               }
            }          
          
       } catch( Exception ex ){}
        finally {
            rl.unlock();
        }
        return null;
    }

    @Override
    public void setMode() {
        manual = true;
        
    }

    @Override
    public void advanceToNextStep() {
       
        rl2.lock();
        cManual.signalAll();
        rl2.unlock();
    }

    @Override
    public void wakeUpFloor1(IMealRoom mMealRoom, int ttlCustomers) {
        this.mMealRoom = mMealRoom;
        this.mrCustomers = ttlCustomers;
        new Thread( TWaiter.getInstance(0, (IMealRoom_Waiter) mMealRoom, ttlCustomers)).start();
        for (int i = 0; i < 3; i++) {
            floor1[i].wakeUp();   
        }
    }
    
    @Override
    public void wakeUpFloor2() {
        for (int i = 0; i < 3; i++) {
            new Thread( TWaiter.getInstance(0, (IMealRoom_Waiter) mMealRoom, mrCustomers)).start();
            floor2[i].wakeUp();   
        }
    }
    
    @Override
    public void wakeUpFloor3() {
        for (int i = 0; i < 3; i++) {
            new Thread( TWaiter.getInstance(0, (IMealRoom_Waiter) mMealRoom, mrCustomers)).start();
            floor3[i].wakeUp();   
        }
    }
    
}
