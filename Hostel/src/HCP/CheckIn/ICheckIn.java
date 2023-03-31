/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package HCP.CheckIn;

import HCP.MealRoom.IMealRoom;

/**
 *
 * @author omp
 */
public interface ICheckIn extends ICheckIn_Customer, ICheckIn_Porter, ICheckIn_Receptionist,
                                  ICheckIn_CCP {

    public void advanceToNextStep();

  

    public void wakeUpFloor1(IMealRoom mMealRoom, int ttlCustomers);
    public void wakeUpFloor2();
    public void wakeUpFloor3();
}
