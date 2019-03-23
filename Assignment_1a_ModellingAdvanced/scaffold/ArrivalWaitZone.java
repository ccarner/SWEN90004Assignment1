// can rmove this class and the Departure waitzone + the factory and roll into the one waitzone class!
public class ArrivalWaitZone extends WaitZone {

	public ArrivalWaitZone(int maxShips) {
		super(maxShips);
	}
	
	public void arrivalMessage(Ship ship){
		System.out.println(ship + " arrives at arrival zone");
	}
	
	@Override
	public void departureMessage(Ship ship){
		// no departure message for arrival zone
	}
	
	public synchronized void arrive (Ship ship) {
		super.arrive(ship);
		// notify pilots looking for a ship potentially!
		notifyAll();
	}
	
	public synchronized Ship allocatePilot() {
		while (findUnpilotedShip() == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return findUnpilotedShip();
	}
	
	private Ship findUnpilotedShip() {
		for (Ship ship : ships) {
			if (ship.getPilot() == null) {
				return ship;
			}
		}
		return null;
	}
}
