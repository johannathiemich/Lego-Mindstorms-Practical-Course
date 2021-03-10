package findColor;

import lejos.robotics.navigation.DifferentialPilot;
import main.CourseSectionStateMachine;
import main.State;
import robotcontrol.Robot;

public class FindingColor extends CourseSectionStateMachine {
	
	public static final int FIRST_COLOR = 0;
	public static final int TURN = 1;
	
	@SuppressWarnings("deprecation")
	public FindingColor(Robot robot, DifferentialPilot pilot) {
		super();
		State[] states = new State[2];
		
		//TODO: exchange states:
		states[0] = new FindFirstColor(this, pilot, robot);
		states[1] = new Turn(this, pilot, robot);
		//states[1] =
		//states[2] =
		// ...
		setStates(states);
		run();
	}

}
