package robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class RobotMouse {

	public static void main(String... args) {
		RobotMouse robot = new RobotMouse();
		robot.init();
	}
	
	public RobotMouse() {
		super();
	}
	
	/**
	 * Posune položku pod ikonou koše o jednu doprava 
	 */
	public void init() {
		try {
			Robot r = new Robot();
			r.mouseMove(50, 250);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseMove(120, 250);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
