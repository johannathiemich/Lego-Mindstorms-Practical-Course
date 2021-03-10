package robotcontrol;

public class Robot {

	/**
	 * This object contains current sample data for all sensors connected
	 * to the robot.
	 */
	public SensorWrapper sensors;

	/**
	 * This object allows direct access to the robots three motors. The subelements
	 * implement the interface {@link lejos.hardware.motor.BasicMotor} and allow direct
	 * control to the motors.
	 */
	public MotorWrapper motors;

	/**
	 * This class supplies basic control options for the roboter.
	 */
	public Robot() {
		this.motors = new MotorWrapper();
		this.sensors = new SensorWrapper();
	}
}
