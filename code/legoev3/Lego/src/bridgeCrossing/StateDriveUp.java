package bridgeCrossing;

import lejos.hardware.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class StateDriveUp extends State {

	private static int MOVE_ACCELERATION = 4000;
	private static int MOVE_SPEED = 200;

	@SuppressWarnings("deprecation")
	public StateDriveUp(BridgeCrossing stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	@Override
	public void init() {
		Sound.twoBeeps();
		Sound.twoBeeps();
		Sound.twoBeeps();
		Sound.twoBeeps();
		Sound.twoBeeps();
		pilot.setAcceleration(MOVE_ACCELERATION);
		pilot.setTravelSpeed(MOVE_SPEED);
		pilot.forward();
	}

	@Override
	public void run() throws PortNotDefinedException {
		//sensor sees black
		if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_GROUND) {
			Sound.beepSequence();
		}

	}

	@Override
	public void leave() {
		pilot.stop();
	}

}
