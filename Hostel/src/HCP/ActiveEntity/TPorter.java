package HCP.ActiveEntity;

import HCP.Outside.IOutside_Porter;
import HCP.CheckIn.ICheckIn_Porter;
import HCP.LeavingHall.ILeavingHall_Porter;

public class TPorter implements Runnable {

    private final int porterId;
    private final IOutside_Porter mOutside;
    private final ICheckIn_Porter mCheckIn;
    private final ILeavingHall_Porter mLeavingHall;
    private final int ttlCustomers;

    private TPorter(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn, int ttlCustomers, ILeavingHall_Porter mLeavingHall) {
        this.porterId = porterId;
        this.mOutside = mOutside;
        this.mCheckIn = mCheckIn;
        this.ttlCustomers = ttlCustomers;
        this.mLeavingHall = mLeavingHall;
    }

    public static Runnable getInstance(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn, int ttlCustomers, ILeavingHall_Porter mLeavingHall) {
        return new TPorter(porterId, mOutside, mCheckIn, ttlCustomers,mLeavingHall);
    }
    
  

    @Override
    public void run() {
        //System.out.print("Total Customers for Porter: "+ ttlCustomers+"\n");
        mOutside.comeIn(this.ttlCustomers);
        mLeavingHall.porterWaitToOpenDoor(porterId, this.ttlCustomers);
    }
    
}
