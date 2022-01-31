package wrappers;

import e3base.Configuration;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class UltrasonicPosition {
    
    static private UltrasonicPosition singleton;
    private EV3MediumRegulatedMotor ultrasonicMotor;
    private boolean upState = false;

    private UltrasonicPosition() {
        ultrasonicMotor = new EV3MediumRegulatedMotor(Configuration.ultrasonicMotorPort);
        // rotate until is down
//        ultrasonicMotor.forward();
//        ultrasonicMotor.setStallThreshold(1, 50);
//        while (!ultrasonicMotor.isStalled()) {};
//        ultrasonicMotor.stop();
    }
    
    public static UltrasonicPosition getInstance() {
        if(singleton == null) {
            singleton = new UltrasonicPosition();
        }
        
        return singleton;
    }

    public boolean isUP() {
        return upState;
    }

    public void moveUP() {
        if (upState == false) {
            ultrasonicMotor.rotate(-110);
            upState = true;
        }
    }

    public void moveDown() {
        if (upState == true) {
            ultrasonicMotor.rotate(110);
            upState = false;
        }
    }
    
    public static void close() {
        if (singleton != null) {
            //singleton.moveDown();
            singleton.ultrasonicMotor.close();
            singleton = null;
        }
    }

}
