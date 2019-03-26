/**
 * Wait Zone where unloaded ships will depart (picked up by the Collector) and exit the
 * simulation.
 * @author ccarn
 *
 */
public class DepartureWaitZone extends WaitZone {

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
	 * overriden arrive method which notifies the Consumer which could be waiting
	 * for a ship to arrive in order to remove it.
	 */
	@Override
	public synchronized void arrive (Ship ship) {
		super.arrive(ship);
		// notify the consumer if waiting for a ship to arrive before consuming
		notifyAll();
	}
	
	@Override
	public void arrivalMessage(Ship ship){
		// no arrival message for departure zone
	}
	
	@Override
	public void departureMessage(Ship ship){
		System.out.println(ship + " departs departure zone");
	}

}
