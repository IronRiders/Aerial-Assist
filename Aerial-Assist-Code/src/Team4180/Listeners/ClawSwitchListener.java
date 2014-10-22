/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Team4180.Listeners;

import Team4180.Claw;

/**
 *
 * @author ros_haremash
 */
public class ClawSwitchListener implements SwitchListener {

    private int closedLimitSwitchPort;
    private int openLimitSwitchPort;
    private Claw claw;

    /**
     * Assigns the claw which will be referenced by this
     * Assigns the ports which will be used
     * @param claw The claw that will be used
     * @param closePort The port on the front of the claw
     * @param openPort The port on the back of the claw
     */
    public ClawSwitchListener(Claw claw, int closePort, int openPort) {
        this.claw = claw;
        this.closedLimitSwitchPort = closePort;
        this.openLimitSwitchPort = openPort;
    }

    public void switchOn(int port) {
    }

    public void switchOff(int port) {
    }

    /**
     * If the openClawLimitSwitch was hit then it stops the claw from opening too much
     * If the closeClawLimitSwitch was hit then it stops the claw from closing too much
     * @param port The port number of the switch that was changed
     * @param state The new state of the limit switch
     */
    public void switchState(int port, boolean state) {
        if(port == openLimitSwitchPort && state && claw.getClawState().equals(Claw.State.OPENING))
        {
            claw.setClawState(Claw.State.OPEN);
            claw.stopClaw();
            System.out.println("The claw has opened" + state);
        }else if(port == closedLimitSwitchPort && state && claw.getClawState().equals(Claw.State.CLOSING))
        {
            claw.setClawState(Claw.State.CLOSED);
            claw.stopClaw();
            System.out.println("The claw has closed" + state);
            System.out.println();
        }
    }

}
