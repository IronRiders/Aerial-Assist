/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;

import Team4180.Input.Attack3Joystick;
import Team4180.Pressure;
import Team4180.Robot;

/**
 *
 * @author Iron Rider
 */
public class PressureLimitListener implements SwitchListener {

    private Pressure PRESSURE_CONTROL;
    
    /**
     * Assigns the Pressure class
     * @param pressure The Pressure class that this will reference
     */
    public PressureLimitListener(Pressure pressure){
        this.PRESSURE_CONTROL = pressure;
    }
    public void switchState(int port, boolean state)
    {
    }

    public void switchOn(int port) {
    }
    /**
     * When the switch is release the relay will be turned off
     * @param port 
     */
    public void switchOff(int port) {
        PRESSURE_CONTROL.relayOff();
    }
}
