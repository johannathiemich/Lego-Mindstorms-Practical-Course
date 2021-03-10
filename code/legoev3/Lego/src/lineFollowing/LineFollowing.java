package lineFollowing;

import lejos.robotics.navigation.DifferentialPilot;
import main.CourseSectionStateMachine;
import main.State;
import robotcontrol.Robot;


public class LineFollowing extends CourseSectionStateMachine {
	public static final int FORWARD = 0;
	public static final int ROTATE = 1;
	public static final int GAP = 2;
	public static final int OBSTACLE = 3;

	public LineFollowing(Robot robot, DifferentialPilot pilot) {
		super();
		State[] states = new State[4];
		states[0] = new StateForward(this, pilot, robot);
		states[1] = new StateRotate(this, pilot, robot);
		states[2] = new StateGap(this, pilot, robot);
		states[3] = new StateObstacle(this, pilot, robot);
		setStates(states);
		run();
	}


}
