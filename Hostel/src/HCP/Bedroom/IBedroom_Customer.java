package HCP.Bedroom;

public interface IBedroom_Customer{
    boolean isFull();
    void wakeUp();
    void goToBathroom(int customerId);
}
