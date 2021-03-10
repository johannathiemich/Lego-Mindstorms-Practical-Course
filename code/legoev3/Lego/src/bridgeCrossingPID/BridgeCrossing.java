package bridgeCrossingPID;

import lejos.hardware.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;

public class BridgeCrossing {

	private Robot robot;
	private DifferentialPilot pilot;

	private static int MOVE_ACCELERATION = 200;
	private static int MOVE_SPEED = 200;
	private static float DISTANCE_FLOOR = 0.144f;

	private float distance = 0;
	private float error = 0;
	private float lastError = 0;
	private float integral = 0;
	private float deriv = 0;
	private double correction = 0;
	private float kP = 3000.0f;
	private float kI = 10.0f;
	private float kD = 0.0f;
	private float leftMotorSpeed = 0;
	private float rightMotorSpeed = 0;

	@SuppressWarnings("deprecation")
	public BridgeCrossing(Robot robot, DifferentialPilot pilot) {
		this.pilot = pilot;
		this.robot = robot;
		try {
			run();
		} catch (PortNotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int counter = 0;
	private void run() throws PortNotDefinedException {
		robot.sensors.gyroReset();
		robot.motors.headMotor.setAcceleration(200);
		robot.motors.headMotor.rotateTo(-115); // wait until arm is on the front right
		
		pilot.setAcceleration(MOVE_ACCELERATION);
		pilot.setTravelSpeed(MOVE_SPEED);
		pilot.travel(80);
//		while (true) {
//			System.out.println(robot.sensors.getGyro());
//		}
		
		while (counter < 900) {
//			if (counter == 700) {
//				Sound.beepSequenceUp();
//			}
//			if (counter == 800) {
//				Sound.beepSequenceUp();
//			}
//			if (counter == 900) {
//				Sound.beepSequenceUp();
//			}
//			if (counter == 1000) {
//				Sound.beepSequenceUp();
//			}
			counter ++;
			System.out.println(robot.sensors.getColors()[0]);
			distance = robot.sensors.getDistance();
			if (distance > DISTANCE_FLOOR + 0.3)
				distance = DISTANCE_FLOOR + 0.3f;
			if (Math.abs(distance) > 1000)
				continue;
			error = DISTANCE_FLOOR + 0.02f - distance;
//			if (counter < 700) {
//				error = DISTANCE_FLOOR + 0.02f - distance;
//			} else if (counter >= 700 && counter < 800) {
//				error = DISTANCE_FLOOR + 0.03f - distance;
//			} else if (counter >= 800 && counter < 900) {
//				error = DISTANCE_FLOOR + 0.07f - distance;
//			} else if (counter >= 900 && counter < 1000) {
//				error = DISTANCE_FLOOR + 0.12f - distance;
//			} else 	if (counter > 1000) {
//				error = DISTANCE_FLOOR + 0.22f - distance;
//			}
			integral += error;
			deriv = error - lastError;
			lastError = error;
			correction = kP * error + kI * integral + kD * deriv;
			// System.out.println("DISTANCE: " + distance);
			// System.out.println(correction);
			// System.out.println("error\t" + error);
			// correction /= 100;
			leftMotorSpeed = (float) (80.0f + correction);
			rightMotorSpeed = (float) (80.0f - correction);
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
		}
		
		pilot.setTravelSpeed(100);
		pilot.travel(600, true);
		
		while (robot.sensors.getColors()[0] > 0.01) {
			
		}
		Sound.beepSequenceUp();
	}

}
