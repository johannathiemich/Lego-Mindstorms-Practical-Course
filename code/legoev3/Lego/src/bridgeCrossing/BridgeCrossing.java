package bridgeCrossing;

import lejos.robotics.navigation.DifferentialPilot;
import main.CourseSectionStateMachine;
import main.State;
import robotcontrol.Robot;

public class BridgeCrossing extends CourseSectionStateMachine {
	public static int DRIVE_UP = 0;
//	public static int ... = 1;
//	public static int ... = 2;

	@SuppressWarnings("deprecation")
	public BridgeCrossing(Robot robot, DifferentialPilot pilot) {
		super();
		State[] states = new State[1];
		states[0] = new StateDriveUp(this, pilot, robot);
		//states[1] =
		//states[2] =
		// ...
		setStates(states);
		run();
	}

}
