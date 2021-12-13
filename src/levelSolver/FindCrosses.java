package levelSolver;

import java.util.Random;

import e3base.Helper;
import lejos.hardware.Audio;
import lejos.hardware.ev3.LocalEV3;
import wrappers.ColorSensor;
import wrappers.Movement;
import lejos.robotics.Color;

public class FindCrosses implements ILevelSolver {

	Movement movement;
	Helper helper;
	ColorSensor color;
	Audio audio;

	boolean whiteMissing = true;
	boolean redMissing = true;
	
	@Override
	public void run() {
		movement = Movement.getInstance();
		helper = Helper.getInstance();
		color = ColorSensor.getInstance();
		audio = LocalEV3.get().getAudio();
		color.setToColorIdMode();
		movement.setToMaxSpeed();
		movement.setToMaxAcc();
//		movement.forward();
//		while(helper.checkLoop(true, false)) {
//			//pass
//		}
//		movement.moveByDistance(-5);
//		movement.turnLeft90();
//		while(helper.checkLoop(false, false)) {
//			passOnceLeft();
//			passOnceRight();
//		}
		normalClearing();

	}
	
	//	0 = red
	//  1 = blue
	//  6 = black
	//  7 = white
	
	void yollo() {
		Random rand = new Random();
		movement.moveByDistance(15);
		while(helper.checkLoop(false, false) && (whiteMissing || redMissing)) {
			movement.stop();
			movement.moveByDistance(-5);
			int newAngle = rand.nextInt(90) + 45;
			movement.turn(newAngle);
			movement.forward();
			while(helper.checkLoop(true, false)) {
				int colorId = color.getColor();
				if(colorId == Color.WHITE) {
					whiteMissing = false;
				}
				if(colorId == Color.RED) {
					redMissing = false;
				}
				if(colorId == Color.BLUE) {
					//handle blue line case aka drive back a bit and turn some weird angle
					System.out.println(colorId);
				}
				if(!whiteMissing || !redMissing) {
					System.out.println("one found");
				}
				if (!(whiteMissing || redMissing)) {
					System.out.println("both");
				}
			}
			
		}
		movement.stop();
		audio.systemSound(2);
	}
	
	void normalClearing() {
		//initial drive to corner
		while(helper.checkLoop(true, false)) {
			movement.forward();
			//check color here as well
		}
		movement.stop();
		movement.moveByDistance(-5);
		movement.turnLeft90();
		while(true) {
			passOnceLeft();
			passOnceRight();
		}
	}
	
	void passOnceLeft() {
		movement.forward();
		while(helper.checkLoop(true, false)) {
			//pass
		}
		movement.stop();
		movement.moveByDistance(-5);
		movement.turnLeft90();
		movement.moveByDistance(5);
		movement.turnLeft90();
	}
	
	void passOnceRight() {
		movement.forward();
		while(helper.checkLoop(true, false)) {
			//pass
		}
		movement.stop();
		movement.moveByDistance(-5);
		movement.turnRight90();
		movement.moveByDistance(5);
		movement.turnRight90();
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub

	}

	
	/**
	 * 1. Find crosses: 
	 * 	Move straigt from entrance to other corner and turn 90 left after reaching the wall
	 * 	Repeat pattern:
	 * 		Move to wall and turn left
	 * 		Move 5-10 cm forward and turn left
	 * 		Move to wall and turn right
	 * 		Move 5-10 cm forward and turn right
	 * Until both crosses have been found
	 * 
	 * 2. Colors based on colorId Sensor values
	 * 		0 = red
	 * 		1 = blue
	 * 		6 = white
	 * 		7 = brown
	 * 
	 * 3. Sound based on LocalEv3 get sound as well as colors
	 */
}
