package HCP.ActiveEntity;

import HCP.Outside.IOutside_Porter;
import HCP.CheckIn.ICheckIn_Porter;

public class TPorter implements Runnable {

    private final int porterId;
    private final IOutside_Porter mOutside;
    private final ICheckIn_Porter mCheckIn;
    private final int ttlCustomers;

    private TPorter(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn, int ttlCustomers) {
        this.porterId = porterId;
        this.mOutside = mOutside;
        this.mCheckIn = mCheckIn;
        this.ttlCustomers = ttlCustomers;
    }

    public static Runnable getInstance(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn, int ttlCustomers) {
        return new TPorter(porterId, mOutside, mCheckIn, ttlCustomers);
    }

    @Override
    public void run() {
        System.out.print("Total Customers for Porter: "+ ttlCustomers+"\n");
        mOutside.comeIn(this.ttlCustomers);
        // CallForCheckIn(int porterId, TCustomer[] customers) -> mCheckIn //Opens door and calls customer into fifo queue for checkin, then closes door
        // CheckOut(int porterId,TCustomer[] customers) -> mLeavingHall    // Opens door to leave
    }
    
}
