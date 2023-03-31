/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Log;


import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MLog implements ILog {
    private final ReentrantLock rl;
    boolean append = true;
    File file = new File("logger.txt");  
    //fileWrt = new FileWriter(file);

    //Logger logger = Logger.getLogger("com.javacodegeeks.snippets.core");
    
    private MLog() {
        rl = new ReentrantLock();
    }
    public static ILog getInstance() {
        return new MLog();
    }
    @Override
    public void meh_inQueue(int customerId) {
        try {
            rl.lock();
            System.out.println("Inside the queue "+customerId+"\n");
            //files.write("Files in Java might be tricky, but it is fun enough!");
        } finally {
            rl.unlock();
        }
    }
    /**
     *
     */
    @Override
    public void ccp_idle() {
        try {
            rl.lock();
            System.out.println("I'm idle");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void isFull(int customerId) {
         try {
            rl.lock();
            System.out.println("Queue is Full.\n");
        } finally {
            rl.unlock();
        }
    }
    
    public void in( int head, int count, int id ) {
       
        try {
            rl.lock();
            System.out.println("In: " + head + ", " + count + ", " + id+"\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void out(int order, int count, int customerId) {
         try {
            rl.lock();
            System.out.println("Out: " + order + ", " + count + ", " + customerId+"\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void recepcionist(int id) {
         try {
            rl.lock();
             System.out.print("Recepcionist "+id+" is on.\n");
        } finally {
            rl.unlock();
        }
         
    }

    @Override
    public void custAsleep(int id, int roomNumber, int floorNumber) {
      try {
            rl.lock();
             System.out.print("Customer "+id+" is asleep in room "+floorNumber+roomNumber+"\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void porterCall() {
        try {
            rl.lock();
             System.out.print("Porter call to customers: Service Open\n");
        } finally {
            rl.unlock();
        }
       
    }

    @Override
    public void goToBath(int customerId, int roomNumber, int floorNumber) {
        try {
            rl.lock();
             System.out.print("Customer "+customerId+" has woken up in room "+floorNumber+roomNumber+"\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void satDown(int customerId) {
        try {
            rl.lock();
             System.out.print("Customer "+customerId+" has sat down at breakfast table.\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void waiterReadyToDeliverFood(int waiterId) {
       try {
            rl.lock();
             System.out.print("Waiter "+waiterId+" ready to deliver food.\n");
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void gotBreakfast(int customerId) {
         try {
            rl.lock();
             System.out.print("Customer "+customerId+" got breakfast.\n");
        } finally {
            rl.unlock();
        } }

    @Override
    public void AwaitingToLeave(int customerId) {
         try {
            rl.lock();
             System.out.print("Customer "+customerId+" is waiting to leave.\n");
        } finally {
            rl.unlock();
        } 
    }

    @Override
    public void WaitingToOpenDoor(int porterId) {
        try {
            rl.lock();
             System.out.print("Porter "+porterId+" awaiting to open the door.\n");
        } finally {
            rl.unlock();
        } 
    }

    @Override
    public void walking(int customerId) {
        try {
            rl.lock();
             System.out.print("Customer "+customerId+" is walking outside.\n");
        } finally {
            rl.unlock();
        } 
    }
}
    
    
    

