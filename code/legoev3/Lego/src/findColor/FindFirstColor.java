package findColor;

import java.io.File;

import lejos.hardware.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class FindFirstColor extends State {
	
	final static int MOVE_ACCELERATION = 4000;
	final static int MOVE_SPEED = 200;
	private boolean foundFirstColor;
	

	public FindFirstColor(FindingColor stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
		foundFirstColor = false;
	}
	
	@Override
	public void init() {
		pilot.setAcceleration(MOVE_ACCELERATION);
		pilot.setTravelSpeed(MOVE_SPEED);
		pilot.forward();
		
		/*while(!(robot.sensors.getTouch() != 0)) {
			
		}
		pilot.stop();*/
	}

	@Override
	public void run() throws PortNotDefinedException {
		if (robot.sensors.getTouch() != 0) {
			stateMachine.changeState(FindingColor.TURN);
		}
		if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_RED || robot.sensors.getColor() == SensorWrapper.COLOR_ID_LINE) {
			Sound.playSample(new File("R2D2N1.wav"));
		}
		if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_BLUE) {
			stateMachine.changeState(FindingColor.TURN);
		}
		
		if (foundFirstColor && (robot.sensors.getColor() == SensorWrapper.COLOR_ID_RED || 
				robot.sensors.getColor() == SensorWrapper.COLOR_ID_LINE))
		{
			pilot.stop();
			Sound.playSample(new File("R2D2N1.wav"));
		}
		
	}

	@Override
	public void leave() {
		// TODO Auto-generated method stub
		
	}

}
