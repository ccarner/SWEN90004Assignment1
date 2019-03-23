
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
		
		
		try {
			Thread.sleep(Params.UNDOCKING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		DepartureMessage(ship);
		ships.remove(ship);
	}

	// Added this below since calling berth.wait() from 'operator' threw a 'java.lang.IllegalMonitorStateException'

 
   private void waitShieldDown() {
		while(shieldUp) {
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
   
   // don't allow ships to berth while one still unberthing I think!
   @Override
   public synchronized void arrive (Ship ship) {
		while (ships.size() >= MAX_NUM_SHIPS) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// add now to ensure no other ship tries to dock while we're berthing
		waitShieldDown();
		ships.add(ship);		
	}
   
   public void dock(Ship ship) {
	   try {
			Thread.sleep(Params.DOCKING_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   ArrivalMessage(ship);
	}
   
	public synchronized void setShieldUp(Boolean shieldUp) {
		this.shieldUp = shieldUp;
		notifyAll();
	}
	
	// change code to use this conditional for all while loops! also move to the superclass so all waitzones can use!
	public int freeSpots() {
		return (MAX_NUM_SHIPS- ships.size());
	}
	
	public void ArrivalMessage(Ship ship){
		System.out.println(ship + " docks at berth");
	}
	
	public void DepartureMessage(Ship ship){
		System.out.println(ship + " undocks from berth");
	}
	
	
	public void unload(Ship ship) {
		System.out.println(ship + " being unloaded");	
		try {
			Thread.sleep(Params.UNLOADING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(ship + " being unloaded");
	}

	
	
}
