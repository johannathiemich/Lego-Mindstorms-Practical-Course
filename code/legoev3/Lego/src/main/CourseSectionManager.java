package main;

import bridgeCrossingPID.BridgeCrossing;
import findColor.FindingColor;
import labyrinth.Labyrinth;
import lejos.hardware.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lineFollowing.LineFollowing;
import robotcontrol.Robot;

public class CourseSectionManager {

	private CourseSections currentSection;
	private Robot robot;
	private DifferentialPilot pilot;

	public CourseSectionManager(CourseSections startingSection) {
		this.currentSection = startingSection;
		this.robot = new Robot();
		this.pilot = new DifferentialPilot(30, 160, robot.motors.leftMotor, robot.motors.rightMotor, true);

		switch (currentSection) {
			case FOLLOW_LINE:
				new LineFollowing(robot, pilot);
				break;

			case BRIDGE:
				new BridgeCrossing(robot, pilot);
				break;

			case LABYRINTH:
				new Labyrinth(robot, pilot);
				break;
				
			case FIND_COLOR:
				new FindingColor(robot, pilot);
				break;
				
			default:
				Sound.beepSequence();
				throw new InternalError("Menue is immernoch am Arsch");
		}
	}

	//do stuff depending on the state. Receive events (barcodes) to switch state.

}
