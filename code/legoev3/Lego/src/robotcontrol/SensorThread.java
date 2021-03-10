package robotcontrol;

import sample.SingleValueSensorWrapper;

public class SensorThread extends Thread {

	private SingleValueSensorWrapper Stouch1;
	private SingleValueSensorWrapper Stouch2;
	private SingleValueSensorWrapper Scolor;
	private SingleValueSensorWrapper Sdistance;
	private SingleValueSensorWrapper Sgyro;

	public float touchLeft, touchRight, color, distance, gyro;

	public SensorThread(SingleValueSensorWrapper Stouch1, SingleValueSensorWrapper Sgyro, SingleValueSensorWrapper Scolor, SingleValueSensorWrapper Sdistance) {
		this.Stouch1 = Stouch1;
		this.Sgyro = Sgyro;
		this.Scolor = Scolor;
		this.Sdistance = Sdistance;
	}

	public void run() {
		try {
			while (true) {
				this.touchLeft = this.Stouch1.getSample();
				this.gyro = this.Sgyro.getSample();
				this.color = this.Scolor.getSample();
				this.distance = this.Sdistance.getSample();
				Thread.sleep(20);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
