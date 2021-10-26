package e3base;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class Base {
	
	// Motors
	public static EV3LargeRegulatedMotor left = new EV3LargeRegulatedMotor(Configuration.leftMotorPort);
	public static EV3LargeRegulatedMotor right = new EV3LargeRegulatedMotor(Configuration.rightMotorPort);
	public static EV3MediumRegulatedMotor irMotor = new EV3MediumRegulatedMotor(Configuration.irMotorPort);
	
	//Sensors
	public static EV3TouchSensor leftTouch = new EV3TouchSensor(Configuration.leftTouchSensorPort);
	public static EV3TouchSensor rightTouch = new EV3TouchSensor(Configuration.rightTouchSensorPort);
	public static EV3ColorSensor colorSensor = new EV3ColorSensor(Configuration.colorSensorPort);
	public static EV3IRSensor ipSensor = new EV3IRSensor(Configuration.irSensorPort);

	public static void main(String[] args) {
		
//		float[] sensorDataLeft = new float[1];
//		float[] sensorDataRight = new float[2];
		Movement move = new Movement(left, right);
		 
		DifferentialPilot robot = new DifferentialPilot(
				Configuration.wheelDiameter, 
				Configuration.trackWidth, 
				left, 
				right, 
				true);
		System.out.println("Press any button!");
		Button.waitForAnyPress();
		robot.setAcceleration(4000);
		robot.setTravelSpeed(20);
		robot.setRotateSpeed(180);
		System.out.println("Going forwards");
//		robot.travel(20);
//		robot.rotate(180);
//		robot.rotate(-1000);
		while(robot.isMoving())Thread.yield();
		System.out.println("Stopped");
		
//		ipMotor.forward();
		irMotor.rotate(90);
//		Delay.msDelay(600);
		Delay.msDelay(1000);
		irMotor.rotate(-90);
//		ipMotor.stop();
//		Delay.msDelay(1000);
//		ipMotor.backward();
//		Delay.msDelay(600);
//		ipMotor.stop(false);
		
//		try {
//			move.turnLeft90();
//			Thread.sleep(1000);
//			move.turnRight90();
//			ipMotor.forward();
//			Thread.sleep(1000);
//			ipMotor.stop();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
				
		while (Button.ESCAPE.isUp()) {
//			leftTouch.getTouchMode().fetchSample(sensorDataLeft, 0);
//			rightTouch.getTouchMode().fetchSample(sensorDataRight, 0);
//			
//			if(sensorDataLeft[0] == 1.0 && sensorDataRight[0] == 1.0) {
//				move.forward();
//			} else if (sensorDataLeft[0] == 1.0) {
//				move.left();
//			} else if (sensorDataRight[0] == 1.0) {
//				move.right();
//			} else {
//				move.stop();
//			}
		}
	}
	
	public static void runLoop() {
		
	}

}
