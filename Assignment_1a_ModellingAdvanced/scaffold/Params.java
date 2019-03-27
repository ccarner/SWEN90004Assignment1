import java.util.Random;

/**
 * contains all of the paramaters for the simulation, and some functions for 
 * determining random time intervals between events
 *
 */

class Params {
	
	/** number of ships the arrival zone can hold*/
	static final int NUM_SHIPS_ARRIVAL_ZONE = 1;
	
	/** number of ships the depature zone can hold*/
	static final int NUM_SHIPS_DEPARTURE_ZONE = 1;
	
	/** number of ships the berth zone can hold*/
	static final int NUM_SHIPS_BERTH_ZONE = 1;
	
	/** number of pilots in the simulation */
    static final int NUM_PILOTS = 2;
    
    /** number of tugs in the simulation */
    static final int NUM_TUGS = 5;
    
    /** nmumber of tugs required for docking process*/
    static final int DOCKING_TUGS = 3;

    /** nmumber of tugs required for undocking process*/
    static final int UNDOCKING_TUGS = 2;

    /** time required for docking */
    static final int DOCKING_TIME = 800;

    /** time required for undocking */
    static final int UNDOCKING_TIME = 400;

    /** time required for unloading */
    static final int UNLOADING_TIME = 1200;

    /** time required for travelling between waitzones*/
    static final int TRAVEL_TIME = 800;

    /** time shield has to stay up for when debris occurs */
    static final int DEBRIS_TIME = 1800;
    
    /** maximum time between ships arriving at arrival zone */
    private static final int MAX_ARRIVAL_INTERVAL = 400;

    /** maximum time between ships departing from departure zone*/
    private static final int MAX_DEPARTURE_INTERVAL = 1000;

    /**maximum time between space storms */
    private static final int MAX_DEBRIS_INTERVAL = 2400;

    /**
     * gives a semi-random value which is to correspond to the time until the next debris
     * storm in space
     * @return
     */
    static int debrisLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEBRIS_INTERVAL);
    }

    /**
     * gives a semi-random value which is to correspond to the time until the next new 
     * ship arrives
     * @return
     */
    static int arrivalLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_ARRIVAL_INTERVAL);
    }

    /**
     * gives a semi-random value which is to correspond to the time until the next
     * ship departs the departure zone
     * @return
     */
    static int departureLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEPARTURE_INTERVAL);
    }
}
