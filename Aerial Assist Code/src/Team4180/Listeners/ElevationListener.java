/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;

import Team4180.Driving;

/**
 *
 * @author ros_haremash
 */

//MOVING UP ASSEMBLY: LEFT MOVEMENT LISTENER
public class ElevationListener implements JoystickListener{
    private Driving drive;
    
    /* These methods (ElevationListener and joystickMoved) update the y value on
     * joystick2 which raises or lowers the claw using a lead screw.
     */
    public ElevationListener (Driving drive){
        this.drive = drive;
    }
    //Updates position of joystick claw
    //Takes in the coordinates of the joystick
    public void joystickMoved(double x, double y, double z){
        drive.updateLeftY(y);
        //claw.clawElevation();
        drive.reCalcLeftVelocity();
    }
}