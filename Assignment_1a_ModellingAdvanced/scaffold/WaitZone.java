import java.util.ArrayList;

//superclass for types of waitzones
public class WaitZone {

	// number of ships in waitzone
	public static int MAX_NUM_SHIPS;
	protected ArrayList<Ship> ships;
	
	public WaitZone (int maxShips) {
		MAX_NUM_SHIPS = maxShips;
		this.ships =  new ArrayList<>();
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
	
	public synchronized void arrive (Ship ship) {
		while (ships.size() >= MAX_NUM_SHIPS) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		ships.add(ship);
		// notify pilots looking for a ship potentially!
		notifyAll();
		ArrivalMessage( ship);
	}
	
	private void ArrivalMessage(Ship ship){
		System.out.println(ship + "arrives at a wait zone");
	}
	
	private void DepartureMessage(Ship ship){
		System.out.println(ship + "departs from a wait zone");
	}
	
	public synchronized void depart(Ship ship) {
		while (!ships.contains(ship)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		ships.remove(ship);
		notifyAll();
		DepartureMessage( ship);
	}

	public synchronized void depart() {
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
	
}
