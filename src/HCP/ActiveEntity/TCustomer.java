/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.ActiveEntity;

import HCP.CheckIn.ICheckIn_Customer;
import HCP.Outside.IOutside_Customer;
/**
 *
 * @author omp
 */
public class TCustomer implements Runnable {

    private final int customerId;
    private final IOutside_Customer mOutside;
    private final ICheckIn_Customer mCheckIn;
    
    private TCustomer(int customerId, IOutside_Customer mOutside, ICheckIn_Customer mCheckIn) {
        this.customerId = customerId;
        this.mOutside = mOutside;
        this.mCheckIn = mCheckIn;
    }
    public static Runnable getInstance(int customerId, IOutside_Customer mOutside, ICheckIn_Customer mCheckIn) {
        return new TCustomer(customerId, mOutside, mCheckIn);
    }
    @Override
    public void run() {
        mOutside.walkArround(customerId);
        mOutside.waitTurn(customerId);
        mCheckIn.inQueue(customerId);
        // getBedandRoom(customerId) -> mCheckIn
        // moveIn(customerId, BedId) -> mBedRoom
        // useBathroom(customerId, BedId?maybe) -> mBedRoom
        // goDown(customerId)???
        // eatBreakfast(customerId) -> mMealRoom
        // loungeAround(customerId) -> mLeavingHall 
        // checkout(customerId) -> mLeavingHall
    }
    
}
