package main;

import robotControl.RobotController;

import java.util.LinkedList;
import java.util.List;

public class RoomController extends RobotController {

	final public static int SPEED_UP = 1;

	private boolean LastRotationRight = false;
	private boolean hasEnteredRoom = false;
	private boolean isEnteringRoom = false;
	private int fuzzy = 0;
	private int fuzzyV = 1;
	private List<double[]> actionStack = new LinkedList<double[]>();
	
	/**
	 * Constructor for Controller in Labyrinth Parcour
	 */
	public RoomController() {

	}

	
	/**
	 * Implements the logic for the robot controls.
	 * @param lightSensorRedValue Value of the red channel from the lightsensor
	 * @param lightSensorBlueValue Value of the blue channel from the lightsensor
	 * @param lightSensorGreenValue Value of the green channel from the lightsensor
	 * @param distance distance meassured from the distancesensor
	 * All light values are given in rgb pixel values (0-255)
	 */
	public double[] getControlAction(int lightSensorRedValue, int lightSensorBlueValue, int lightSensorGreenValue, double distance, boolean touch)
	{
		//robot kinematics is as follows:
		//center of rotation is one third in, seen from the front
		//the light sensor is mounted on the center of the front 
		//the robot has a touchbar mounted on the front
		
		// Process stack of stored actions before thinking about actual logic.
		if (actionStack != null && actionStack.size() > 0) {
			double[] motorControl = actionStack.remove(0);
			return new double[]{motorControl[0], motorControl[1]};
		}

		// Handle room entering and colored tile finding
		if (lightSensorBlueValue > 200 || lightSensorRedValue > 200) move(0.d, 0.d, 5);
		if (lightSensorGreenValue > 200 && !hasEnteredRoom && !isEnteringRoom) isEnteringRoom = true;
		else if (lightSensorGreenValue < 200 && !hasEnteredRoom && isEnteringRoom) hasEnteredRoom = true;

		// Handle walls
		if (touch || (hasEnteredRoom && lightSensorGreenValue > 200)) {
			move(-2.d, -2.d, 30);

			if (LastRotationRight) {
				move(0.d, 2.d, 79);
			} else {
				move(2.d, 0.d, 79);
			}

			LastRotationRight = !LastRotationRight;
		}

		// Fuzzy movement (currently not used)
		if (Math.abs(fuzzy) > 20) fuzzyV = -fuzzyV;
		fuzzy += fuzzyV;
		fuzzy = 0;

		// For sped up movement, to prevent getting stuck in front of walls
		if (SPEED_UP > 1 && Math.random() > .95d) {
			return new double[]{1.d, 1.d};
		}

		return hasEnteredRoom ? new double[]{SPEED_UP + ((double) fuzzy * SPEED_UP)/20, SPEED_UP - ((double) fuzzy * SPEED_UP)/20} : new double[]{SPEED_UP, SPEED_UP};
	}

	/**
	 * Add movement to action stack
	 * @param lmotor left motor control data
	 * @param rmotor right motor control data
	 * @param ticks tick duration for how long the given motor control data should control the motor
	 */
	private void move(double lmotor, double rmotor, int ticks) {
		for (int i = 0; i < ticks / SPEED_UP; i++) actionStack.add(new double[]{lmotor * SPEED_UP, rmotor * SPEED_UP});
	}


	@Override
	public double[] getControlAction(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}
}
