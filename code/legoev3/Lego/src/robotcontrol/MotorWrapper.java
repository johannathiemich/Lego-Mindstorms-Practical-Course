package robotcontrol;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class MotorWrapper {
	public final EV3LargeRegulatedMotor leftMotor, rightMotor;
	public final EV3MediumRegulatedMotor headMotor;

	public MotorWrapper() {
		// TODO exceptions for undefined ports
		this.leftMotor = new EV3LargeRegulatedMotor(RobotConfig.leftMotorPort);
		this.rightMotor = new EV3LargeRegulatedMotor(RobotConfig.rightMotorPort);
		this.headMotor = new EV3MediumRegulatedMotor(RobotConfig.headMotorPort);
	}
}
