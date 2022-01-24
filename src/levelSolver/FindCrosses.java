package levelSolver;

import java.util.Random;

import e3base.Helper;
import lejos.hardware.Audio;
import lejos.hardware.ev3.LocalEV3;
import wrappers.ColorSensor;
import wrappers.ExitCode;
import wrappers.Movement;
import lejos.robotics.Color;

public class FindCrosses implements ILevelSolver {

	Movement movement;
	Helper helper;
	ColorSensor color;
	Audio audio;

	boolean whiteFound = false;
	boolean redFound = false;
	
	int samplesRequired;
	int whiteSeen = 0;
	int redSeen = 0;
	
	public FindCrosses(int samplesRequired) {
		this.samplesRequired = samplesRequired;
	}
	
	public FindCrosses() {
		this(5);
	}
	
	@Override
	public ExitCode run() {
		movement = Movement.getInstance();
		helper = Helper.getInstance();
		color = ColorSensor.getInstance();
		audio = LocalEV3.get().getAudio();
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
		movement.stopCorrected();
		audio.systemSound(2);
		return ExitCode.SUCCESSFULL;
	}
	
	//	0 = red
	//  1 = blue
	//  6 = black
	//  7 = white
	
//	void yollo() {
//		Random rand = new Random();
//		movement.moveByDistance(15);
//		while(helper.checkLoop(false, false) && (whiteMissing || redMissing)) {
//			movement.stopCorrected();
//			movement.moveByDistance(-5);
//			int newAngle = rand.nextInt(90) + 45;
//			movement.turn(newAngle);
//			movement.forward();
//			while(helper.checkLoop(true, false)) {
//				int colorId = color.getColor();
//				if(colorId == Color.WHITE) {
//					whiteMissing = false;
//				}
//				if(colorId == Color.RED) {
//					redMissing = false;
//				}
//				if(colorId == Color.BLUE) {
//					//handle blue line case aka drive back a bit and turn some weird angle
//					System.out.println(colorId);
//				}
//				if(!whiteMissing || !redMissing) {
//					System.out.println("one found");
//				}
//				if (!(whiteMissing || redMissing)) {
//					System.out.println("both");
//				}
//			}
//			
//		}
//		movement.stop();
//		audio.systemSound(2);
//	}
	
	/**
	 * Trys to find the crosses using a cross pattern
	 */
	void normalClearing() {
		//initial drive to corner
		movement.moveByDistance(10);
		movement.forward();
		while(helper.checkLoop(true, false)) {
			if(checkColorCondition()) {
				return;
			}
		}
		movement.stopCorrected();
		movement.moveByDistance(-10);
		movement.turnLeft90();
		movement.moveByDistance(-5);
		while(true) {
			if(passOnceLeft()) {
				return;
			}
			if(passOnceRight()) {
				return;
			}
		}
	}
	
	/**
	 * Goes from left to right turning Left in the end
	 * @return true if the crosses both have been found
	 */
	boolean passOnceLeft() {
		movement.forward();
		while(helper.checkLoop(true, false)) {
			if(checkColorCondition()) {
				return true;
			}
		}
		movement.moveByDistance(5);
		movement.moveByDistance(-5);
		movement.turnLeft90();
		movement.moveByDistance(5);
		movement.turnLeft90();
		movement.moveByDistance(-8);
		return false;
	}
	
	/**
	 * Goes from right to left turning right in the end
	 * @return true if the crosses both have been found
	 */
	boolean passOnceRight() {
		movement.forward();
		while(helper.checkLoop(true, false)) {
			if(checkColorCondition()) {
				return true;
			}
		}
		movement.moveByDistance(5);
		movement.moveByDistance(-5);
		movement.turnRight90();
		movement.moveByDistance(5);
		movement.turnRight90();
		movement.moveByDistance(-8);
		return false;
	}
	
	/*
	 * Returns true when both colors have been found
	 */
	boolean checkColorCondition() {
		int colorId = color.getColor();
		
		//increment the found color
		if(colorId == Color.WHITE) {
			whiteSeen++;
		} else if(colorId == Color.RED) {
			redSeen++;
		} else {
			whiteSeen = 0;
			redSeen = 0;
		}
		
		//check if color was found multiple times
		if(!whiteFound && whiteSeen >= samplesRequired) {
			audio.systemSound(1);
			whiteFound = true;
		}
		if(!redFound && redSeen >= samplesRequired) {
			audio.systemSound(2);
			redFound = true;
		}
		
		if(colorId == Color.BLUE) {
			//handle blue line case aka drive back a bit and turn some weird angle
			System.out.println(colorId);
		}
		if(whiteFound || redFound) {
			System.out.println("one found");
		}
		if (whiteFound && redFound) {
			System.out.println("both");
		}
		return whiteFound && redFound;
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
