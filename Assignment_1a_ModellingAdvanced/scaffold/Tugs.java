// monitor class
// basic level implement: just keeps track of NUMBER of tugs, not tracking indiviudla ones + expects that other
// classes don't just 'return' more tugs than they borrowed + that the other classes don't operate WITHOUT having
// first taken tugs! Just for modelling purposes.

public class Tugs {
	
	private int numFreeTugs;
	
	public Tugs (int numTugs) {
		numFreeTugs = numTugs;
	}
	
	public synchronized int requestTugs(int numTugsRequested) {
		while (numTugsRequested > numFreeTugs) {
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
	
	public synchronized void returnTugs(int numTugsReturned) {
		numFreeTugs += numTugsReturned;
		notifyAll();
	}
	
	public int getNumTugsRemaining() {
		return numFreeTugs;
	}
	
	
}
