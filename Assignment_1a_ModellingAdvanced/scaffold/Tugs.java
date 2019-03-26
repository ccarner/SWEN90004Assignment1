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
	/** number of free tugs that the class can offer to pilots */
	private int numFreeTugs;
	
	/** berth for the simulation */
	private BerthWaitZone berth;
	
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
	 * @return
	 */
	public synchronized int requestTugs(int numTugsRequested, Ship ship, Pilot pilot) {
		while (numTugsRequested > determineFreeTugs(ship)) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		numFreeTugs -= numTugsRequested;
		System.out.println(""+ pilot + " acquires " + numTugsRequested + " tugs ("
				+ numFreeTugs + " available).");
		return numTugsRequested;
	}
	
	/**
	 * Returns the number of tugs available to a particular ship.
	 * If berth is full, will reserve enough tugs to undock one ship (ie the returned
	 * number of 'free' tugs will be less than the total number of ACTUAL free tugs
	 * @param ship
	 * @return
	 */
	private int determineFreeTugs(Ship ship) {
		if (ship.isLoaded()) {
			// the ship hasn't docked + unloaded yet
			if (berth.freeSpots() > 0) {
				// there's an empty berth, so let this ship take the tugs for now,
				// can't deadlock as long as DOCKING_TUGS is >= UNDOCKING_TUGS
				return numFreeTugs;
			} else {
				return numFreeTugs - Params.UNDOCKING_TUGS;
			}
		} else {
			//ship is unloaded and waiting to leave the berth
			return numFreeTugs;
		}
	}
	
	/**
	 * add tugs back to the pool, notify any pilots waiting on tugs
	 * @param numTugsReturned
	 * @param pilot
	 */
	public synchronized void returnTugs(int numTugsReturned, Pilot pilot) {
		numFreeTugs += numTugsReturned;
		System.out.println(""+ pilot + " releases " + numTugsReturned + " tugs (" + 
				numFreeTugs + " available).");
		notifyAll();
	}
	
	public int getNumTugsRemaining() {
		return numFreeTugs;
	}
	
	
}
