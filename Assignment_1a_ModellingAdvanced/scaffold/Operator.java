
public class Operator extends Thread{
	
	private WaitZone berth;
	
	public Operator(WaitZone berth) {
		this.berth=berth;
	}

	public void run() {
		
		while(!isInterrupted()) {
            try {
            	// first sleep until time for debris
                sleep(Params.debrisLapse());
                
                ((BerthWaitZone) berth).setShieldUp(true);
                
                //THIS DIDN'T WORK
                // now, wake up and make the berth wait for us
                //berth.wait(Params.arrivalLapse());
                
                
                // sleep while the debris is happening, and the shield is up (so berth is waiting on us)
                sleep(Params.DEBRIS_TIME);
                
                ((BerthWaitZone) berth).setShieldUp(false);
                
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
		
		
	}
	

}
