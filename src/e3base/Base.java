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
		
		float[] sensorDataLeft = new float[1];
		float[] sensorDataRight = new float[2];
		Movement move = new Movement(left, right);
		
				
		while (Button.ESCAPE.isUp()) {
			leftTouch.getTouchMode().fetchSample(sensorDataLeft, 0);
			rightTouch.getTouchMode().fetchSample(sensorDataRight, 0);
			
			if(sensorDataLeft[0] == 1.0 && sensorDataRight[0] == 1.0) {
				move.forward();
			} else if (sensorDataLeft[0] == 1.0) {
				move.left();
			} else if (sensorDataRight[0] == 1.0) {
				move.right();
			} else {
				move.stop();
			}
		}
	}
	
	public static void runLoop() {
		
	}

}
