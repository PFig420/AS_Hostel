/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package HCP.Log;

/**
 *
 * @author omp
 */
public interface ILog_Customer {
    void meh_inQueue(int customrerId);

    public void isFull(int customerId);

    public void out(int order, int count, int customerId);
    void in( int head, int count, int id );
    void recepcionist(int id);
    void custAsleep(int id, int roomNumber, int floorNumber);

    void goToBath(int customerId, int roomNumber, int floorNumber);

    void satDown(int customerId);

    void waiterReadyToDeliverFood(int waiterId);

    void gotBreakfast(int customerId);

    public void AwaitingToLeave(int customerId);

    public void WaitingToOpenDoor(int porterId);

    public void walking(int customerId);
 
}
