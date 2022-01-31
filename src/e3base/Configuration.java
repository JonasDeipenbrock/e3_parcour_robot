package e3base;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class Configuration {
	
	// Motor Port definitions
    public static final Port leftMotorPort = MotorPort.A;
    public static final Port rightMotorPort = MotorPort.D;
    public static final Port ultrasonicMotorPort = MotorPort.C;
    
    // Sensor Port definitions
    public static final Port leftTouchSensorPort = SensorPort.S1;
    public static final Port rightTouchSensorPort = SensorPort.S2;
    public static final Port colorSensorPort = SensorPort.S3;
    public static final Port ultrasonicSensorPort = SensorPort.S4;
    
    // Car defaults
	public static final double wheelDiameter = 3.2d; //3.15d
	public static final double trackWidth = 21d; //19.2d
}