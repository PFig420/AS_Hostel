package HCP.ActiveEntity;

import HCP.Outside.IOutside_Porter;
import HCP.CheckIn.ICheckIn_Porter;

public class TPorter implements Runnable {

    private final int porterId;
    private final IOutside_Porter mOutside;
    private final ICheckIn_Porter mCheckIn;

    private TPorter(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn) {
        this.porterId = porterId;
        this.mOutside = mOutside;
        this.mCheckIn = mCheckIn;
    }

    public static Runnable getInstance(int porterId, IOutside_Porter mOutside, ICheckIn_Porter mCheckIn) {
        return new TPorter(porterId, mOutside, mCheckIn);
    }

    @Override
    public void run() {
        
    }
    
}
