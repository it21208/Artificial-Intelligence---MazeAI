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
public class FringeLIFO extends Fringe {

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- */
    public FringeLIFO() {
        super();
    }
    
    // ------------------ METHODS ------------------------------------
    @Override
    public void INSERT(SearchNode newNode) {
        this.deque.addFirst(newNode);
    }    
}
