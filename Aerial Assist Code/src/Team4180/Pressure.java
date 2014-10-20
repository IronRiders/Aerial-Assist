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
    public Pressure()
    {
        pressureRelay = new Relay(RELAY_PORT);
        shoot = new Solenoid(SHOOT_PORT);
        
    }
    
    public void relayReverse(){
        pressureRelay.set(Relay.Value.kReverse);
    }
    public void relayOn()
    {
        pressureRelay.set(Relay.Value.kForward);
    }
    
    public void relayOff() {
        pressureRelay.set(Relay.Value.kOn);
    }
    
    public int getPressureRelayPort()
    {
        return RELAY_PORT;
    }
    
    public void solenoidOn(){
        shoot.set(true);
    }
    
    public void solenoidOff(){
        shoot.set(false);
    }
}
