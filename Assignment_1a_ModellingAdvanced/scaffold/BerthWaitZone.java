/**
 * Berth of the USS EMAFOR. Ships will dock here and unload. In the process they will
 * release all of their tugs post docking, and reaquire more to undock. The shield state
 * is controlled by the Operator class.
 * @author ccarn
 *
 */
public class BerthWaitZone extends WaitZone {

	/** whether the shield around the berth is currently active */
	private Boolean shieldUp = false;
	
	/** create a new berth waitzone with capacity for maxShips */
	public BerthWaitZone(int maxShips) {
		super(maxShips);
	}
	
	/**
	 * override of depart method: ship waits for shields to go down before departing and
	 * simulates undocking time.
	 */
	@Override
	public synchronized void depart(Ship ship) {
		if (!ships.contains(ship)) {
			throw new ShipNotPresentException();
		}
		// can't undock while the shield is up
		waitShieldDown();
		
		// simulate undocking time
		undockSleep();
		
		ships.remove(ship);
		departureMessage(ship);
		// notify ships waiting to arrive
		notifyAll();
	}
	
	/**
	 * override of arrive method: ship waits for shields to go down before arriving and
	 * simulates undocking time.
	 */   
	@Override
	public synchronized void arrive (Ship ship) {
		while (freeSpots() <= 0) {
			// no free spots, make ships wait
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		waitShieldDown();
		
		// simulate docking time
		dockSleep();
		
		ships.add(ship);
		arrivalMessage(ship);
	}
	
	/**
    * Sets state of shield and notifies ships potentially waiting on shield going down
    * @param shieldUp
    */
	public synchronized void setShieldUp(Boolean shieldUp) {
		this.shieldUp = shieldUp;
		notifyAll();
	}
	
	/**
	 * prints an arrival message to the console
	 * @param ship
	 */
	@Override
	public void arrivalMessage(Ship ship){
		System.out.println(ship + " docks at berth.");
	}
	
	/**
	 * prints a departure message to the console
	 * @param ship
	 */
	@Override
	public void departureMessage(Ship ship){
		System.out.println(ship + " undocks from berth.");
	}
	
	/**
	 * simulate unloading time by sleeping thread
	 * @param ship
	 */
	public void simulateUnloading(Ship ship) {
		System.out.println(ship + " being unloaded.");	
		try {
			Thread.sleep(Params.UNLOADING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ship.setLoaded(false);
	}

	/**
	 * Simulates undock time by sleeping thread.
	 */
	private void undockSleep() {
		try {
			Thread.sleep(Params.UNDOCKING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Simulates undock time by sleeping thread.
	 */
   	private void dockSleep() {	
		try {
			Thread.sleep(Params.DOCKING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
   }

   /**
    * Waits thread for shield to go down
	*/
   private void waitShieldDown() {
		while(shieldUp) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
   
   
	

	
	
}
