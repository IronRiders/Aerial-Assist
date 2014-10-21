/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Aaron Jacobson
 */
public class Pressure {
    private static final int TEMP_PORT = 10;
    private int RELAY_PORT = 1;
    private Relay pressureRelay;
    private int SHOOT_PORT = 1;
    private Solenoid shoot;

    /**
     * The constructor for the Pressure class which controls the pneumatics on the robot
     * It assigns the ports for the solenoid and relay
     */
    public Pressure()
    {
        pressureRelay = new Relay(RELAY_PORT);
        shoot = new Solenoid(SHOOT_PORT);
        
    }
    
    /**
     * Gives the relay the value to reverse
     */
    public void relayReverse(){
        pressureRelay.set(Relay.Value.kReverse);
    }

    /**
     * Turns on the relay
     */
    public void relayOn()
    {
        pressureRelay.set(Relay.Value.kForward);
    }
    
    /**
     * Turns the relay off
     */
    public void relayOff() {
        pressureRelay.set(Relay.Value.kOn);
    }
    
    /**
     * Gives the port number of the relay
     * @return the port of the relay
     */
    public int getPressureRelayPort()
    {
        return RELAY_PORT;
    }
    
    /**
     * Turns the solenoid on
     */
    public void solenoidOn(){
        shoot.set(true);
    }
    
    /**
     * Turns the solenoid off
     */
    public void solenoidOff(){
        shoot.set(false);
    }
}
