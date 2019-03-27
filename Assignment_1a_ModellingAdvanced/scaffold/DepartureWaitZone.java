/**
 * Wait Zone where unloaded ships will depart (picked up by the Collector) and exit the
 * simulation.
 * @author ccarn
 *
 */
public class DepartureWaitZone extends WaitZone {

	/** create a new deaparture waitzone with capacity for maxShips */
	public DepartureWaitZone(int maxShips) {
		super(maxShips);
	}

	/**
	 * Removes one Ship from the departure zone if there is one available, else makes the
	 * calling thread wait.
	 */
	public synchronized void removeOneShip() {
		while (ships.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		departureMessage(ships.get(0));
		ships.remove(0);
	}
	
	/**
	 * Adds ship to the waitzone if there's room, else makes the ship wait.
	 * Notifies the Consumer which could be waiting for a ship to arrive in
	 * order to remove it.
	 */
	@Override
	public synchronized void arrive (Ship ship) {
		super.arrive(ship);
		// notify the consumer if waiting for a ship to arrive before consuming
		notifyAll();
	}
	
	/**
	 * prints a departure message to the console
	 * @param ship
	 */
	@Override
	public void departureMessage(Ship ship){
		System.out.println(ship + " departs departure zone");
	}

}
