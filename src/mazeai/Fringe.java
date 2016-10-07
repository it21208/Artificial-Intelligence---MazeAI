/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * The Fringe is collection of search nodes that have been produced by the
 * tree search but have not been expanded yet.
 * According to the book "AI" 2nd edition, by Stuart Russell & Peter Norvig
 * pg105 the fringe should be implemented as a queue. By reviewing some of 
 * the search algorithms it is clear that this queue should be able to behave
 * both as a LIFO (stack) or as a FIFO (queue).
 * By carefully examining the Java tutorial on Collections 
 * http://docs.oracle.com/javase/tutorial/collections/interfaces/index.html
 * it is clear that the Deque interface is the one that suits us better.
 * Since Deque is only an interface we need to use one of the classes that
 * implement this interface. By examining the Java API Reference at
 * https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html
 * we chose the LinkedList which is explained in:
 * https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
 * @author tioa
 */
public abstract class Fringe {
    // --------------- DATA MEMBERS --------------------------------------
    Deque<SearchNode> deque;

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- */
    public Fringe() {
        this.Reset();
    }

    // ------------------ METHODS ------------------------------------
    public void Reset() {
        deque = new LinkedList<>();
    }
    
    /* Corresponds to EMPTY method required for Fringe by pg105 */
    public boolean EMPTY() {
        return this.deque.isEmpty();
    }
    
    /* Corresponds to FIRST method required for Fringe by pg105.
       Used peekFirst() instead of getFirst() to avoid dealing with
       the exception thrown when dequeu is EMPTY.
       Returns NULL if dequeu is EMPTY */
    public SearchNode FIRST() {
        return this.deque.peekFirst();
    }

    /* Corresponds to REMOVE-FIRST method required for Fringe by pg105.
       Used pollFirst() instead of removeFirst() to avoid dealing with
       the exception thrown when dequeu is EMPTY.
       Returns NULL if dequeu is EMPTY */
    public SearchNode REMOVE_FIRST() {
        return this.deque.pollFirst();
    }
    
    /* Corresponds to INSERT(element) method required for Fringe by pg105.
       This function will be defined by FRINGE_FIFO, etc */
    public abstract void INSERT(SearchNode newNode);
    
    /* Corresponds to INSERT-ALL(elementList) method required for Fringe by pg105.
       This function will be defined by FRINGE_FIFO, etc */
    public void INSERT_ALL(LinkedList<SearchNode> nodeList) {
        SearchNode sn;
        ListIterator<SearchNode> lstit = nodeList.listIterator();
        while (lstit.hasNext()) {
            sn = lstit.next();
            this.INSERT(sn);
        }        
    }

    public boolean Contains(MazeState state) {
        Iterator<SearchNode> snit = this.deque.iterator();
        SearchNode sn;
        boolean found = false;
        while (snit.hasNext() && !found) {
            sn = snit.next();
            if (sn.getState().equals(state))
                found = true;
        }
        return found;
    }
    
    @Override
    public String toString() {
        String fringeStr = "";
        Iterator<SearchNode> lstit = this.deque.iterator();
        while (lstit.hasNext())
            fringeStr = fringeStr + lstit.next().toString() + " ";
        return "[" + fringeStr + "]";
    }
    
    
}
