/**
 * Pilots control a ship in the vicinity of the spaceport; requests and returns tugs
 * and initiates docking + undocking.
 * @author ccarn
 *
 */
public class Pilot extends Thread {

	private ArrivalWaitZone arrivalZone;
	private DepartureWaitZone departureZone;
	private BerthWaitZone berth;
	private Tugs tugs;
	private Ship ship;
	private int pilotNumber;
	private int numTugsPossessed = 0;
	
	public Pilot(int i, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs,
			WaitZone berth) {
		this.pilotNumber = i;
		this.arrivalZone = (ArrivalWaitZone) arrivalZone;
		this.departureZone = (DepartureWaitZone) departureZone;
		this.berth = (BerthWaitZone) berth;
		this. tugs = tugs;
	}

	/**
	 * Main pilot routine, runs constantly while the program is running.
	 * See spec sheet for routine description.
	 */
	public void run() {
		
		while(true) {
			
			// extra safeguard to prevent null pointer exceptions occasionally occurring
			while((ship = arrivalZone.allocatePilot()) == null) {
			}
			
			ship.setPilot(this);
			
			numTugsPossessed += tugs.requestTugs(Params.DOCKING_TUGS,ship,this);
			
			arrivalZone.depart(ship);

			travelSleep();
			
			berth.arrive(ship);
			
			releaseAllTugs();
			
			berth.simulateUnloading(ship);
			
			numTugsPossessed += tugs.requestTugs(Params.UNDOCKING_TUGS,ship,this);
			
			berth.depart(ship);
			
			travelSleep();
			
			departureZone.arrive(ship);
		
			releaseAllTugs();
			
			// DONT NEED TO DEPART FROM DEPARTURE ZONE, done by the 'consumer'

			ship = null;
		
		}
	}

	public String toString() {
		return "pilot " + getPilotNumber();
	}

	public int getPilotNumber() {
		return pilotNumber;
	}
	
	/**
	 * Simulates travel time by sleeping thread.
	 */
	private void travelSleep() {
		try {
			sleep(Params.TRAVEL_TIME);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
	}

	/**
	 * Releases all tugs on the ship controlled by the pilot back to the pool.
	 */
	private void releaseAllTugs() {
		tugs.returnTugs(numTugsPossessed, this);
		numTugsPossessed = 0;		
	}

}
