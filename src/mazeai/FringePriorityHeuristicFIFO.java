/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author tioa
 */
public class FringePriorityHeuristicFIFO extends Fringe {
    private final ComparatorSearchNodeByTargetsHeuristic comparator;
    
    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- */    
    public FringePriorityHeuristicFIFO(CompareSearchNodeBy compareBy) {
        this.comparator = new ComparatorSearchNodeByTargetsHeuristic(compareBy);
    }

    // ------------------ METHODS ------------------------------------
    @Override
    public void INSERT(SearchNode newNode) {
        this.deque.addLast(newNode);
        Collections.sort((LinkedList<SearchNode>)this.deque, this.comparator);
    }
    
}
