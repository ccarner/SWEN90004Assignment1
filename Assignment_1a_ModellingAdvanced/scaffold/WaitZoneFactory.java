
public  class WaitZoneFactory {
	
	public static WaitZone getWaitZone(String waitZoneType) {
		switch (waitZoneType){
		case ("arrival"): 
			return new ArrivalWaitZone(Params.NUM_SHIPS_ARRIVAL_ZONE); 
		case ("departure") : 
			return new DepartureWaitZone(Params.NUM_SHIPS_DEPARTURE_ZONE); 
		case ("berth"):
			return new BerthWaitZone(Params.NUM_SHIPS_BERTH_ZONE);
		default: 
			return new ArrivalWaitZone(Params.NUM_SHIPS_ARRIVAL_ZONE);
		
		}
	}
}
