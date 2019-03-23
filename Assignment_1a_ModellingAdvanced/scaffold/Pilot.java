// My own class
// pilot should seek to obtain a new ship whenever it is free.
public class Pilot extends Thread {

	private ArrivalWaitZone arrivalZone;
	private DepartureWaitZone departureZone;
	private BerthWaitZone berth;
	private Tugs tugs;
	private Ship ship;
	private int pilotNumber;
	private int numTugsPossessed = 0;
	
	public Pilot(int i, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, WaitZone berth) {
		pilotNumber = i;
		this.arrivalZone = (ArrivalWaitZone) arrivalZone;
		this.departureZone= (DepartureWaitZone) departureZone;
		this.berth = (BerthWaitZone) berth;
		this. tugs = tugs;
	}

	public void run() {
		
		while(true) {
			
			while((ship = arrivalZone.allocatePilot()) == null) {
				// extra safeguard to prevent null pointer exceptions which occasionally occurred
			}
			
			ship.setPilot(this);
			
			System.out.println("Pilot " + pilotNumber + " acquires " + ship +".");
			
			tugs.requestTugs(Params.DOCKING_TUGS,ship,this);
			numTugsPossessed += Params.DOCKING_TUGS;
			
			try {
				sleep(Params.TRAVEL_TIME);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			arrivalZone.depart(ship);
			
			berth.arrive(ship);
			
			berth.dock(ship);	
			
			releaseAllTugs();
			
			berth.unload(ship);
			
			numTugsPossessed += tugs.requestTugs(Params.UNDOCKING_TUGS,ship,this);
			
			berth.depart(ship);
			
			
			try {
				sleep(Params.TRAVEL_TIME);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			departureZone.arrive(ship);
		
			releaseAllTugs();
			
			// DONT NEED TO DEPART FROM DEPARTURE ZONE!
			// done by the 'consumer'
			
			ship = null;
		
		}
	}

	public void releaseAllTugs() {
		tugs.returnTugs(numTugsPossessed, this);
		numTugsPossessed = 0;		
	}
	
	public String toString() {
		return "pilot " + pilotNumber;
	}
}
