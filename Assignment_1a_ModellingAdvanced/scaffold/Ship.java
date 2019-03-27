/**
 * A cargo ship, with a unique id, that arrives at
 * the space station to deliver its cargo.
 *
 * @author ngeard@unimelb.edu.au
 * modified by Colton Carner
 *
 */

public class Ship {

    // a unique identifier for this cargo ship
    private int id;
    
    /** pilot who will be driving the ship in the simulation*/
    private Pilot pilot;

    // the next ID to be allocated
    private static int nextId = 1;

    // a flag indicating whether the ship is currently loaded
    private boolean loaded;

    // create a new vessel with a given identifier
    private Ship(int id) {
        this.id = id;
        this.setLoaded(true);
    }

    // get a new Ship instance with a unique identifier
    public static Ship getNewShip() {
        return new Ship(nextId++);
    }

    // produce an identifying string for the cargo ship
    public String toString() {
        return "ship [" + id + "]";
    }

    /* get the pilot of thje ship*/
	public Pilot getPilot() {
		return pilot;
	}
	
	/** set pilot and print to command line */
	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
		System.out.println(pilot + " acquires " + this +".");
	}

	/** return if the ship has cargo or not*/
	public boolean isLoaded() {
		return loaded;
	}

	/** change whether the ship is loaded (ie load/unload the ship)*/
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	
	
}
