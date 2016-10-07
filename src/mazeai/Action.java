/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.ArrayList;

/**
 *
 * @author tioa
 */
public enum Action {
    Start,
    Up,
    Left,
    Down,
    Right;
    
    // ------------------ METHODS ------------------------------------
    public Action Opposite() {
        Action ret = Start;
        switch (this) {
            case Up: ret = Down;
                    break;
            case Down: ret = Up;
                    break;
            case Left: ret = Right;
                    break;
            case Right: ret = Left;
                    break;
        };
        return ret;
    } 
    
    public static ArrayList<Action> AntiClockwiseActionList() {
        ArrayList<Action> arrlst = new ArrayList<>();
        arrlst.add(Up);
        arrlst.add(Left);
        arrlst.add(Down);
        arrlst.add(Right);
        return arrlst;
    }
    
    public static ArrayList<Action> ClockwiseActionList() {
        ArrayList<Action> arrlst = new ArrayList<>();
        arrlst.add(Up);
        arrlst.add(Right);
        arrlst.add(Down);
        arrlst.add(Left);
        return arrlst;
    }
    
    /* Returns a String with a description that corresponds to
     * Action enum
     *
     *  Action --> String
     */
    @Override
    public String toString() {
        String s = "";
        switch (this) {
         case Start:
              s = "Start";
              break;
         case Up:
              s = "Up";
              break;
         case Left:
              s = "Left";
              break;
         case Down:
              s = "Down";
              break;
         case Right:
              s = "Right";
              break;
        }
        return s;
    }    
}