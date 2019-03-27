/**
 * handles creation logic for Waitzones; 'arrival', 'berth' or 'departure' zones
 * @author ccarn
 *
 */
public class WaitZoneFactory {
	
	/** message printed to console when unknown input given */
	private static String unrecognisedParamMsg = "parameter not recognised for wait " + 
		"zone : defaulting to arrival zone";
	
	/**
	 * will return a waitzone with appropriate configuration of type requested.
	 * @param waitZoneType determines wait zone type returned
	 * - "arrival" returns an arrival zone
	 * - "departure" returns a departure zone
	 * - "berth" returns a berth zone
	 * - other input will default to an arrival zone
	 * @return WaitZone class
	 */
	public static WaitZone getWaitZone(String waitZoneType) {
		switch (waitZoneType){
		case ("arrival"): 
			return new ArrivalWaitZone(Params.NUM_SHIPS_ARRIVAL_ZONE); 
		case ("departure") : 
			return new DepartureWaitZone(Params.NUM_SHIPS_DEPARTURE_ZONE); 
		case ("berth"):
			return new BerthWaitZone(Params.NUM_SHIPS_BERTH_ZONE);
		default: 
			// mistyped string or not given one... print note to console + default
			// to arrival zone
			System.out.println(unrecognisedParamMsg);
			return new ArrivalWaitZone(Params.NUM_SHIPS_ARRIVAL_ZONE);
		}
	}
}
