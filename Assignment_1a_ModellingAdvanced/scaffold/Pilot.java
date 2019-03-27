/**
 * Pilots control a ship in the vicinity of the spaceport; requests and returns tugs
 * and initiates docking + undocking.
 * @author ccarn
 *
 */
public class Pilot extends Thread {
	
	/** identification number for the pilot, used for printouts*/
	private int pilotNumber;
	
	/** waitzones that the ship needs to visit */
	private ArrivalWaitZone arrivalZone;
	private DepartureWaitZone departureZone;
	private BerthWaitZone berth;
	
	/** tug controller the pilot needs to ask for tugs/returns tugs to */
	private Tugs tugs;
	
	/** ship pilot is driving */
	private Ship ship;
	
	/** number of tugs the pilot has for his ship */
	private int numTugsPossessed = 0;
	
	
	/** create new pilot with a given ID number, and give it access to the waitzones in
	 * the simulation and the tug controller
	 * @param i pilot number to be assigned
	 * @param arrivalZone arrival zone in simulation where pilots will collect ships
	 * @param departureZone departure zone in simulation where pilots will leave ships
	 * @param berth berth zone in simulation where pilots will dock
	 * @param tugs tug controller pilots will ask for tugs/return tugs to
	 */
	public Pilot(int i, WaitZone arrivalZone, WaitZone departureZone, WaitZone berth,
			Tugs tugs) {
		this.pilotNumber = i;
		this.arrivalZone = (ArrivalWaitZone) arrivalZone;
		this.departureZone = (DepartureWaitZone) departureZone;
		this.berth = (BerthWaitZone) berth;
		this. tugs = tugs;
	}
	

	/**
	 * Main pilot routine, runs constantly while the program is running.
	 * Pilot obtains new ships from arrival zone, docks at berth, unloads and then goes 
	 * to the departure zone. Acquires tugs at arrival zone for docking, then releases 
	 * them while unloading. Acquires new tugs for undocking and releases them at the
	 * departure zone.
	 * See spec sheet for full routine description.
	 */
	public void run() {
		
		while(true) {
			
			/* ask the arrival zone for a ship, will be made to wait if none avialable.
			 * while loop is an extra safeguard to prevent null pointer exceptions
			 * occasionally occurring
			 */ 
			while((ship = arrivalZone.allocatePilot()) == null) {
			}
			
			ship.setPilot(this);
			
			// ask for tugs from controller, will be waiting if none available.
			numTugsPossessed += tugs.requestTugs(Params.DOCKING_TUGS,ship,this);
			
			arrivalZone.depart(ship);

			// sleep the thread to simulate travel
			travelSleep();
			
			// involves waiting for shields and docking
			berth.arrive(ship);
			
			// release tugs so other pilots can acquire them from arrival zone
			releaseAllTugs();
			
			// simulate unloading
			berth.simulateUnloading(ship);
			
			// requestiung more tugs to be able to depart
			numTugsPossessed += tugs.requestTugs(Params.UNDOCKING_TUGS,ship,this);
			
			// wait for shields and head for the departure zone
			berth.depart(ship);			
			travelSleep();
			
			departureZone.arrive(ship);
		
			// finished undocking process, tugs no longer needed
			releaseAllTugs();
			
			// we're done with this ship, pilot no longer needed
			ship = null;
			
			// Don't need to depart from departure zone, done by the 'consumer'
		
		}
	}

	/**
	 * "pilot" + number
	 */
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
