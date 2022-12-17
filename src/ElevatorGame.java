import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ElevatorGame extends Application {

	// Floor Y coordinates
	public int[] floorY = { 445, 380, 315, 250, 185 };
	public int currentFloorInt;

	public int[] personTargets = { -1, -1, -1, -1, -1 };
	public TextField[] personNames = new TextField[5];
	
	Elevator elevator = new Elevator();
	
	// Elevator in building
	Rectangle elevatorRec = new Rectangle(205, floorY[0], 36, 50);
	Rectangle statisticRec = new Rectangle(680, 120, 400, 400);
	
	// Get the current floor
	public String currentFloor = String.valueOf(elevator.getCurrentFloor());

	// Texts
	public Text elevatorTotalMeter = new Text(780, 290, "Total elevator travel meter is: " + Elevator.getTravelMeter());
	public Text personNum = new Text(780, 330, "Person Number: " + elevator.getPersonSize());
	public Text topPerson = new Text(420, 70, "Top Person: " + elevator.getTopPerson());
	public Text reachedPersonNum = new Text(780, 250, "Number of Reached Person: 0");
	public Text reachedPersonInfo = new Text(1070, 585, "Nobody reached");
	public Text statisticTable = new Text(810, 190, "STATISTIC TABLE");
	public Text elevatorPos = new Text(1205, 35, currentFloor);
	public Text warningMsg = new Text(85, 590, "");
	
	public ElevatorPerson reachedPerson;

	Person[] waitingPerson = new Person[5];

	public int personIndex;
	public int sameFloorIndex = -1;	// To avoid the person to get in the elevator for once when it is on the floor where elevator is on.

	@Override
	public void start(Stage stage) {


		// Elevator floor indicator
		elevatorPos.setStyle("-fx-font-size: 18px;");
		elevatorPos.setFill(Color.RED);

		// Print the top person in stack
		topPerson.setStyle("-fx-font-size: 18px;");
		topPerson.setFill(Color.RED);
		
		// it shows an error message when user press the up or down button but elevator is on max or min floor
		warningMsg.setFill(Color.RED);
		warningMsg.setFont(Font.font("Courie", FontWeight.BOLD, 20));
		
		// Prints how many person in elevator currently
		personNum.setFill(Color.WHITE);
		personNum.setFont(Font.font("Courie", FontWeight.BOLD, 15));

		ImageView elevatorImage = new ImageView(new Image("images/building.png"));	// print building

		// Print elevator image in ElevatorRec
		Image elevatorImage2 = new Image("images/elevator.png");
		elevatorRec.setFill(new ImagePattern(elevatorImage2));
		
		Image tabletImage = new Image("images/tablet.png");
		statisticRec.setFill(new ImagePattern(tabletImage));

		// UP and DOWN buttons
		Button btUp = new Button("⬆");
		btUp.setLayoutX(1400);
		btUp.setLayoutY(298);
		
		Button btDown = new Button("⬇");
		btDown.setLayoutX(1400);
		btDown.setLayoutY(328);

		// STATISTIC TABLE
		statisticTable.setFont(Font.font("Courie", FontWeight.BOLD, 20));
		statisticTable.setFill(Color.RED);

		// Sets Fonts of the how many meter has elevator traveled
		elevatorTotalMeter.setFont(Font.font("Courie", FontWeight.BOLD, 15));
		elevatorTotalMeter.setFill(Color.WHITE);

		// Sets Fonts of the information and counter of reached person
		reachedPersonInfo.setFont(Font.font("Courie", FontWeight.BOLD, 15));
		
		reachedPersonNum.setFont(Font.font("Courie", FontWeight.BOLD, 15));
		reachedPersonNum.setFill(Color.WHITE);

		Group formGroup = new Group();

		int yDistance = 100; // Distance between each form group

		for (int i = elevator.getMinFloor(),
				personI = 4; i <= elevator.getMaxFloor() - elevator.getMinFloor(); i++, personI--) {

			int personIndex = personI;

			// Floor Label
			Text floorLabel = new Text("-------------------- " + (elevator.getMaxFloor() - i) + ". Floor --------------------");
			floorLabel.setLayoutX(420);
			floorLabel.setLayoutY(135 + yDistance * i);
			floorLabel.setFont(Font.font("Courie", FontWeight.BOLD, 12));

			// Person Name and Target Labels
			Text personNameLabel = new Text("Person Name: ");
			personNameLabel.setLayoutX(420);
			personNameLabel.setLayoutY(165 + yDistance * i);
			
			Text targetFloorLabel = new Text("Target Floor: ");
			targetFloorLabel.setLayoutX(420);
			targetFloorLabel.setLayoutY(200 + yDistance * i);

			// Person name text field
			TextField personName = new TextField();
			personName.setLayoutX(510);
			personName.setLayoutY(150 + yDistance * i);

			// Person target ComboBox
			ComboBox<String> targetFloor = new ComboBox<String>();
			targetFloor.getItems().addAll("0", "1", "2", "3", "4");
			targetFloor.setValue("Choose a target floor");
			targetFloor.setLayoutX(510);
			targetFloor.setLayoutY(185 + yDistance * i);

			personNames[i] = personName;

			// If user changes the person name and target which is on the floor where elevator is on
			targetFloor.setOnAction((event) -> {
				int selectedIndex = targetFloor.getSelectionModel().getSelectedIndex();	// what user selects as target
				personTargets[personIndex] = selectedIndex;

				if (personIndex == elevator.getCurrentFloor()) {
					
					// If person changes the target
					if(sameFloorIndex == elevator.getCurrentFloor()) {
						if(!elevator.isEmpty()) {
							elevator.popLastPerson();
						}
					}
					
					// person enters in the elevator
					elevator.enter(new Person(personNames[elevator.getMaxFloor() - personIndex].getText()), selectedIndex);
					
					// changes the sameFloorIndex to current floor to control this if later
					sameFloorIndex = elevator.getCurrentFloor();

					// Reset the personTargets and waitingPerson arrays
					personTargets[elevator.getCurrentFloor()] = -1;
					waitingPerson[elevator.getCurrentFloor()] = null;

					// Person number and Top Person informations
					personNum.setText("Person Number: " + elevator.getPersonSize());
					topPerson.setText("Top Person: " + elevator.getTopPerson());
				}
			});

			// Add form elements in the form group
			formGroup.getChildren().addAll(floorLabel, personNameLabel, personName, targetFloor, targetFloorLabel);
		}

		// If user press the Up Button
		btUp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				// Check if elevator is on highest floor
				if (elevator.getCurrentFloor() == elevator.getMaxFloor()) {
					warningMsg.setText("You are on the highest floor!");
				} else {
					warningMsg.setText("");
					currentFloorInt = Integer.parseInt(elevator.goUp());	// elevator goes one up
					
					elevatorOperations(currentFloorInt);
				}

			}
		});

		// If user press the Down Button
		btDown.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				// Check if elevator is on lowest floor
				if (elevator.getCurrentFloor() == elevator.getMinFloor()) {
					warningMsg.setText("You are on the lowest floor!");
				} else {
					warningMsg.setText("");
					currentFloorInt = Integer.parseInt(elevator.goDown());	// elevator goes one down
					
					elevatorOperations(currentFloorInt);
				}
			}
		});

		// Create group for all elements
		Group group = new Group();

		// Add all elements into group
		group.getChildren().addAll(formGroup, elevatorImage, elevatorRec, statisticRec, personNum, btDown, btUp, elevatorPos,
				warningMsg, topPerson, statisticTable, elevatorTotalMeter, reachedPersonInfo, reachedPersonNum);

		// This is for full screen
		Screen screen = Screen.getPrimary();
		javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

		// Stage Settings
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());

		// Scene Settings
		Scene scene = new Scene(new BorderPane(group), 100, 100);
		stage.setTitle("Elevator Game");
		stage.setScene(scene);
		stage.show();
	}
	
	public void elevatorOperations(int currentFloorInt) {
		
		// changes elevator indicator
		elevatorPos.setText(String.valueOf(currentFloorInt));	
		
		// changes elevator's position which is in building
		elevatorRec.setY(floorY[currentFloorInt]);				
		
		// Get if there is a person who reached to his/her target
		reachedPerson = (ElevatorPerson) elevator.getReachedPerson();
		
		// If there is a person who reached to his/her target print him/her
		if(reachedPerson != null) {
			reachedPersonInfo.setText(reachedPerson.getPerson().getName() + " is out.\n"+ reachedPerson.toString());
			reachedPersonNum.setText("Number of Reached Person: " + elevator.getReachedPersonNum());
		}else {
			reachedPersonInfo.setText("Nobody reached");
		}

		// Add people to waiting person array
		for (int i = 0, j = 4; i <= 4; i++, j--) {
			Person waitP = new Person(personNames[j].getText());
			waitingPerson[i] = waitP;
		}

		// if elevator is not full add people into elevator which are in the waitingPerson array
		if (!elevator.isFull()) {
			for (int k = 0; k < waitingPerson.length; k++) {
				if (currentFloorInt == k && personTargets[k] != -1) {
					elevator.enter(waitingPerson[k], personTargets[k]);

					personTargets[k] = -1;
					waitingPerson[k] = null;
				}
			}
		}

		// Print Person number and top person information
		personNum.setText("Person Number: " + elevator.getPersonSize());
		topPerson.setText("Top Person: " + elevator.getTopPerson());

		// Prints how many meter has elevator traveled
		elevatorTotalMeter.setText("Total elevator travel meter is: " + Elevator.getTravelMeter());
	}

	public static void main(String args[]) {
		launch(args);
	}
}