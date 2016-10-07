/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

/**
 *
 * @author tioa
 */
public enum CompareSearchNodeBy {
    Heuristic,
    Evaluation;
    
    /* Returns a String with a description that corresponds to
     * CompareSearchNodeBy enum
     *
     *  CompareSearchNodeBy --> String
     */
    @Override
    public String toString() {
        String s = "";
        switch (this) {
         case Heuristic:
              s = "Heuristic";
              break;
         case Evaluation:
              s = "Evaluation (Path cost + Heuristic value";
              break;
        }
        return s;
    }  
}
