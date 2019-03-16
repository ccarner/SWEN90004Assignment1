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
		
		ship = arrivalZone.allocatePilot();
		ship.setPilot(this);
		
		System.out.println("Pilot " + pilotNumber + " acquires " + ship);
		
		tugs.requestTugs(Params.DOCKING_TUGS);
		
		System.out.println("Pilot " + pilotNumber + " acquires " + Params.DOCKING_TUGS + " tugs ");
		
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
		
		tugs.returnTugs(1);
		
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
		
		departureZone.depart();
		
		ship = null;
		
		ship = arrivalZone.allocatePilot();
		
	}
}
