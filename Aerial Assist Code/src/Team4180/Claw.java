/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Team4180;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author ros_aljacobson
 */
public class Claw {

    private Talon openClose;
    private Jaguar raiseLower;
    //private Talon pullBackPiston;
    private Talon drumMotor;
    private Servo ratchet;
    private Servo clutch;
    private double yValueNow;
    private double zValueNow = 1;
    private String clawState;
    private String clawElevationState;
    

    //These are which ports each motor is on the robot
    public static final int TEMP_PORT = 10; //if hardware has not given us a port yet then TEMP_PORT is used
    public static final int CLAW_OPEN_CLOSE_PORT = 6;
    public static final int CLAW_RAISE_LOWER_PORT = 5;
    public static final int RATCHET_PORT = 10;
    public static final int DRUM_PORT = 4;
    public static final int CLUTCH_SERVO_PORT = 7;//not temp

    public static final double DISENGAGE_CLUTCH_ANGLE = 70; // -20
    public static final double ENGAGE_CLUTCH_ANGLE = 180; // -100
    public static final double DISENGAGE_RATCHET_ANGLE = 100;
    public static final double ENGAGE_RATCHET_ANGLE = 0;
    
    //controls the speed of the talons for the claw and drum
    public final double CLOSE_CLAW_VALUE = -1;
    public final double OPEN_CLAW_VALUE = 1;
    public final double DRUM_FORWARD_SPEED = 1;
    public final double DRUM_BACKWARDS_SPEED = -1;

    public Servo getClutch() {
        return clutch;
    }

    public final class State {
        public static final String OPEN = "open";
        public static final String OPENING = "opening";
        public static final String CLOSED = "closed";
        public static final String CLOSING = "closing";
    }
    
    public final class ElevationState
    {
        public static final String GOING_UP = "going up";
        public static final String GOING_DOWN = "going down";
        public static final String DOWN = "down";
    }

    public Claw() {
        //This assigns the ports to each of the seperate motors (talons and servos.
        //each motor will get input from the robot through its assigned port.
        openClose = new Talon(CLAW_OPEN_CLOSE_PORT);
        raiseLower = new Jaguar(CLAW_RAISE_LOWER_PORT);
        //pullBackPiston = new Talon(PULL_BACK_PISTON_PORT);
        drumMotor = new Talon(DRUM_PORT);
        ratchet = new Servo(RATCHET_PORT);
        
        clutch = new Servo(CLUTCH_SERVO_PORT);
        yValueNow = 0;
        clawState = State.CLOSED;
        clawElevationState = ElevationState.GOING_UP;
    }

    //This method opens the claw. It takes in a double from a button listener(ClawOpenListener).
    public void openClaw() {
        Robot.log(Robot.DEBUG_CLAW, "Opening claw...");
        if (!clawState.equals(State.OPEN)) {
            openClose.set(OPEN_CLAW_VALUE);
            setClawState(State.OPENING);
            System.out.println("opening claw");
        }
        else {
            Robot.log(Robot.DEBUG_CLAW, "Cannot open claw more");
        }
    }
    
    //This method closes the claw. It takes a negative double from the ClawCloseListener.
    public void closeClaw() {
        Robot.log(Robot.DEBUG_CLAW, "Closing claw...");
        if (!clawState.equals(State.CLOSED)) {
            openClose.set(CLOSE_CLAW_VALUE);
            setClawState(State.CLOSING);
        }
        else {
            Robot.log(Robot.DEBUG_CLAW, "Cannot close claw more");
        }
    }
    
    public void setClawState(String state) {
        clawState = state;
        Robot.log(Robot.DEBUG_CLAW, "Claw state: " + state);
    }
    
    public void setClawElevationState(String state)
    {
        String old = clawElevationState;
        clawElevationState = state;
        if (old != null && !old.equals(state))
            Robot.log(Robot.DEBUG_CLAW, "Claw elevation state: " + state);;
    }

    public String getClawState() {
        return clawState;
    }
    
    public String getClawElevationState()
    {
        return clawElevationState;
    }

    public void stopClaw() {
        double old = openClose.get();
        openClose.set(0);
        if (old != 0)
            Robot.log(Robot.DEBUG_CLAW, "Claw stopped");
    }

    public void updateY(double y) {
        yValueNow = y;
    }

    public void updateZ(double z) {
        zValueNow = z;
    }

    public void clawElevation() {
        double newY = ((double) (int) (yValueNow * 100)) / 100;
        raiseLower.set(newY);
        if(newY > 0) {
            setClawElevationState(Claw.ElevationState.GOING_UP);
        }
        else if(newY < 0 && !getClawElevationState().equals(Claw.ElevationState.DOWN)) {
            setClawElevationState(Claw.ElevationState.GOING_DOWN);
        }
    }

    public void stopElevation() {
        raiseLower.set(0);
        Robot.log(Robot.DEBUG_CLAW, "Claw elevation stopped");
    }

    //This method releases the ratchet and releases the spring system to let the robot shoot the ball.
    //When the cocking servo reaches the angle (100 degrees) that is equal to the disengae clutch angle
    //then the disengage ratchet method is called.
    public void fire() {
        //100 --> class constant
        disengageRatchet();
        //if (clutch.get() == DISENGAGE_CLUTCH_ANGLE) {
          //  disengageRatchet();
        //}
        Timer.delay(1);
        reload();
    }

    //Automatically reloads the shooting mechanism
    public void reload() {
        engageRatchet();
        engageClutch();
        drumBackwards();
    }

    public void stopReload() {
        drumStop();
        disengageClutch();
    }

    public void engageClutch() {
        clutch.setAngle(ENGAGE_CLUTCH_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Clutch engaged.  Angle: " + clutch.getAngle());
    }

    public void disengageClutch() {
        clutch.setAngle(DISENGAGE_CLUTCH_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Clutch disengaged. Angle: " + clutch.getAngle());
    }

    public void drumForwards() {
        drumMotor.set(DRUM_FORWARD_SPEED);
        Robot.log(Robot.DEBUG_CLAW, "Moving the drum forwards");
    }

    public void drumBackwards() { 
        drumMotor.set(DRUM_BACKWARDS_SPEED);
        Robot.log(Robot.DEBUG_CLAW, "Moving the drum forwards");
    }

    public void drumStop() {
        drumMotor.stopMotor();
        Robot.log(Robot.DEBUG_CLAW, "Drum stopped");
    }

    //This method disengages the ratchet to allow fireing. 
    //This method is called upon in the method fire.
    public void disengageRatchet() {
        ratchet.set(DISENGAGE_RATCHET_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Ratchet disengaged");
    }

    //This method engages the ratchet to hold the system in place after the reload process.
    //The method is used when called upon by the auto reload.
    public void engageRatchet() {
        ratchet.set(ENGAGE_RATCHET_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Ratchet engaged");
    }
    
    public void increaseClutchAngle() {
        clutch.setAngle(clutch.getAngle()+1);
        Robot.log(Robot.DEBUG_CLAW, "Increased clutch angle to " + clutch.getAngle());
    }
    
    public void decreaseClutchAngle() {
        clutch.setAngle(clutch.getAngle()-1);
        Robot.log(Robot.DEBUG_CLAW, "Decreased clutch angle to " + clutch.getAngle());
    }
    
    public void openClawA(double speed)
    {
        openClose.set(speed);
        Robot.log(Robot.DEBUG_CLAW, "Opening claw...");
        setClawState(State.OPENING);
    }
    
    public void closeClawA(double speed)
    {
        openClose.set(-speed);
        Robot.log(Robot.DEBUG_CLAW, "Closing claw...");
        setClawState(State.CLOSING);
    }
}
