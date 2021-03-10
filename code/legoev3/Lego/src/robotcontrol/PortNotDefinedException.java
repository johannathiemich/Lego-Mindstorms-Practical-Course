package robotcontrol;

/**
 * A device was attempted to be accessed, however the port of this
 * device was not defined.
 * <p>
 * Solve the problem by connecting the device and defining the port
 * in the roboter configuration object or try to avoid using the
 * device.
 */
public class PortNotDefinedException extends RobotException {
	public PortNotDefinedException(String msg) {
		super(msg);
	}
}
