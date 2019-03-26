/**
 * Runtime exception when we call a 'depart' method with a ship that isn't in the
 * waitzone
 * @author ccarn
 *
 */
public class ShipNotPresentException extends RuntimeException {
	
	final static String defaultMsg = "Error: tried to 'depart' ship not in the waitzone";
	
	public ShipNotPresentException(String message) {
        super(message);
    }
	
	public ShipNotPresentException() {
        super(defaultMsg);
    }
}
