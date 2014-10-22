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

//MOVING UP ASSEMBLY: LEFT MOVEMENT LISTENER
public class ElevationListener implements JoystickListener{
    private Claw CLAW;

    /**
     * Assigns the claw which will be referenced by this class
     * @param claw
     */
    
    public ElevationListener (Claw claw){
        CLAW = claw;
    }
    /**
     * When the joystick is moved this method updates the y value of the Claw and moves it
     * @param x
     * @param y the new y value
     * @param z 
     */
    public void joystickMoved(double x, double y, double z) {
        CLAW.updateY(y);
        CLAW.moveClaw();
    }
}