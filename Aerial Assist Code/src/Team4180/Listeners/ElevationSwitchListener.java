/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;

import Team4180.Claw;
import Team4180.Robot;

/**
 *
 * @author ros_aljacobson
 */
public class ElevationSwitchListener implements SwitchListener {
    
    private int elevationSwitchPort;
    private Claw claw;
    
    /**
     * Assigns the port and the claw which will be referenced by this class
     * @param claw The Claw to be used
     * @param elevationPort The port of to be used
     */
    public ElevationSwitchListener(Claw claw, int elevationPort) {
        this.claw = claw;
        this.elevationSwitchPort = elevationPort;
    }
    
    public void switchOn (int port) {
    }
    
    public void switchOff (int port) {
    }
    /**
     * This method is called when the state of the switch changes
     * If the switch was hit and the claw is not going up, then the claw is stopped
     * @param port The port of the switch which changed
     * @param state The new state of the port
     */
    public void switchState(int port, boolean state) {
        if(Robot.DEBUG_CLAW_LISTENERS)
        {
            System.out.println("The state of the elevation limit switch is: " + state);
        }
        if(port == elevationSwitchPort && state && !claw.getClawElevationState().equals(Claw.ElevationState.GOING_UP)){
            claw.stopElevation();
            claw.setClawElevationState(Claw.ElevationState.DOWN);
            Robot.log(Robot.DEBUG_CLAW_LISTENERS, "Claw elevation limit hit");
        }
    }
}
