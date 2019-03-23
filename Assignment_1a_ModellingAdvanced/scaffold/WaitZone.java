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
		arrivalMessage(ship);
	}
	
	public void arrivalMessage(Ship ship){
		System.out.println(ship + " arrives at a wait zone");
	}
	
	public void departureMessage(Ship ship){
		System.out.println(ship + " departs from a wait zone");
	}
	
	public synchronized void depart(Ship ship) {
		// note this should never happen, but just checking anyway.
		while (!ships.contains(ship)) {
			System.out.println("Error:"+ ship + " asked to depart but wasn't in port. At Waitzone.depart()");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ships.remove(ship);
		// notify all in case other ships waiting to enter the waitzone.
		notifyAll();
		departureMessage(ship);
	}

	

	

	
}
