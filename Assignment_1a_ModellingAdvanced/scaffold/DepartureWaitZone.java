
public class DepartureWaitZone extends WaitZone {

	public DepartureWaitZone(int maxShips) {
		super(maxShips);
	}

	public synchronized void removeOneShip() {
		while (ships.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		notifyAll();
		ships.remove(0);
		
	}
	
	public void ArrivalMessage(Ship ship){
		System.out.println(ship + " arrives at departure zone");
	}
	
	public void DepartureMessage(Ship ship){
		System.out.println(ship + " departs departure zone");
	}

}
