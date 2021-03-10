package labyrinth;

import bridgeCrossingPID.BridgeCrossing;
import lejos.hardware.Sound;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import main.State;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;
import robotcontrol.SensorWrapper;

public class FollowLine extends State {
	
	float color = 0;
	float error = 0;
	float lastError = 0;
	float integral = 0;
	float deriv = 0;
	float correction = 0;
	float kP = 2400.0f;
	float kI = 0.0f;
	float kD = 0.0f;
	float motorSpeed = 150.0f;
	int counter = 0;
	
	@SuppressWarnings("deprecation")
	public FollowLine(Labyrinth stateMachine, DifferentialPilot pilot, Robot robot) {
		this.stateMachine = stateMachine;
		this.pilot = pilot;
		this.robot = robot;
	}

	@Override
	public void init() {

	}
	
	@Override
	public void run() throws PortNotDefinedException {
		//float[] rgb = new float[3];
		//((EV3ColorSensor) (robot.sensors.colorSensor.sensor)).getRGBMode().fetchSample(rgb, 0);
		float[] rgb = robot.sensors.getColors();		
		
		//System.out.println(rgb[0] + "," + rgb[1] + "," + rgb[2]);
		//System.out.println("RED" + rgb[0]);
		//System.out.println("GRE" + rgb[1]);
		//System.out.println("BLU" + rgb[2]);

		
		if (robot.sensors.getColor() == SensorWrapper.COLOR_ID_BLUE) { // if blue detected
			pilot.stop();
			stateMachine.stop();
			Sound.beepSequence();
			new BridgeCrossing(robot, pilot);
			return;
		} else {
			error = 0.07f - rgb[0]; //TODO
			integral += error;
			deriv = error - lastError;
			correction = kP * error + kI * integral + kD * deriv;
		//	System.out.println(correction);
			
			//motorSpeed = Math.min(150.f, Math.max(150.f - Math.abs(error * 10000), 0.f));
			//motorSpeed = Math.max(0.f, Math.min(100.f, 100.f - Math.abs(integral * 100)));
	//		motorSpeed = integral < 1 ? 100.f : 100.f - integral * 100;
	//		motorSpeed = motorSpeed < 0 ? 0 : motorSpeed;
			
			if (lastError * error < 0) {
				counter = 0;
			} else {
				counter = counter + 1;//counter > 50 ? 50 : counter + 1;
			}
			
			motorSpeed = counter > 10 ? 0 : 150f;
			
			
			 float leftMotorSpeed = (float) (motorSpeed + correction);
			 float rightMotorSpeed = (float) (motorSpeed - correction);
			 
			 //System.out.println((((float) Math.round(error * 100)) / 100) + ",\t"
			//	 + (((float) Math.round(motorSpeed * 100)) / 100));
			 
			 robot.motors.rightMotor.setSpeed(rightMotorSpeed);
			 robot.motors.leftMotor.setSpeed(leftMotorSpeed); 
			 if (leftMotorSpeed < 0) {
				 robot.motors.leftMotor.forward();
			 } else {
				 robot.motors.leftMotor.backward();
			 }
			 
			 if (rightMotorSpeed < 0) {
				 robot.motors.rightMotor.forward();
			 } else {
				 robot.motors.rightMotor.backward();
			 }
			 
			 Delay.msDelay(20);
			lastError = error;
		}
		
	}
		

	@Override
	public void leave() {
		// TODO Auto-generated method stub

	}

}
