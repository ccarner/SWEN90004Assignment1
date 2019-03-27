/**
 * Controller class that is responsible for distributing and managing tugs as requested 
 * by pilots for their ships. 
 * Will not give out additional tugs if the berth is completely full to avoid deadlock 
 * (see reflection report)
 * Keeps track of tugs as basic integers (since tugs have no properties and are not
 * unique for the purpose of this simulation)
 * @author ccarn
 *
 */
public class Tugs {
	
	/** number of tugs in the simulation that aren't attached to a ship */
	private int numFreeTugs;
	
	/** berth for the simulation */
	private BerthWaitZone berth;
	
	/** create new tug controller with a number of tugs and the simulation's berth zone*/
	public Tugs (int numTugs, BerthWaitZone berth) {
		numFreeTugs = numTugs;
		this.berth=berth;
	}
	
	/**
	 * Pilots call this method to request tugs for their ship for docking/undocking.
	 * Will reserve enough tugs to undock one ship if the berth is completely full
	 * to avoid deadlock. (see reflection report)
	 * @param numTugsRequested
	 * @param ship
	 * @param pilot
	 * @return number of tugs requested
	 */
	public synchronized int requestTugs(int numTugsRequested, Ship ship, Pilot pilot) {
		while (numTugsRequested > determineFreeTugs(ship)) {
			// there are no free tugs available, need to wait
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// the number of tugs requested are avaialable!
		numFreeTugs -= numTugsRequested;
		System.out.println(""+ pilot + " acquires " + numTugsRequested + " tugs ("
				+ numFreeTugs + " available).");
		return numTugsRequested;
	}
	
	/**
	 * Returns the number of tugs available to a particular ship.
	 * If berth is full, will reserve enough tugs to undock one ship (ie the returned
	 * number of 'free' tugs will be less than the total number of ACTUAL free tugs)
	 * @param ship
	 * @return number of tugs available to offer a given ship
	 */
	private int determineFreeTugs(Ship ship) {
		if (ship.isLoaded()) {
			// the ship hasn't docked + unloaded yet
			if (berth.freeSpots() > 0) {
				// there's an empty berth, so let this ship take the tugs for now,
				// can't deadlock as long as DOCKING_TUGS is >= UNDOCKING_TUGS
				return numFreeTugs;
			} else {
				// berth is full, need to retain enough tugs to undock one ship to
				// prevent deadlock
				return numFreeTugs - Params.UNDOCKING_TUGS;
			}
		} else {
			//ship is unloaded and waiting to leave the berth, give it the tugs if
			// theyre available at all
			return numFreeTugs;
		}
	}
	
	/**
	 * Add tugs back to the pool, notify any pilots waiting on tugs
	 * @param numTugsReturned
	 * @param pilot
	 */
	public synchronized void returnTugs(int numTugsReturned, Pilot pilot) {
		numFreeTugs += numTugsReturned;
		System.out.println(""+ pilot + " releases " + numTugsReturned + " tugs (" + 
				numFreeTugs + " available).");
		// notify any objects waiting on the lock, since they may have been waiting for
		// tugs!
		notifyAll();
	}
	
	public int getNumTugsRemaining() {
		return numFreeTugs;
	}
	
	
}
