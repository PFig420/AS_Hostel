/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package HCP.Log;

import java.util.ArrayList;

/**
 *
 * @author omp
 */
public interface ILog_Customer {
   void sendQueue(ArrayList<Integer> q);
   void sendSuspend();
   void sendResume();
   void sendManual();
   void sendStep();
   void sendOpen();
   void sendClose();
   void sendAuto();
   void sendCheckout();
   void sendCheckin();
   void recepcionist(int id);
   void custAsleep(int id, int roomNumber, int floorNumber);
   void goToBath(int customerId, int roomNumber, int floorNumber);
 
}
