package main;

import lejos.robotics.navigation.DifferentialPilot;
import robotcontrol.PortNotDefinedException;
import robotcontrol.Robot;

public abstract class State {
	protected CourseSectionStateMachine stateMachine;
	protected DifferentialPilot pilot;
	protected Robot robot;

	public abstract void init();

	public abstract void run() throws PortNotDefinedException;

	public abstract void leave();

}
