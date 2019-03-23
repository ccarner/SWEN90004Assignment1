
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
                System.out.println("Shield is activated.");
                           
                // sleep while the debris is happening, and the shield is up (so berth is waiting on us)
                sleep(Params.DEBRIS_TIME);
                
                ((BerthWaitZone) berth).setShieldUp(false);
                System.out.println("Shield is deactivated.");
                
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
		
		
	}
	

}
