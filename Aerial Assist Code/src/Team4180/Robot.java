/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package Team4180;

import Team4180.Input.Attack3Joystick;
import Team4180.Input.Attack3Joystick.Button;
import Team4180.Input.DigitalInputHandler;
import Team4180.Listeners.BallCaughtListener;
import Team4180.Listeners.BallDetectListener;
//import Team4180.Listeners.ButtonListener;
import Team4180.Listeners.ClawButtonListener;
import Team4180.Listeners.ClawSwitchListener;
import Team4180.Listeners.TriggerListener;
import Team4180.Listeners.MovementListener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import Team4180.Listeners.ElevationListener;
import Team4180.Listeners.ElevationSwitchListener;
import Team4180.Listeners.PressureLimitListener;
import Team4180.Listeners.ReloadSwitchListener;
import Team4180.Listeners.SwitchListener;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    
    public static final boolean DEBUG_SWITCH_LISTENER = false;
    public static final boolean DEBUG_CLAW = true;
    public static final boolean DEBUG_CLAW_LISTENERS = true;
    
    // Setting this to true will spin the clutch as long as the buttons are pushed
    // It will not spin to predetermined angles
    public static final boolean INCREMENTAL_CLUTCH = false;
    
    public static float BALL_DETECT_TIME;
    
    //the port numbers of the limit switches
    public static final int TEMP_PORT = 10;
    public static final int PRESSURE_LIMIT_PORT = 4;
    public static final int RELAY_PORT = TEMP_PORT;
    public static final int CLOSECLAW_LIMIT_SWITCH = 8;
    public static final int OPENCLAW_LIMIT_SWITCH = 14;
    public static final int RELOAD_LIMIT_SWITCH = 5;//was TRIGGER_LIMIT_SWITCH We dont have one of these
    public static final int ELEVATION_SWITCH_LISTENER_PORT = 2;
    public static final int DETECT_BALL_PORT = 13;
    public static final int BALL_CAUGHT_PORT = 11;//find out what module number is for digital input
   // public static final int MODULE_NUMBER = 1;//This is for the new crio only
    
    private int[] digitalInputPorts;
    private Attack3Joystick joystick1;
    private Driving driving; 
    private Attack3Joystick joystick2;
    private Claw claw;
    private TriggerListener triggerL;
    private MovementListener movementL;
    private int[] jagPorts;
    private double z = 0;
    private ClawSwitchListener clawLimitL;
    private ElevationListener clawElevationL;
    private ClawButtonListener clawButtonL;
    private DigitalInputHandler digitalInputHandler;
    private ReloadSwitchListener reloadSwitchL;
    private ElevationSwitchListener elevationSwitchL;
    private BallDetectListener ballDetectL;
    private BallCaughtListener ballCaughtL;
    private Pressure pressure;
    private PressureLimitListener pressureLimitL;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
        //initialize everything bit
        joystick1 = new Attack3Joystick(1);
        joystick2 = new Attack3Joystick(2);
        digitalInputPorts = new int[] {PRESSURE_LIMIT_PORT, BALL_CAUGHT_PORT,CLOSECLAW_LIMIT_SWITCH, OPENCLAW_LIMIT_SWITCH, RELOAD_LIMIT_SWITCH, DETECT_BALL_PORT, ELEVATION_SWITCH_LISTENER_PORT};
        digitalInputHandler = new DigitalInputHandler(digitalInputPorts);
        
        // driving
        driving = new Driving();
        triggerL = new TriggerListener(driving);
        movementL = new MovementListener(driving);
 
        // claw
        claw = new Claw();
        pressure = new Pressure();
        pressureLimitL = new PressureLimitListener(pressure);
        clawElevationL = new ElevationListener(driving);
        clawButtonL = new ClawButtonListener(claw,pressure);
        clawLimitL = new ClawSwitchListener(claw, CLOSECLAW_LIMIT_SWITCH, OPENCLAW_LIMIT_SWITCH);
        reloadSwitchL = new ReloadSwitchListener(claw,RELOAD_LIMIT_SWITCH);
        elevationSwitchL = new ElevationSwitchListener(claw,ELEVATION_SWITCH_LISTENER_PORT);
        ballDetectL = new BallDetectListener(claw, DETECT_BALL_PORT);
        ballCaughtL = new BallCaughtListener(claw, BALL_CAUGHT_PORT);
        claw.setClawElevationState(Claw.ElevationState.GOING_UP);
        
        //joysticks
        joystick1.addJoystickListener(movementL);
        joystick1.addButtonListener(Button.TRIGGER, triggerL);
        joystick2.addJoystickListener(clawElevationL); 
        joystick2.addButtonListener(Button.TRIGGER, clawButtonL);
        joystick2.addButtonListener(Button.TRIGGER,  clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_2, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_3, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_6, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_4, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_5, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_6, clawButtonL);//should this listent to fireL? Should it restart the autorelease?
        joystick2.addButtonListener(Button.BUTTON_7, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_8, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_9, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_10, clawButtonL);
        joystick2.addButtonListener(Button.BUTTON_11, clawButtonL);
        
        //limit switches
        digitalInputHandler.addSwitchListener(PRESSURE_LIMIT_PORT, pressureLimitL);
        digitalInputHandler.addSwitchListener(OPENCLAW_LIMIT_SWITCH, clawLimitL);
        digitalInputHandler.addSwitchListener(CLOSECLAW_LIMIT_SWITCH, clawLimitL);
        digitalInputHandler.addSwitchListener(ELEVATION_SWITCH_LISTENER_PORT, elevationSwitchL);
        digitalInputHandler.addSwitchListener(DETECT_BALL_PORT, ballDetectL);
        digitalInputHandler.addSwitchListener(BALL_CAUGHT_PORT, ballCaughtL);
    }

    /**
     * This function is called once when autonomous mode is first started
     */
    public void autonomousInit() {
        driving.reCalcVelocityA(.8);
        Timer.delay(2.5);
        driving.stop();
    }

    /**
     * This function is called once when operator control is first started
     */
    public void teleopInit() {
        System.out.println("The robot has been enabled.");
    }
    
    /**
     * This function is called once when test mode is first started
     */
    public void testInit() {
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        digitalInputHandler.listen();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        joystick1.listen();
        joystick2.listen();
        digitalInputHandler.listen();
        if(System.currentTimeMillis() - BALL_DETECT_TIME >= 2000 && BALL_DETECT_TIME != 0)
        {
            claw.stopClaw();
            BALL_DETECT_TIME = 0;
            claw.setClawState(Claw.State.CLOSED);
            Robot.log(DEBUG_CLAW, "The timer has stopped the claw");
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
    
    public static void log(boolean flag, String message) {
        if (flag) {
            System.out.println(message);
        }
    }

}
    
   

