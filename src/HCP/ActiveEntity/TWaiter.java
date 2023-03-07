package HCP.ActiveEntity;

import HCP.MealRoom.IMealRoom_Waiter;

public class TWaiter implements Runnable {

    private final int waiterId;
    private final IMealRoom_Waiter mMealRoom;

    private TWaiter(int waiterId, IMealRoom_Waiter mMealRoom) {
        this.waiterId = waiterId;
        this.mMealRoom = mMealRoom;
    }

    public static Runnable getInstance(int waiterId, IMealRoom_Waiter mMealRoom) {
        return new TWaiter(waiterId, mMealRoom);
    }

    @Override
    public void run() {
        
    }
    
}
