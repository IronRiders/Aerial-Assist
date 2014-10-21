/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Team4180.Listeners;

import Team4180.Claw;

/**
 *
 * @author ros_aljacobson
 */
public class BallCaughtListener implements SwitchListener {

    private int ballCaughtLimitSwitchPort;
    private Claw claw;

    /**
     * Assigns the claw which will be reference by this class
     * Assigns the port number to be checked
     * @param claw The claw which will be used
     * @param ballCaughtPort The limit switch port which will be used
     */
    public BallCaughtListener(Claw claw, int ballCaughtPort) {
        this.claw = claw;
        this.ballCaughtLimitSwitchPort = ballCaughtPort;
    }

    public void switchOn(int port) {
    }

    public void switchOff(int port) {
    }

    /**
     * If the port that was hit is the right port it stops the claw
     * from breaking either the motor or the ball
     * @param port The port number to check
     * @param state The state of the limit switch that was hit
     */
    public void switchState(int port, boolean state) { 
        if(port == ballCaughtLimitSwitchPort && state && claw.getClawState().equals(Claw.State.CLOSING))
        {
            claw.setClawState(Claw.State.CLOSED);
            claw.stopClaw();
            System.out.println("The ball has been caught");
        }
    }

}
