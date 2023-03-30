/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package HCP.CheckIn;

import HCP.Bedroom.IBedroom;

/**
 *
 * @author omp
 */
public interface ICheckIn_Customer {
    void inQueue(int customerId);
    IBedroom assignRoomToCustomer(int customerId);
}
