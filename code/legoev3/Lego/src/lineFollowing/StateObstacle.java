package lineFollowing;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;

public class StateObstacle extends State {

	@SuppressWarnings("deprecation")
	StateObstacle(LineFollowing stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	private void drive(int timeMS) {
		pilot.setTravelSpeed(StateForward.MOVE_SPEED);
		pilot.setAcceleration(StateForward.MOVE_ACCELERATION);
		pilot.forward();
		Delay.msDelay(timeMS);
		pilot.stop();
	}

	private void turn(int targetAngle) throws PortNotDefinedException {
		float angleToTurn = Math.abs(targetAngle - this.robot.sensors.getGyro());
		if (angleToTurn < StateRotate.STOPPING_ANGLE_EPS) return;

		// TODO Busy waiting didn't work previously here. If problems still occur, immediateReturn can be set to false
		// TODO in pilot.rotate and the while loop can be removed, that did work in the past.
		pilot.rotate(targetAngle);
		/*while (true) {
			if (Math.abs(targetAngle - this.robot.sensors.getGyro()) < StateRotate.STOPPING_ANGLE_EPS) {
				pilot.stop();
				break;
			}
		}*/
	}

	@Override
	public void init() {
		try {
			this.robot.sensors.gyroReset();
			turn(-80);
			drive(600);
			turn(90);
			drive(1650);
			turn(90);
			drive(750);
			turn(-100);
			stateMachine.changeState(LineFollowing.ROTATE);
		} catch (PortNotDefinedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() throws PortNotDefinedException {
	}

	@Override
	public void leave() {
	}

}
