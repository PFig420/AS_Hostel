package HCP.ActiveEntity;

import HCP.MealRoom.IMealRoom_Waiter;

public class TWaiter implements Runnable {

    private final int waiterId;
    private final IMealRoom_Waiter mMealRoom;
    private int ttlCustomers;

    private TWaiter(int waiterId, IMealRoom_Waiter mMealRoom, int ttlCustomers) {
        this.waiterId = waiterId;
        this.mMealRoom = mMealRoom;
        this.ttlCustomers = ttlCustomers;
    }

    public static Runnable getInstance(int waiterId, IMealRoom_Waiter mMealRoom, int ttlCustomers) {
        return new TWaiter(waiterId, mMealRoom, ttlCustomers);
    }
   

    @Override
    public void run() {
        mMealRoom.deliverFood(waiterId);
        // ServeCustomers(int waiterId, Customers) -> mMealRoom
    }
    
}
