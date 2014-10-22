/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Team4180.Listeners;

import Team4180.Driving;
import Team4180.Input.Attack3Joystick;
import edu.wpi.first.wpilibj.Servo;

/**
 *
 * @author ros_diwang
 */
public class TriggerListener implements ButtonListener {

    private Driving driving;
    /**
     *
     * @param driving The driving class this will reference
     */
    public TriggerListener(Driving driving) {
        this.driving = driving;
    }
    /**
     * When the button is released the robot will be able to turn
     * @param button the button that was pressed
     */
    public void buttonUp(Attack3Joystick.Button button) {
        driving.driveStraight(false);
    }
    /**
     * When the button is down the robot won't be able to turn
     * @param button The button that was pressed
     */
    public void buttonDown(Attack3Joystick.Button button) {
        driving.driveStraight(true);
    }

}