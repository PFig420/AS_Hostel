package HCP.LeavingHall;

public interface ILeavingHall extends ILeavingHall_Customer, ILeavingHall_Porter {

    public void suspend();

    public void restart();
    
}
