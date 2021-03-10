package main;

import lejos.hardware.Sound;
import robotcontrol.PortNotDefinedException;

public class CourseSectionStateMachine {
	/**
	 * Delay between calling the run method on the current state.
	 */
	private final static int STATE_RUN_DELAY = 20; //ms

	private int state = 0;
	private State[] states;
	private boolean running = true;

	protected CourseSectionStateMachine() {
		Sound.beepSequence();
		Sound.playTone(2000, 1000);
	}

	protected void run() {
		states[state].init();

		while (running) {
			try {
				states[state].run();
				Thread.sleep(STATE_RUN_DELAY);
			} catch (PortNotDefinedException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void changeState(int state) {
		this.states[this.state].leave();
		this.state = state;
		this.states[state].init();
	}

	protected void setStates(State[] states) {
		if (states.length < 1) {
			throw new IllegalArgumentException("At least one state is needed for a statemachine to work");
		}
		this.states = states;
	}

	public void stop() {
		running = false;
		this.states[state].leave();
	}
}
