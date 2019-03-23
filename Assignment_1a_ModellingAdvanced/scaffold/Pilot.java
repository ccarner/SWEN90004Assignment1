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
			ship = arrivalZone.allocatePilot();
			ship.setPilot(this);
			
			System.out.println("Pilot " + pilotNumber + " acquires " + ship);
			
			tugs.requestTugs(Params.DOCKING_TUGS);
			numTugsPossessed += Params.DOCKING_TUGS;
			
			System.out.println("Pilot " + pilotNumber + " acquires " + Params.DOCKING_TUGS + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
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
			
			tugs.requestTugs(Params.UNDOCKING_TUGS);
			
			berth.dock(ship);
			
			numTugsPossessed += Params.DOCKING_TUGS;
			
			System.out.println("Pilot " + pilotNumber + " releases " + Params.NUM_TUGS_RELEASE_BERTH + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
			// here change it so that we re-acquire more tugs?
			
			berth.depart(ship);
			System.out.println("Pilot " + pilotNumber + " undocks from berth");
			
			try {
				sleep(Params.TRAVEL_TIME);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			departureZone.arrive(ship);
		
			releaseAllTugs();
			
			System.out.println("Pilot " + pilotNumber + " releases " + Params.UNDOCKING_TUGS + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
			// DONT NEED TO DEPART FROM DEPARTURE ZONE!
			// done by the 'consumer'
			
			ship = null;
		
		}
	}

	public void releaseAllTugs() {
		tugs.returnTugs(numTugsPossessed);
		numTugsPossessed = 0;		
	}
}
