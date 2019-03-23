// monitor class
// basic level implement: just keeps track of NUMBER of tugs, not tracking indiviudla ones + expects that other
// classes don't just 'return' more tugs than they borrowed + that the other classes don't operate WITHOUT having
// first taken tugs! Just for modelling purposes.

public class Tugs {
	
	private int numFreeTugs;
	private BerthWaitZone berth;
	
	public Tugs (int numTugs, BerthWaitZone berth) {
		numFreeTugs = numTugs;
		this.berth=berth;
	}
	
	
	public synchronized int requestTugs(int numTugsRequested, Ship ship, Pilot pilot) {
		while (numTugsRequested > determineFreeTugs(ship)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		numFreeTugs -= numTugsRequested;
		System.out.println(""+ pilot + " acquires " + numTugsRequested + " tugs (" + numFreeTugs + " available).");
		return numTugsRequested;
	}
	
	private int determineFreeTugs(Ship ship) {
		if (ship.isLoaded()) {
			// the ship hasn't docked + unloaded yet
			if (berth.freeSpots() != 0) {
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
	
	public synchronized void returnTugs(int numTugsReturned, Pilot pilot) {
		numFreeTugs += numTugsReturned;
		System.out.println(""+ pilot + " releases " + numTugsReturned + " tugs (" + numFreeTugs + " available).");
		notifyAll();
	}
	
	public int getNumTugsRemaining() {
		return numFreeTugs;
	}
	
	
}
