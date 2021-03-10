package robotcontrol;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class RobotConfig {
	public static final Port touchSensorPort = SensorPort.S2;
	public static final Port colorSensorPort = SensorPort.S3;
	public static final Port ultrasonicSensorPort = SensorPort.S1;
	public static final Port gyroPort = SensorPort.S4;

	public static final String colorSensorMode = "RGB";
	public static final String ultrasonicSensorMode = "Distance";
	public static final String gyroSensorMode = "Angle";

	public static final Port leftMotorPort = MotorPort.B;
	public static final Port rightMotorPort = MotorPort.C;
	public static final Port headMotorPort = MotorPort.A;
}
