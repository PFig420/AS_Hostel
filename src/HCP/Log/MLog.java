/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Log;

import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MLog implements ILog {
    private final ReentrantLock rl;
    
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
}
