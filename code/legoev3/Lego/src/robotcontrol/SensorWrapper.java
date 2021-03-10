package robotcontrol;


import lejos.hardware.Sound;
import lejos.hardware.sensor.*;
import lejos.utility.Delay;

public class SensorWrapper {
	public static final int COLOR_ID_RED = 0; // normally 0=none
	public static final int COLOR_ID_BLUE = 2;
	public static final int COLOR_ID_GROUND = 7; // 7=brown
	public static final int COLOR_ID_LINE = 6; // 6=white

	private class MultiSensorWrapper {
		public BaseSensor sensor;
		private SensorMode mode;
		private float[] samples;

		MultiSensorWrapper(BaseSensor sensor, String mode) {
			this.sensor = sensor;
			this.mode = sensor.getMode(mode);
			this.samples = new float[this.mode.sampleSize()];
		}

		float[] getSamples() {
			mode.fetchSample(samples, 0);
			return samples;
		}
	}

	public class SingleValueSensorWrapper extends MultiSensorWrapper {
		SingleValueSensorWrapper(BaseSensor sensor, String mode) {
			super(sensor, mode);
		}

		float getSample() {
			return super.getSamples()[0];
		}
	}

	private class gyroSensorWrapper extends SingleValueSensorWrapper {
		gyroSensorWrapper(BaseSensor sensor, String mode) {
			super(sensor, mode);
		}

		void reset() {
			((EV3GyroSensor) this.sensor).reset();
		}
	}

	private SingleValueSensorWrapper touchSensor;
	public SingleValueSensorWrapper colorSensor;
	private SingleValueSensorWrapper ultrasonicSensor;
	private gyroSensorWrapper gyroSensor;
	private int sensorSampleFrequency;

	//private float touchLeft, touchRight, color, distance;

	public SensorWrapper() {
		this.touchSensor = RobotConfig.touchSensorPort == null ? null :
				new SingleValueSensorWrapper(new EV3TouchSensor(RobotConfig.touchSensorPort), "Touch");
		this.colorSensor = RobotConfig.colorSensorPort == null ? null :
				new SingleValueSensorWrapper(new EV3ColorSensor(RobotConfig.colorSensorPort), RobotConfig.colorSensorMode);
		this.ultrasonicSensor = RobotConfig.ultrasonicSensorPort == null ? null :
				new SingleValueSensorWrapper(new EV3UltrasonicSensor(RobotConfig.ultrasonicSensorPort), RobotConfig.ultrasonicSensorMode);
		this.gyroSensor = RobotConfig.gyroPort == null ? null :
				new gyroSensorWrapper(new EV3GyroSensor(RobotConfig.gyroPort), RobotConfig.gyroSensorMode);
	}

    /*public void run() {
		try {
            while (true) {
                if (this.leftTouchSensor != null) {
                    this.touchLeft = this.leftTouchSensor.getSample();
                }

                if (this.rightTouchSensor != null) {
                    this.touchRight = this.rightTouchSensor.getSample();
                }

                if (this.colorSensor != null) {
                    this.color = this.colorSensor.getSample();
                }

                if (this.ultrasonicSensor != null) {
                    this.distance = this.ultrasonicSensor.getSample();
                }

                Thread.sleep(this.sensorSampleFrequency);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

	/**
	 * Get a sample of the defined sensor.
	 *
	 * @return the sample data as float.
	 * @throws PortNotDefinedException if the port of the accessed device is not defined in the roboter
	 *                                 config object.
	 */
	public float getTouch() throws PortNotDefinedException {
		if (this.touchSensor != null) {
			return this.touchSensor.getSample();
		} else {
			throw new PortNotDefinedException("Touch sensor is being accessed, but not defined.");
		}
	}

	/**
	 * Get a sample of the defined sensor.
	 *
	 * @return the sample data as float.
	 * @throws PortNotDefinedException if the port of the accessed device is not defined in the roboter
	 *                                 config object.
	 */
	public int getColor() throws PortNotDefinedException {
		if (this.colorSensor != null) {
			float[] colors = this.colorSensor.getSamples();
			int color = 0;//Math.round(colorUnrounded);
			color = ((EV3ColorSensor) this.colorSensor.sensor).getColorID();
			
			float eps = 0.05f;
			float[] supposedLine = {.22f, .31f, .14f};
			float[] supposedGround = {.04f, .04f, .02f};
			float[] supposedBlue = {.037f, .12f, .08f};
			float[] supposedRed = {.24f, .04f, .015f};
			
			
			if (checkColor(supposedLine, colors, 0.06f)) {
				System.out.println("DETECTED LINE");
				return SensorWrapper.COLOR_ID_LINE;
			} else if (checkColor(supposedGround, colors, 0.06f)) {
				System.out.println("DETECTED GROUND");
				return SensorWrapper.COLOR_ID_GROUND;
			} else if (checkColor(supposedBlue, colors, 0.035f)) {
				System.out.println("DETECTED BLUE");
				return SensorWrapper.COLOR_ID_BLUE;
			} else if (checkColor(supposedRed, colors, eps)) {
				System.out.println("DETECTED RED");
				return SensorWrapper.COLOR_ID_RED;
			} else {
				Sound.playTone(200, 30);
				Delay.msDelay(10);
				Sound.playTone(200, 30);
				Delay.msDelay(10);
				Sound.playTone(200, 30);
				Delay.msDelay(10);
				Sound.playTone(200, 30);
				Delay.msDelay(10);
				Sound.playTone(200, 30);
				return -1;
			}
			
			/*if (colors[0] > 0.2 && colors[0] < 0.24
					&& colors[1] > 0.29 && colors[1] < 0.33
					&& colors[2] > 0.12 && colors[2] < 0.15) {
				// LINE
				//System.out.println("DETECTED LINE");
				return SensorWrapper.COLOR_ID_LINE;
			} else if (colors[0] > 0.00 && colors[0] < 0.06
					&& colors[1] > 0.00 && colors[1] < 0.06
					&& colors[2] > 0.00 && colors[2] < 0.05) {
				// GROUND
				//System.out.println("DETECTED GROUND");
				return SensorWrapper.COLOR_ID_GROUND;
			} if (colors[0] > 0.03 && colors[0] < 0.05
					&& colors[1] > 0.10 && colors[1] < 0.15
					&& colors[2] > 0.06 && colors[2] < 0.11) {
				// BLUE
				//System.out.println("DETECTED BLUE");
				return SensorWrapper.COLOR_ID_BLUE;
			} if (colors[0] > 0.22 && colors[0] < 0.28
					&& colors[1] > 0.02 && colors[1] < 0.07
					&& colors[2] > 0.00 && colors[2] < 0.02) {
				// RED
				//System.out.println("DETECTED RED");
				return SensorWrapper.COLOR_ID_RED;
			} else {
				System.out.println(colors[0] + "," + colors[1] + "," + colors[2]);
				Sound.playTone(1000, 1000);
				Sound.playTone(1100, 200);
				Sound.playTone(1200, 250);
				Sound.playTone(1000, 300);
			}*/
			
			//return color;
		} else {
			throw new PortNotDefinedException("Color sensor is being accessed, but not defined.");
		}
	}
	
	private boolean checkColor(float[] supposed, float[] is, float eps) {
		return Math.abs(supposed[0] - is[0]) < eps
				&& Math.abs(supposed[1] - is[1]) < eps
				&& Math.abs(supposed[2] - is[2]) < eps;
	}

	public float[] getColors() throws PortNotDefinedException {
		if (this.colorSensor != null) {
			return this.colorSensor.getSamples();
		} else {
			throw new PortNotDefinedException("Color sensor is being accessed, but not defined.");
		}
	}

	/**
	 * Get a sample of the defined sensor.
	 *
	 * @return the sample data as float.
	 * @throws PortNotDefinedException if the port of the accessed device is not defined in the roboter
	 *                                 config object.
	 */
	public float getDistance() throws PortNotDefinedException {
		if (this.ultrasonicSensor != null) {
			return this.ultrasonicSensor.getSample();
		} else {
			throw new PortNotDefinedException("Ultrasonic sensor is being accessed, but not defined.");
		}
	}

	public float getGyro() throws PortNotDefinedException {
		if (this.gyroSensor != null) {
			return this.gyroSensor.getSample();
		} else {
			throw new PortNotDefinedException("Gyroscope sensor is being accessed, but not defined.");
		}
	}

	public void gyroReset() throws PortNotDefinedException {
		if (this.gyroSensor != null) {
			this.gyroSensor.reset();
		} else {
			throw new PortNotDefinedException("Gyroscope sensor is being accessed, but not defined.");
		}

	}
}
