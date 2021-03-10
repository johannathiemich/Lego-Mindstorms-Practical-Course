package sample;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import robotcontrol.SensorThread;

public class RobotThread extends Thread {

	public SensorThread sensors;
	public EV3LargeRegulatedMotor mRight, mLeft;

	public RobotThread() {

		mRight = new EV3LargeRegulatedMotor(MotorPort.A);
		mLeft = new EV3LargeRegulatedMotor(MotorPort.B);

		EV3TouchSensor tLeft = new EV3TouchSensor(SensorPort.S1);
		EV3TouchSensor tRight = new EV3TouchSensor(SensorPort.S2);
		EV3ColorSensor color = new EV3ColorSensor(SensorPort.S3);
		EV3UltrasonicSensor ultra = new EV3UltrasonicSensor(SensorPort.S4);

		SingleValueSensorWrapper touchLeft = new SingleValueSensorWrapper(tLeft, "Touch");
		SingleValueSensorWrapper touchRight = new SingleValueSensorWrapper(tRight, "Touch");
		SingleValueSensorWrapper col = new SingleValueSensorWrapper(color, "Red");
		SingleValueSensorWrapper dist = new SingleValueSensorWrapper(ultra, "Distance");

		sensors = new SensorThread(touchLeft, touchRight, col, dist);
	}

	public static void main(String[] args) throws InterruptedException {
		RobotThread robot = new RobotThread();
		robot.mainLoop();
	}

	public void mainLoop() throws InterruptedException {
		sensors.start();
		this.start(); //start the program
		Button.LEDPattern(1); // green light

		while (Button.ESCAPE.isUp()) {

			//exit if the button is pressed

			mySleep(20);
		}
		System.exit(0);
	}

	private void mySleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		// do something useful
	}
}