/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;

import Team4180.Claw;
import Team4180.Robot;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author ros_haremash
 */
public class BallDetectListener implements SwitchListener {
    private Claw claw;
    private int ballDetectPort;
    private boolean isLoaded = false;
    
    /**
     * Assigns the claw which will be referenced by this class
     * Assigns the limit switch which will be used
     * @param claw
     * @param ballDetectPort
     */
    public BallDetectListener(Claw claw, int ballDetectPort) {
        this.claw = claw;
        this.ballDetectPort = ballDetectPort;
    }
    
    public void switchOn (int port) {
    }
    
    public void switchOff (int port) {
    }

    /**
     * If the right port was hit then it starts closing the claw to catch the ball
     * @param port The port number to check
     * @param state The state of the port
     */
    public void switchState(int port, boolean state) {
        if(port == ballDetectPort && state && !(claw.getClawState().equals(Claw.State.CLOSED))){
            System.out.println("The ball has been detected. And the state is: " + state);
            Robot.BALL_DETECT_TIME = System.currentTimeMillis();
            claw.closeClawA(1);
        }
    }
}