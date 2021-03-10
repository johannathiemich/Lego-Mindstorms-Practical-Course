package main;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.TextMenu;

public class Menu {
	public static final String MENU_TITLE = "Pls select Start"; //Max 16 chars.
	private TextMenu courseSectionMenu;

	public Menu() {
		CourseSections startingSection = displayMenu();
		new CourseSectionManager(startingSection);
	}

	/**
	 * Displays all course sections on the EV3 Screen
	 *
	 * @return The starting section selected by the user
	 */
	private CourseSections displayMenu() {
		String[] sectionNames = CourseSections.names();
		courseSectionMenu = new TextMenu(sectionNames, 1, MENU_TITLE);
		int selection = courseSectionMenu.select();

		//DEBUG
		for (int i = 0; i < selection; i++) {
			Sound.beep();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (selection < 0 || selection >= sectionNames.length) {
			Sound.beepSequence();
			throw new InternalError("Men� is immernoch am Arsch");
		}


		LocalEV3.get().getGraphicsLCD().clear();
		//Vll. bissel unsafe
		return CourseSections.values()[selection];
	}

	public static void main(String[] args) {
		
		Sound.playSample(new File("sm64_game_over.wav"));
		Sound.beepSequenceUp();
		Button.ESCAPE.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(Key k) {
			}

			@Override
			public void keyPressed(Key k) {
				Sound.beepSequence();
				System.exit(0);
			}
		});
		
		new Menu();
	}
}
