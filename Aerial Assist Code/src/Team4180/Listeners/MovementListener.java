/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;
import Team4180.Driving;

/**
 *
 * @author ros_kecooper
 */
public class MovementListener implements JoystickListener {
    private Driving driving;

    /**
     * Assigns the Driving class this will reference
     * @param driving The driving class to be used
     */
        public MovementListener (Driving driving){
        this.driving = driving;
    }
    /**
     * When the joystick has moved, this method is called to update the x y and z values of the Driving class
     * After the values have been updated, it calls reCalcVelocity()
     * @param x The new x value
     * @param y The new y value
     * @param z The new z value
     */
    public void joystickMoved(double x, double y, double z){
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("z = " + z);
        driving.updateX(x);
        driving.updateY(y);
        driving.reCalcVelocity();
    }
}
