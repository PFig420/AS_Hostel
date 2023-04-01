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
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
/**
 *
 * @author omp
 */
public class MLog implements ILog {
    private final ReentrantLock rl;
    private FileWriter filewriter;
    
    private MLog() {
        rl = new ReentrantLock();
        try {
            //File file = new File("Hostel/src/HCP/Log/log.txt");
            filewriter = new FileWriter("log.txt");
            writeString("                   (MCI)                   FLOOR 1 (MBR - bed)           FLOOR 2 (MBR - bed)         FLOOR 3 (MBR - bed)");
            writeString("| ST | DR P1 P2 P3 P4 P5 P6 R1 R2 R3 | 11 12 13 21 22 23 31 32 33 | 11 12 13 21 22 23 31 32 33 | 11 12 13 21 22 23 31 32 33 |");
        } catch (IOException ex) {
            //javax.swing.JOptionPane.showMessageDialog(null,"It was not possible to access the log.txt file");
        }
    }
    public static ILog getInstance() {
        return new MLog();
    }
    
    @Override
    public void ccp_idle() {
       writeString("| ID |                               |                            |                            |                            |");
    }
    
    @Override
    public void sendRun() {
        writeString("| RN |                               |                            |                            |                            |");
    }

    public void sendSuspend() {
        writeString("| SP |                               |                            |                            |                            |");
    }

    public void sendResume() {
        writeString("| RS |                               |                            |                            |                            |");
    }

    public void sendManual() {
        writeString("| MN |                               |                            |                            |                            |");
    }

    public void sendStep() {
        writeString("| ST |                               |                            |                            |                            |");
    }

    public void sendAuto() {
        writeString("| AU |                               |                            |                            |                            |");
    }

    public void sendCheckin() {
        writeString("| CI |                               |                            |                            |                            |");
    }

    public void sendCheckout() {
        writeString("| CO |                               |                            |                            |                            |");
    }

    public void recepcionist(int id) {
        if (id == 0) {
            writeString("|    |                      RC       |                            |                            |                            |");
        }
        if (id == 1) {
            writeString("|    |                         RC    |                            |                            |                            |");
        }
        if (id == 2) {
            writeString("|    |                            RC |                            |                            |                            |");
        }
    }

    public void custAsleep(int id, int roomNumber, int floorNumber) {
        if (floorNumber == 1 && roomNumber == 1) {
            writeString("|    |                               | " + id + "                         |                            |                            |");
            writeString("|    |                               |    " + id + "                      |                            |                            |");
            writeString("|    |                               |       " + id + "                   |                            |                            |");
        }
        if (floorNumber == 1 && roomNumber == 2) {
            writeString("|    |                               |          " + id + "                |                            |                            |");
            writeString("|    |                               |             " + id + "             |                            |                            |");
            writeString("|    |                               |                " + id + "          |                            |                            |");      
        }
        if (floorNumber == 1 && roomNumber == 3) {
            writeString("|    |                               |                   " + id + "       |                            |                            |");
            writeString("|    |                               |                      " + id + "    |                            |                            |");
            writeString("|    |                               |                         " + id + " |                            |                            |");
        }
    }

    public void goToBath(int customerId, int roomNumber, int floorNumber) {
        if (floorNumber == 1 && roomNumber == 1) {
            writeString("|    | " + customerId + "       |                            |                            |                            |                             |");
        }
        if (floorNumber == 1 && roomNumber == 2) {
            writeString("|    |    " + customerId + "    |                            |                            |                            |                             |");
        }
        if (floorNumber == 1 && roomNumber == 3) {
            writeString("|    |       " + customerId + " |                            |                            |                            |                             |");
        }
    }
    
    
     private void writeString (String str_to_write) {
        try {
            filewriter.write(str_to_write + "\n");
            //filewriter.close();
            System.out.println(str_to_write);
        } catch (IOException ex) {
            System.out.println("Failed to print to file!");
        }
    }
     
       public void closeWriter() {
        try {
            this.filewriter.close();
        } catch (IOException ex) {
           
        }
    }

    @Override
    public void sendOpen() {
        writeString("|    | OP                            |                            |                            |                            |");
    }

    @Override
    public void sendClose() {
        writeString("|    | CL                            |                            |                            |                            |");
    }

    /**
     *
     * @param q
     */
    @Override
    public void sendQueue(ArrayList<Integer> q) {
        writeString("|    | "+ q.toString() +"  |                            |                            |                            |");
     }

  

    

}
    
    
    

