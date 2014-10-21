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
    private String clawState;
    private String clawElevationState;
    

    //These are which ports each motor is on the robot

    /**
     * if hardware has not given us a port yet then TEMP_PORT is used
     */
        public static final int TEMP_PORT = 10;

    /**
     * The port for the motor on the robot which opens and closes the claw.
     */
    public static final int CLAW_OPEN_CLOSE_PORT = 6;

    /**
     * The port number for the motor which moves the claw up and down.
     */
    public static final int CLAW_RAISE_LOWER_PORT = 5;

    /**
     * The port number for the servo which controls the ratchet on the claw firing mechanism.
     */
    public static final int RATCHET_PORT = 10;

    /**
     * The port number for the motor which acts as the drum which pulls the spring back.
     */
    public static final int DRUM_PORT = 4;

    /**
     * The port number for the servo which controls the clutch on the robot firing mechanism.
     */
    public static final int CLUTCH_SERVO_PORT = 7;//not temp

    /**
     * The angle which works best for disengaging the clutch.
     */
    public static final double DISENGAGE_CLUTCH_ANGLE = 70; // -20

    /**
     * The angle which works best for engaging the clutch.
     */
    public static final double ENGAGE_CLUTCH_ANGLE = 180; // -100

    /**
     * The angle which works best for disengaging the ratchet.
     */
    public static final double DISENGAGE_RATCHET_ANGLE = 100;

    /**
     * The angle that works best for engaging the ratchet.
     */
    public static final double ENGAGE_RATCHET_ANGLE = 0;

    /**
     * The close claw speed value.(at maximum)
     */
        public final double CLOSE_CLAW_VALUE = -1;

    /**
     * The open claw speed value(at maximum)
     */
    public final double OPEN_CLAW_VALUE = 1;

    /**
     * The speed which is used to spin the drum forwards (at maximum)
     */
    public final double DRUM_FORWARD_SPEED = 1;

    /**
     * The speed which is used to spin the drum backwards (at maximum)
     */
    public final double DRUM_BACKWARDS_SPEED = -1;

    /**
     *
     * @return The clutch assigned to the claw
     */
    public Servo getClutch() {
        return clutch;
    }

    /**
     * This class holds of the states that the claw can be in.
     */
    public final class State {

        /**
         * This is the fully open state which is only active if the claw is 
         * fully opened and the driver is not giving the claw any commands
         */
        public static final String OPEN = "open";

        /**
         * This is the state that the claw is in when the driver is giving the command
         * to open the claw.
         */
        public static final String OPENING = "opening";

        /**
         * This state is only active when the claw is fully closed and the driver 
         * is not giving any commands to move the claw
         */
        public static final String CLOSED = "closed";

        /**
         * This is the state which the claw is in only if the driver is giving 
         * the command to close the claw.
         */
        public static final String CLOSING = "closing";
    }
    
    /**
     * This class holds all the states that the claw could be in
     * in terms of its elevation
     * 
     * There is no UP state because there is no limit switch to detect of the claw
     * is going to far up
     */
    public final class ElevationState
    {

        /**
         * This state is only active if the driver is giving the command to 
         * move the claw up
         */
        public static final String GOING_UP = "going up";

        /**
         * This state is only active if the driver is giving the command
         * to move the claw down.
         */
        public static final String GOING_DOWN = "going down";

        /**
         * This state is only active if the claw is fully down and the driver
         * isn't giving any commands to change the elevation of the claw.
         */
        public static final String DOWN = "down";
    }

    /**
     * The constructor of the claw class which assigns all the port numbers and the initial states
     */
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

    /**
     * This method is only called when the "open claw" button has been pressed. 
     * It sends the motor on the claw the value to open
     */
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

    /**
     * This method is only called when the "close claw" button is pressed
     * It sends the motor on the claw the value to close the claw.
     */
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
    
    /**
     *
     * @param state The state which the clawState needs to be set to
     */
    public void setClawState(String state) {
        clawState = state;
        Robot.log(Robot.DEBUG_CLAW, "Claw state: " + state);
    }
    
    /**
     *
     * @param state The state which the moveClaw state needs to be set to
     */
    public void setClawElevationState(String state)
    {
        String old = clawElevationState;
        clawElevationState = state;
        if (old != null && !old.equals(state))
            Robot.log(Robot.DEBUG_CLAW, "Claw elevation state: " + state);;
    }

    /**
     *
     * @return Gives the state of the claw
     */
    public String getClawState() {
        return clawState;
    }
    
    /**
     *
     * @return Gives the elevation state of the claw.
     */
    public String getClawElevationState()
    {
        return clawElevationState;
    }

    /**
     * Sets the speed of the claw motor to 0, which stops the claw from moving
     */
    public void stopClaw() {
        double old = openClose.get();
        openClose.set(0);
        if (old != 0)
            Robot.log(Robot.DEBUG_CLAW, "Claw stopped");
    }

    /**
     *
     * @param y The value y needs to be set to. Should only take in a double from -1 to 1
     */
    public void updateY(double y) {
        yValueNow = y;
    }

    /**
     * Sets the claw speed to the value needed to move
     * The speed is taken from yValueNow
     */
    public void moveClaw() {
        double newY = ((double) (int) (yValueNow * 100)) / 100;
        raiseLower.set(newY);
        if(newY > 0) {
            setClawElevationState(Claw.ElevationState.GOING_UP);
        }
        else if(newY < 0 && !getClawElevationState().equals(Claw.ElevationState.DOWN)) {
            setClawElevationState(Claw.ElevationState.GOING_DOWN);
        }
    }

    /**
     * Sets the claw elevation speed to 0, this stops the claw from moving
     */
    public void stopElevation() {
        raiseLower.set(0);
        Robot.log(Robot.DEBUG_CLAW, "Claw elevation stopped");
    }

    /**
     * Uses the ENGAGE_CLUTCH_ANGLE value to engage the clutch
     */
    public void engageClutch() {
        clutch.setAngle(ENGAGE_CLUTCH_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Clutch engaged.  Angle: " + clutch.getAngle());
    }

    /**
     * Uses the DISENGAGE_CLUTCH_ANGLE to disengage the clutch
     */
    public void disengageClutch() {
        clutch.setAngle(DISENGAGE_CLUTCH_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Clutch disengaged. Angle: " + clutch.getAngle());
    }

    /**
     * Uses the DRUM_FORWARD_SPEED to have the drum pin forwards
     */
    public void drumForwards() {
        drumMotor.set(DRUM_FORWARD_SPEED);
        Robot.log(Robot.DEBUG_CLAW, "Moving the drum forwards");
    }

    /**
     * Uses the DRUM_BACKWARDS_SPEED to have the drum spin backwards
     */
    public void drumBackwards() { 
        drumMotor.set(DRUM_BACKWARDS_SPEED);
        Robot.log(Robot.DEBUG_CLAW, "Moving the drum forwards");
    }

    /**
     * Stops the drum motor
     */
    public void drumStop() {
        drumMotor.stopMotor();
        Robot.log(Robot.DEBUG_CLAW, "Drum stopped");
    }

    /**
     * Uses the DISENGAGE_RACTHET_ANGEL to disengage the ratchet
     * This is used to fire the ball.
     */
        public void disengageRatchet() {
        ratchet.set(DISENGAGE_RATCHET_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Ratchet disengaged");
    }

    /**
     * Uses the ENGAGE_RATCHET_ANGLE to engage the ratchet
     */
        public void engageRatchet() {
        ratchet.set(ENGAGE_RATCHET_ANGLE);
        Robot.log(Robot.DEBUG_CLAW, "Ratchet engaged");
    }
    
    /**
     * Used only during autonomous
     * @param speed The speed which the claw will open at
     */
    public void openClawA(double speed)
    {
        openClose.set(speed);
        Robot.log(Robot.DEBUG_CLAW, "Opening claw...");
        setClawState(State.OPENING);
    }
    
    /**
     * Used only during autonomous
     * @param speed The speed which the claw will close at
     */
    public void closeClawA(double speed)
    {
        openClose.set(-speed);
        Robot.log(Robot.DEBUG_CLAW, "Closing claw...");
        setClawState(State.CLOSING);
    }
}
