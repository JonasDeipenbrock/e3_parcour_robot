package e3base;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

public class Base {
	
	// Motors
	public static EV3LargeRegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.A);
	public static EV3LargeRegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.B);
	
	//Sensors
	public static EV3TouchSensor leftTouch = new EV3TouchSensor(SensorPort.S1);
	public static EV3TouchSensor rightTouch = new EV3TouchSensor(SensorPort.S2);

	public static void main(String[] args) {
				
		while (Button.ESCAPE.isUp()) {
			runLoop();
		}
	}
	
	public static void runLoop() {
		
	}

}
