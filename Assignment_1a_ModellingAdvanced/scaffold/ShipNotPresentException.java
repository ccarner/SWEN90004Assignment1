/**
 * Runtime exception when we call a 'depart' method with a ship that isn't in the
 * waitzone
 * @author ccarn
 *
 */
public class ShipNotPresentException extends RuntimeException {
	
	/** default error message */
	final static String defaultMsg = "Error: tried to 'depart' ship not in the waitzone";
	
	/** throw an exception with the given message*/
	public ShipNotPresentException(String message) {
        super(message);
    }
	
	/** throw an exception with the default message */
	public ShipNotPresentException() {
        super(defaultMsg);
    }
}
