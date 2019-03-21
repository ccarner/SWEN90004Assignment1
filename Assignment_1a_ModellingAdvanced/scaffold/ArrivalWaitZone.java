// can rmove this class and the Departure waitzone + the factory and roll into the one waitzone class!
public class ArrivalWaitZone extends WaitZone {

	public ArrivalWaitZone(int maxShips) {
		super(maxShips);
	}
	
	public void ArrivalMessage(Ship ship){
		System.out.println(ship + " arrives at arrival zone");
	}
	
	@Override
	public void DepartureMessage(Ship ship){
		System.out.println(ship + " departs arrival zone");
	}
	

}
