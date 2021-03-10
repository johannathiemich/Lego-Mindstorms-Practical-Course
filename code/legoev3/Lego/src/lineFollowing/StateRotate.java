package lineFollowing;

import lejos.robotics.navigation.DifferentialPilot;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class StateRotate extends State {

	final static int SEARCH_SPEED = 35;
	final static int FAST_SPEED = 80;

	final static int TURN_ANGLE_EXTRA = 15;
	final static int STOPPING_ANGLE_EPS = 5;

	// TODO: Move to robot
	private boolean lastRotationLeft = true;
	private float rotationDirection = 1;

	@SuppressWarnings("deprecation")
	StateRotate(LineFollowing stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	@Override
	public void init() {
		// Resetting Gyro angles. Robot needs to be stationary for that.
		pilot.stop();
		try {
			robot.sensors.gyroReset();
		} catch (PortNotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() throws PortNotDefinedException {

		if (turnAndSearch(SEARCH_SPEED, rotationDirection * 90)) return;
		rotationDirection *= -1;
		if (turnAndSearch(FAST_SPEED, 0)) return;

		if (turnAndSearch(SEARCH_SPEED, rotationDirection * 90)) return;
		rotationDirection *= -1;
		if (turnAndSearch(FAST_SPEED, 0)) return;

		stateMachine.changeState(LineFollowing.GAP);
	}

	@Override
	public void leave() {
	}

	private boolean turnAndSearch(int speed, float targetAngle) throws PortNotDefinedException {
		float angleToTurn = Math.abs(targetAngle - robot.sensors.getGyro());
		int direction = targetAngle - this.robot.sensors.getGyro() > 0 ? 1 : -1;
		
		pilot.setRotateSpeed(speed);
		pilot.rotate(direction * (angleToTurn + 30), true); // Make sure to turn AT LEAST angleToTurn °

		while (true) {
			if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_LINE) {
				pilot.stop();
				stateMachine.changeState(LineFollowing.FORWARD);
				return true;
			}

			if (Math.abs(targetAngle - robot.sensors.getGyro()) < STOPPING_ANGLE_EPS) {
				pilot.stop();
				return false;
			}
		}
	}
}
