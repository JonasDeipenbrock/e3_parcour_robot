package e3base;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class Configuration {
	
	// Motor Port definitions
    public static final Port leftMotorPort = MotorPort.A;
    public static final Port rightMotorPort = MotorPort.B;
    public static final Port irMotorPort = MotorPort.D;
    
    // Sensor Port definitions
    public static final Port irSensorPort = SensorPort.S4;
    public static final Port colorSensorPort = SensorPort.S3;
    public static final Port leftTouchSensorPort = SensorPort.S1;
    public static final Port rightTouchSensorPort = SensorPort.S2;
    
    // Car defaults
	public static final double wheelDiameter = 3.15d;
	public static final double trackWidth = 19.2d;
}