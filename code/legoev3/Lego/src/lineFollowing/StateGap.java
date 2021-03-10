package lineFollowing;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class StateGap extends State {
	/**
	 * The amount of degrees of the angle, in which the robot looks for a line in each direction
	 * each time the robot stops while moving through the gap.
	 */
	static final int LINE_SEARCH_ANGLE = 90;

	/**
	 * The robot will move forward for each element in this list, each time for as many ms as the
	 * value of the respective integer, turning and looking for a line in between.
	 * <p>
	 * For example, if the list is [500, 1000], the robot will move forward 500ms, look for a line
	 * by turning in both sides, move forward 1000ms and look again.
	 */
	private static final Integer[] forwardTimes = new Integer[]{500, 500, 500};

	@SuppressWarnings("deprecation")
	StateGap(LineFollowing stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	@Override
	public void run() throws PortNotDefinedException {
		// Attempt to go forward 3 times for 500ms each, looking around for a line each time.


		for (int forwardTime : forwardTimes) {
			pilot.setAcceleration(StateForward.MOVE_ACCELERATION);
			pilot.setTravelSpeed(StateForward.MOVE_SPEED);
			pilot.forward();

			Delay.msDelay(forwardTime);

			pilot.stop();
			if (turnSearch(1)) return;
			if (turnSearch(-1)) return;
		}
	}

	@Override
	public void init() {

	}

	private boolean turnSearch(int direction) {
		pilot.setRotateSpeed(StateRotate.SEARCH_SPEED);
		pilot.rotate(45 * direction, true);

		while (pilot.isMoving()) {
			try {
				if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_LINE) {
					stateMachine.changeState(LineFollowing.FORWARD);
					return true;
				}
			} catch (PortNotDefinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		pilot.setRotateSpeed(StateRotate.FAST_SPEED);
		pilot.rotate(-45 * direction);

		return false;
	}

	@Override
	public void leave() {
		pilot.stop();
	}


}
