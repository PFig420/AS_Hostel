/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HCP.ActiveEntity;

import HCP.Bedroom.IBedroom;
import HCP.Bedroom.MBedroom;
import HCP.Bedroom.IBedroom_Customer;
import HCP.CheckIn.ICheckIn_Customer;
import HCP.LeavingHall.ILeavingHall_Customer;
import HCP.MealRoom.IMealRoom;
import HCP.MealRoom.IMealRoom_Customer;
import HCP.Outside.IOutside_Customer;
/**
 *
 * @author omp
 */
public class TCustomer implements Runnable {

    
    private final int customerId;
    private final IOutside_Customer mOutside;
    private final ICheckIn_Customer mCheckIn;
    private IBedroom mBedroom = null;
    private final IMealRoom_Customer mMealRoom;
    private final ILeavingHall_Customer mLeavingHall;
    
    private TCustomer(int customerId, IOutside_Customer mOutside, ICheckIn_Customer mCheckIn, IMealRoom_Customer mMealRoom,ILeavingHall_Customer mLeavingHall) {
        this.customerId = customerId;
        this.mOutside = mOutside;
        this.mCheckIn = mCheckIn;
        this.mMealRoom = mMealRoom;
        this.mLeavingHall = mLeavingHall;
    }
    public static Runnable getInstance(int customerId, IOutside_Customer mOutside, ICheckIn_Customer mCheckIn, IMealRoom mMealRoom, ILeavingHall_Customer mLeavingHall) {
        return new TCustomer(customerId, mOutside, mCheckIn, mMealRoom,mLeavingHall);
    }
    
    public void setBedroom(IBedroom iB){
        this.mBedroom = iB;
    }
    @Override
    public void run() {
        
        mOutside.walkArround(customerId);
        mOutside.waitTurn(customerId);
        mCheckIn.inQueue(customerId);
        IBedroom iB = mCheckIn.assignRoomToCustomer(customerId);
        setBedroom(iB);
        mBedroom.goToSleep(customerId);
        mBedroom.goToBathroom(customerId);
        mMealRoom.sitAtTable(customerId);
        mMealRoom.getBreakfast(customerId);
        mLeavingHall.waitToLeave(customerId);
        // eatBreakfast(customerId) -> mMealRoom
        // loungeAround(customerId) -> mLeavingHall 
        // checkout(customerId) -> mLeavingHall
    }
    
}
