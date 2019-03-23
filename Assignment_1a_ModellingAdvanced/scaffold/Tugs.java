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
	
	
	public synchronized int requestTugs(int numTugsRequested) {
		// FIX THIS SO THAT when a ship that's IN the berth it can still recruit tugs.
		//while (numTugsRequested > unconditionallyFreeTugs()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		numFreeTugs -= numTugsRequested;
		return numTugsRequested;
	}
	
	private int unconditionallyFreeTugs() {
		if (berth.freeSpots() != 0) {
			return numFreeTugs;
		} else {
			return numFreeTugs - Params.DOCKING_TUGS;
		}
	}
	
	public synchronized void returnTugs(int numTugsReturned) {
		numFreeTugs += numTugsReturned;
		notifyAll();
	}
	
	public int getNumTugsRemaining() {
		return numFreeTugs;
	}
	
	
}
