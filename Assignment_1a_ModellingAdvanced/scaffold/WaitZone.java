import java.util.ArrayList;

/**
 * Parent class of all waitzones, has a maximum number of ships it can have waiting in it
 * and default wait+depart methods that add and remove ships to the waitzone.
 */
public class WaitZone {

	/** max number of ships in a waitzone */
	public static int MAX_NUM_SHIPS;
	
	/** list of all ships in the waitzone*/
	protected ArrayList<Ship> ships;
	
	/** create a new waitzone with capacity for maxShips */
	public WaitZone (int maxShips) {
		MAX_NUM_SHIPS = maxShips;
		this.ships =  new ArrayList<>();
	}

	/**
	 * Adds ship to the waitzone if there's room, else makes the ship wait
	 * @param ship
	 */
	public synchronized void arrive (Ship ship) {
		while (freeSpots() <= 0) {
			// no room for more ships, make new ships wait
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// now we have room
		ships.add(ship);
		arrivalMessage(ship);
	}
	
	/**
	 * prints an arrival message to the console. Default is no message.
	 * @param ship
	 */
	public void arrivalMessage(Ship ship){
		// default implementation is to not print anything.
	}
	
	/**
	 * prints a departure message to the console. Default is no message.
	 * @param ship
	 */
	public void departureMessage(Ship ship){
		// default implementation is to not print anything.
	}
	
	/**
	 * Removes ship from the waitzone and notifies ships waiting to arrive.
	 * Throws a runtime error if the Ship argument was not IN the waitzone
	 * @param ship
	 */
	public synchronized void depart(Ship ship) {
		if (!ships.contains(ship)) {
			// error: a ship has been asked to depart the waitrzone, but wasn't in it
			// to begin with!
			throw new ShipNotPresentException();
		}
		ships.remove(ship);
		// notify all in case other ships waiting to enter the waitzone.
		notifyAll();
		departureMessage(ship);
	}

	/**
	 * Returns the number of free spots in the waitzone
	 */
	public int freeSpots() {
		return MAX_NUM_SHIPS- ships.size();
	}
	

	

	
}
