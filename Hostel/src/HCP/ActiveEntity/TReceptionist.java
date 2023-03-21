package HCP.ActiveEntity;

import HCP.CheckIn.ICheckIn_Receptionist;

public class TReceptionist implements Runnable {

    private final int receptionistId;
    private final ICheckIn_Receptionist mCheckIn;

    private TReceptionist(int receptionistId, ICheckIn_Receptionist mCheckIn) {
        this.receptionistId = receptionistId;
        this.mCheckIn = mCheckIn;
    }

    public static Runnable getInstance(int receptionistId, ICheckIn_Receptionist mCheckIn) {
        return new TReceptionist(receptionistId, mCheckIn);
    }

    @Override
    public void run() {
        // AssignRoomToCustomerAtCheckIn(int recepcionistId, TCustomer customer)
        
        
    }
    
}
