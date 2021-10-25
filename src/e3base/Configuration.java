package e3base;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

public class Configuration {
	
	// Motor Port definitions
    public final Port leftMotorPort = MotorPort.A;
    public final Port rightMotorPort = MotorPort.B;
    public final Port ipMotor = MotorPort.D;
    
    // Sensor Port definitions
    public final Port ipSensorPort = SensorPort.S4;
    public final Port colorSensorPort = SensorPort.S2;
    public final Port leftTouchSensorPort = SensorPort.S1;
    public final Port rightTouchSensorPort = SensorPort.S3;
    
}