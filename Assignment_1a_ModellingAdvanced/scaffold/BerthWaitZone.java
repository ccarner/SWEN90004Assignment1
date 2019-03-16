
public class BerthWaitZone extends WaitZone {

	private Boolean shieldUp = false;
	
	public BerthWaitZone(int maxShips) {
		super(maxShips);
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
		waitShieldDown();
		ships.remove(ship);
	}

	// Added this below since calling berth.wait() from 'operator' threw a 'java.lang.IllegalMonitorStateException'

 
   private void waitShieldDown() {
		while(shieldUp) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void setShieldUp(Boolean shieldUp) {
		this.shieldUp = shieldUp;
		notifyAll();
	}
	
}
