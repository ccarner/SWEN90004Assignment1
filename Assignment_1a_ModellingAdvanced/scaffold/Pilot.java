// My own class
// pilot should seek to obtain a new ship whenever it is free.
public class Pilot extends Thread {

	private WaitZone arrivalZone;
	private WaitZone departureZone;
	private WaitZone berth;
	private Tugs tugs;
	private Ship ship;
	private int pilotNumber;
	
	public Pilot(int i, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, WaitZone berth) {
		pilotNumber = i;
		this.arrivalZone = arrivalZone;
		this.departureZone= departureZone;
		this.berth = berth;
		this. tugs = tugs;
	}

	public void run() {
		
		while(true) {
			ship = arrivalZone.allocatePilot();
			ship.setPilot(this);
			
			System.out.println("Pilot " + pilotNumber + " acquires " + ship);
			
			tugs.requestTugs(Params.DOCKING_TUGS);
			
			System.out.println("Pilot " + pilotNumber + " acquires " + Params.DOCKING_TUGS + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
			try {
				sleep(Params.TRAVEL_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			arrivalZone.depart(ship);
			
			berth.arrive(ship);
			
			try {
				sleep (Params.DOCKING_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tugs.returnTugs(Params.NUM_TUGS_RELEASE_BERTH);
			System.out.println("Pilot " + pilotNumber + " releases " + Params.NUM_TUGS_RELEASE_BERTH + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
			
			try {
				sleep (Params.UNLOADING_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// here change it so that we re-acquire more tugs?
			
			try {
				sleep (Params.UNDOCKING_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			berth.depart(ship);
			
			try {
				sleep(Params.TRAVEL_TIME);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			departureZone.arrive(ship);
			
			try {
				sleep (Params.DOCKING_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tugs.returnTugs(Params.UNDOCKING_TUGS);
			System.out.println("Pilot " + pilotNumber + " releases " + Params.UNDOCKING_TUGS + " tugs (" + tugs.getNumTugsRemaining() + " available).");
			
			// DONT NEED TO DEPART FROM DEPARTURE ZONE!
			// done by the 'consumer'
			
			ship = null;
		
		}
		
	}
}
