
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
		departureMessage(ships.get(0));
		ships.remove(0);
		
	}
	
	public void arrivalMessage(Ship ship){
		// no arrival message for departure zone
	}
	
	public void departureMessage(Ship ship){
		System.out.println(ship + " departs departure zone");
	}

}
