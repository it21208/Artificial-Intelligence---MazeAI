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
public class FringeFIFO extends Fringe {

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- */    
    public FringeFIFO() {
        super();
    }

    // ------------------ METHODS ------------------------------------
    @Override
    public void INSERT(SearchNode newNode) {
        this.deque.addLast(newNode);
    }
}