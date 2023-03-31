package HCP.LeavingHall;

public interface ILeavingHall_Porter {
    void comeOut( int count );
    void porterWaitToOpenDoor(int porterId, int ttlCustomers);
}
