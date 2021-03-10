package findColor;

import labyrinth.Labyrinth;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;

public class Turn extends State {
	
	final static int MOVE_ACCELERATION = 4000;
	final static int MOVE_SPEED = 200;
	private boolean firstTurn;
	private final int durationDriveBack = 500;
	private final int durationDriveForward = 600;

	public Turn(FindingColor stateMachine, DifferentialPilot pilot, Robot robot)
	{
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
		firstTurn = false;
	}
	
	@Override
	public void init() {
		pilot.setAcceleration(MOVE_ACCELERATION);
		pilot.setTravelSpeed(MOVE_SPEED);
		if (firstTurn) {
			pilot.backward();
			Delay.msDelay(durationDriveBack);
			pilot.rotate(90);
			pilot.forward();
		} else {
			pilot.backward();
			Delay.msDelay(durationDriveForward);
			pilot.rotate(90);
			pilot.forward();
			Delay.msDelay(durationDriveForward);
			pilot.rotate(90);
		}
		stateMachine.changeState(FindingColor.FIRST_COLOR);
	}

	@Override
	public void run() throws PortNotDefinedException {
		// TODO Auto-generated method stub
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
	}

}
