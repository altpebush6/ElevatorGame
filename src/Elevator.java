
public class Elevator{

	private static int travelMeter = 0;
	private int currentFloor;
	private int maxFloor;
	private int minFloor;
	private MyStack people = new MyStack();
	private int capacity;
	private ElevatorPerson reachedPerson;
	private int reachedPersonNum=0;
	
	// This constructor initialize variables
	public Elevator() {
		currentFloor = 0; 
		minFloor = 0;
		maxFloor = 4; 
		capacity = 4;
	}
	
	// This constructor assigns given values to some variables
	public Elevator(int size, int minFloor, int maxFloor) {
		currentFloor = 0;
		this.capacity = size;
		this.minFloor = minFloor;
		this.maxFloor = maxFloor;
	}
	
	// This function directly gives travelMeter
	public static int getTravelMeter() {
		return travelMeter;
	}
	
	/* 
	 This function first push a new given person and given target to stack.
	 Then check if given target is valid or invalid.
	 If target is valid then check if there is a space in elevator and places person to elevator.
	*/
	public boolean enter(Person p, int target) {
		
		
		if(target > maxFloor || target < minFloor) {
			 throw new IllegalArgumentException("target " + target + " out of bounds");
		}else {
			if(capacity > people.getSize()) {
				people.push(new ElevatorPerson(p, currentFloor, target));
				System.out.println(p.getName()+" is in.");
				return true;
			}
		}
		return false;
	}
	
	/* 
		This function first check if given target floor is valid or not.
		If valid, then increases travelMeter by traveled meter and assigns given floor to current floor.
		Finally, if person which is on top is at target, then takes person out.
	*/
	public void goToFloor(int floor) {
		if(floor > maxFloor || floor < minFloor) {
			 throw new IllegalArgumentException("target " + floor + " out of bounds");
		}else {
			Elevator.travelMeter += Math.abs(floor - currentFloor);
			currentFloor = floor;
			
			if(!isEmpty()) {
				
				ElevatorPerson topPerson = (ElevatorPerson) people.peek();
				
				if(topPerson.getTarget() != currentFloor) {
					reachedPerson = null;
				}
				
				while(topPerson.getTarget() == currentFloor) {
					System.out.println(topPerson.getPerson().getName() + " is out.");
					System.out.println(topPerson.toString());
					
					// Assign top person in the stack to reachedPerson and increment reached person counter by one
					reachedPerson = topPerson;
					reachedPersonNum++;

					people.pop();
					
					if(people.getSize() == 0) { // if nobody is in the elevator don't look the person after last one
						break;
					}else {
						topPerson = (ElevatorPerson) people.peek();
					}
				}
				System.out.println(this);
			}else {
				reachedPerson = null;
			}
		}
	}
	
	// Until nobody remains take out everyone at their target floor.
	public void releaseEveryone() {
		while(!isEmpty()) {
			ElevatorPerson topPerson = (ElevatorPerson) people.peek();
			goToFloor(topPerson.getTarget());
		}
	}
	
	// check elevator is full
	public boolean isFull() {
		if(capacity <= people.getSize()) {
			return true;
		}
		return false;
	}
	
	// check elevator is empty
	public boolean isEmpty() {
		if(people.getSize() == 0) {
			return true;
		}
		return false;
	}
	
	// returns top person's toString method
	public String getTopPerson() {
		if(!isEmpty()) {
			return ((ElevatorPerson) people.peek()).getPerson().toString();
		}else {
			return "Nobody";
		}
	}
	
	// Removes the last person in the stack
	public void popLastPerson() {
		people.pop();
	}
	
	// Returns How many people in stack
	public int getPersonSize() {
		return people.getSize();
	}
	
	// Returns Person if someone reached their target
	public Object getReachedPerson() {
		return reachedPerson;
	}
	
	// Gives the number of how many people reached their target
	public int getReachedPersonNum() {
		return reachedPersonNum;
	}
	
	// returns the current floor
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	// returns the highest floor
	public int getMaxFloor() {
		return maxFloor;
	}
	
	// returns the lowest floor
	public int getMinFloor() {
		return minFloor;
	}
	
	// runs goToFloor method by going one up 
	public String goUp() {
		goToFloor(currentFloor + 1);
		return String.valueOf(currentFloor);
	}
	
	// runs goToFloor method by going one down
	public String goDown() {
		goToFloor(currentFloor - 1);
		return String.valueOf(currentFloor);
	}
	
	// return information string about elevator
	public String toString() {
		return "Elevator is on floor: " + currentFloor + ", Number of people: " + people.getSize();
	}
	
}
