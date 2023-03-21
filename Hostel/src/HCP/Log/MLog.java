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
    fileWrt = new FileWriter(file);

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
            files.write("Files in Java might be tricky, but it is fun enough!");
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
        } finally {
            rl.unlock();
        }
    }

    @Override
    public void isFull(int customerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void out(int order, int count, int customerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
