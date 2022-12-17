
public class ElevatorPerson {

	private int enterTime;
	private int initialPosition;
	private int target;
	private Person person;
	
	// This constructor assigns given values to variables
	public ElevatorPerson(Person p, int ip, int t) {
		person = p;
		initialPosition = ip;
		target = t;
		enterTime = Elevator.getTravelMeter();
	}
	
	// returns person
	public Person getPerson() {
		return person;
	}
	
	// returns target
	public int getTarget() {
		return target;
	}
	
	// This function returns a string that gives information about person and person's travel
	public String toString() {
		if(Math.abs(Elevator.getTravelMeter() - enterTime) <= Math.abs(target - initialPosition)) {
			return "I am " + person.getName() + ". I traveled " + Math.abs(Elevator.getTravelMeter() - enterTime) + " floors. I am happy.";
		}else {
			return "I am " + person.getName() + ". I traveled " + Math.abs(Elevator.getTravelMeter() - enterTime) + " floors. I am unhappy.";
		}
		
	}
}
