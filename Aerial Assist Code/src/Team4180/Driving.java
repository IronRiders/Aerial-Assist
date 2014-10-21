/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Team4180;
import edu.wpi.first.wpilibj.Jaguar;

/**
 *
 * @author ros_kecooper
 * modified by Aaron Jacobson
 * modified on: 1/30/14
 */
public class Driving {
    private Jaguar jagleft;
    private Jaguar jagright;
    private double CurrentXValue; 
    private double CurrentYValue;
    private double CurrentLeftYValue;
    private double CurrentRightYValue;
    private boolean triggered;

    /**
     * The port assigned to the motors on the left side of the robot
     */
    public static final int LEFT_MOTOR_PORT = 1;

    /**
     * The port assigned to the motors on the right side of the robot
     */
    public static final int RIGHT_MOTOR_PORT = 2;

    /**
     * The constructor for the driving class. It assigns the initial values for the x and y
     * It also assigns the port numbers to the jaguar motor controllers
     */
    public Driving(){
        jagleft = new Jaguar(LEFT_MOTOR_PORT);//the port that the left Jaguar motor is plugged into(value = 1);
        jagright = new Jaguar(RIGHT_MOTOR_PORT);//the port that the right Jaguar motor is plugged into(value = 2);

        CurrentXValue = 0;
        CurrentYValue = 0;
        triggered = false;
    }
    /**
     * The value is reversed because the motors on the left side of the robot are flipped
     * @param speed Sets the speed of the motors on the left side of the robot
     */
    private void leftJaguars(double speed){
        jagleft.set(-speed);
    }
    /**
     * 
     * @param speed Sets the speed of the motors on the right side of the robot
     */
    private void rightJaguars(double speed) {
        jagright.set(speed);
    }

    /**
     * Updates the x value which is used for turning
     * @param x The new value which CurrentXValue will be set to
     */
    public void updateX(double x){
        CurrentXValue = x;
    }

    /**
     * Updates the CurrentYValue with the given number
     * @param y The value which CurrentYValue will be updated to
     */
    public void updateY(double y){
        CurrentYValue = y;
    }

    /**
     * Gives the speed to the motors on each side of the robot
     * The Math.min() and Math.max() make sure that the value is within the 
     * tolerable speeds for the motors
     */
    public void reCalcVelocity(){
      System.out.println("Giving the right motors these values: " + Math.max(Math.min(CurrentYValue+CurrentXValue, 1),-1));
      System.out.println("Giving the left motors these values: " + Math.max(Math.min(CurrentYValue-CurrentXValue, 1),-1));
      rightJaguars(Math.max(Math.min(CurrentYValue+CurrentXValue, 1),-1));
      leftJaguars(Math.max(Math.min(CurrentYValue-CurrentXValue, 1),-1));
      
    }

    /**
     * Sets the speed of the motors on each side of the robot to the given value
     * Is only used during autonomous
     * @param speed The speed which the motors on each side of the robot will be set to
     */
    public void reCalcVelocityA(double speed)
    {
        leftJaguars(speed);
        rightJaguars(speed);
    }

    /**
     * Give the motors on each side of the robot a value of 0, stopping the robot
     */
    public void stop(){
        leftJaguars(0);
        rightJaguars(0);
    }

    /**
     * If the trigger on the movement joystick is triggered, the robot will drive in a straight line
     * @param triggerPressed the boolean which triggered will be set to
     */
    public void driveStraight(boolean triggerPressed){ //triggerpressed comes from TriggerListener.
      triggered = triggerPressed;
      reCalcVelocity();
    }
}

