/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180.Listeners;

import Team4180.Pressure;
import Team4180.Claw;
import Team4180.Input.Attack3Joystick;
import Team4180.Listeners.ClawButtonListener;
/**
 *
 * @author ros_maremash
 */
//this class will select the correct Listener method depending on the button it recives as a perameter.
public class ClawButtonListener implements ButtonListener{
    private Claw claw;
    private Pressure pressure;

    /**
     * Assigns the claw and pressure classes which will be referenced
     * @param claw
     * @param pressure
     */
    public ClawButtonListener(Claw claw,Pressure pressure){
        this.claw = claw;
        this.pressure = pressure;
        
    }
    
    public void buttonUp(Attack3Joystick.Button button) {
        if(button == Attack3Joystick.Button.TRIGGER){
            claw.drumStop();
        }else if(button == Attack3Joystick.Button.BUTTON_6){
            claw.drumStop();
        }else if(button == Attack3Joystick.Button.BUTTON_7){
            claw.drumStop();
        }else if(button == Attack3Joystick.Button.BUTTON_2){
            claw.stopClaw();
        }else if(button == Attack3Joystick.Button.BUTTON_3){
            claw.stopClaw();
        }
    }

    public void buttonDown(Attack3Joystick.Button button) {
        if(button == Attack3Joystick.Button.TRIGGER){
            claw.disengageClutch();
            claw.disengageRatchet();
        } else if(button == Attack3Joystick.Button.BUTTON_2){
            claw.openClaw();
        } else if(button == Attack3Joystick.Button.BUTTON_3){
            claw.closeClaw();
        } else if(button == Attack3Joystick.Button.BUTTON_4){
//            claw.engageRatchet();
//            claw.drumStop();
//            claw.disengageClutch();
        } else if(button == Attack3Joystick.Button.BUTTON_5){
//            claw.disengageRatchet();
//            claw.engageClutch();
//            claw.drumForwards();
        } else if(button == Attack3Joystick.Button.BUTTON_6){
            //claw.drumForwards();  
            pressure.relayOn();
        } else if(button == Attack3Joystick.Button.BUTTON_7){
            //claw.drumBackwards();
            pressure.relayOff();
        } else if(button == Attack3Joystick.Button.BUTTON_8){
            //claw.disengageRatchet();
        } else if(button == Attack3Joystick.Button.BUTTON_9){
            //claw.engageRatchet();
        } else if(button == Attack3Joystick.Button.BUTTON_10){
            pressure.solenoidOn();
            //claw.disengageClutch();
        } else if(button == Attack3Joystick.Button.BUTTON_11){
            pressure.solenoidOff();
            //claw.engageClutch();
        }
    }
}
