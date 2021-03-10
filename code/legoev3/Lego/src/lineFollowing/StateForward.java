package lineFollowing;

import labyrinth.Labyrinth;
import lejos.hardware.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class StateForward extends State {

	final static int MOVE_ACCELERATION = 4000;
	final static int MOVE_SPEED = 200;

	@SuppressWarnings("deprecation")
	StateForward(LineFollowing stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	@Override
	public void run() throws PortNotDefinedException {
		if (this.robot.sensors.getTouch() != 0) {
			stateMachine.changeState(LineFollowing.OBSTACLE);
		} else if (this.robot.sensors.getColor() == SensorWrapper.COLOR_ID_RED) {
			// Go to labyrinth
			stateMachine.stop();
			new Labyrinth(robot, pilot);
		} else if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_GROUND) {
			stateMachine.changeState(LineFollowing.ROTATE);
		}

	}

	@Override
	public void init() {
		Sound.beep();
		pilot.setAcceleration(MOVE_ACCELERATION);
		pilot.setTravelSpeed(MOVE_SPEED);
		pilot.forward();
	}

	@Override
	public void leave() {
		pilot.stop();
	}

}
