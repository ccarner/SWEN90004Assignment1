/**
 * Operator controls the shields around the USS Emaphore's berth.
 * Uses functions from the Param class to determine how long between shield activations
 * @author ccarn
 *
 */
public class Operator extends Thread{
	
	/** berth which the shield is protecting */
	private WaitZone berth;
	
	/** create a new operator for controlling the shield of the given berth parameter */
	public Operator(WaitZone berth) {
		this.berth=berth;
	}

	/**
	 * Main run loop for the Operator, activates + deactivates shields.
	 */
	public void run() {
		
		while(!isInterrupted()) {
            try {
            	// first sleep until time for debris (semi-random time)
                sleep(Params.debrisLapse());
                
                //now, raise shields
                ((BerthWaitZone) berth).setShieldUp(true);
                System.out.println("Shield is activated.");
                           
                // sleep while the debris is happening (fixed time), shield is up.
                sleep(Params.DEBRIS_TIME);
                
                // now put the shield back down.
                ((BerthWaitZone) berth).setShieldUp(false);
                System.out.println("Shield is deactivated.");
                
            } catch (InterruptedException e) {
                // should never be interrupted 
            	e.printStackTrace();
            }
        }
		
	}
	
}
