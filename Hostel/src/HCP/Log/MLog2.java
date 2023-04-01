/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.Log;

import HCP.ActiveEntity.TCustomer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Monitor for Logger
 * @author Pedro Figueiredo 97487 and Renato Dias 98380
 */

public class MLog2 implements ILog_Customer {

    private FileWriter filewriter;

    /**
     * Constructor for the logger class MLog. This constructor creates a FileWriter to the log file and prints the first line.
     */

    public MLog2() {
        try {
            File file = new File("Hostel/src/HCP/Log/log.txt");
            filewriter = new FileWriter(file);
            writeString("                   (MCI)                   FLOOR 1 (MBR - bed)           FLOOR 2 (MBR - bed)         FLOOR 3 (MBR - bed)");
            writeString("| ST | DR P1 P2 P3 P4 P5 P6 R1 R2 R3 | 11 12 13 21 22 23 31 32 33 | 11 12 13 21 22 23 31 32 33 | 11 12 13 21 22 23 31 32 33 |");
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(null,"It was not possible to access the log.txt file");
        }
    }

    public void sendIdle() {
        writeString("| ID |                               |                            |                            |                            |");
    }

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
            this.filewriter.write(str_to_write + "\n");
            System.out.println(str_to_write);
        } catch (IOException ex) {
            System.out.println("Failed to print to file!");
        }
    }

    public void closeWriter() {
        try {
            this.filewriter.close();
        } catch (IOException ex) {
            Logger.getLogger(MLog2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
