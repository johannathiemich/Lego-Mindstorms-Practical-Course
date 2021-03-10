package robotcontrol;


public class Drive {
	private MotorWrapper motorWrapper;
	private float kP = 50.0f;

	public Drive() {
		motorWrapper = new MotorWrapper();
	}

	public void forward(float speed) {
		System.out.println("s");
		motorWrapper.leftMotor.setSpeed(speed);
		motorWrapper.rightMotor.setSpeed(speed);
		motorWrapper.leftMotor.backward();
		motorWrapper.rightMotor.backward();


		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int encoderLeft;
				int encoderRight;
				int error;
				float controllValue;
				int counter = 0;
				try {
					while (counter < 5000) {
						counter++;
						System.out.println("LOOP");

						encoderLeft = motorWrapper.leftMotor.getTachoCount();
						encoderRight = motorWrapper.rightMotor.getTachoCount();

						error = encoderLeft - encoderRight;
						controllValue = kP * error;

						motorWrapper.leftMotor.setSpeed(motorWrapper.leftMotor.getSpeed() + controllValue);
						//motorWrapper.leftMotor.backward();
						Thread.sleep(2);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
