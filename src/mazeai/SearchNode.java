/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author tioa
 */
public class SearchNode {
    // --------------- DATA MEMBERS --------------------------------------
    private final MazeState state;  // maze state that this search node points to
    private SearchNode parentNode; // parent search node that produced this one
    private Action parentAction; // parent search node action that produced this one
    private int pathCost; // path cost from Start up to this search node
    private int depth; // depth is number of expantions (generations)
    private final AIProblem problem; // AI problem this search node relates to

    // ------------ DATA ACCESSORS ---------------------------------------
    public MazeState getState() {
        return state;
    }

    public int getPathCost() {
        return pathCost;
    }

    public SearchNode getParentNode() {
        return parentNode;
    }

    /* Accessor recelculates dependent attributes of search node
       every time there is a change of parentNode and/or parentAction */
    public void setParentNode(SearchNode parentNode, Action parentAction) throws Exception {
        this.parentNode = parentNode;
        if (parentNode != null) {
            this.parentAction = parentAction;
            this.pathCost = parentNode.getPathCost()+this.state.getG();
            this.depth = parentNode.getDepth() + 1;
        } else {    // parentNode == null ==> this is the starting node!!
            if (parentAction != Action.Start)
                throw new Exception("<SearchNode>: Cannot have parentNode NULL and parentAction " + parentAction.toString());
            this.parentAction = Action.Start;
            this.pathCost = this.state.getG();
            this.depth = 0;
        }
    }

    public int getDepth() {
        return depth;
    }

    public Action getParentAction() {
        return parentAction;
    }

    public AIProblem getProblem() {
        return problem;
    }
    
    // check if this is the starting node
    public boolean isStartingNode() {
        return ((parentNode == null) && (problem.isInitialState(state)));
    }
    
    public float getHeuristicValue(MazeState targetState) {
        return state.getHeuristicValue(targetState);
    }
    
    public float getMinHeuristicValue() {
        // get an iterator for the AI Problem's goal states list
        Iterator<MazeState> itms = problem.getGoalStates().iterator();
        MazeState ms;
        /* set the initial value for min to an impossibly big number 
         * for the problem = maze.rows + maze.columns */
        float min = state.getMaze().getRows()+state.getMaze().getColumns();
        float tmp;
        // for each goal state of the problem check if the h(s) is minimum
        while (itms.hasNext()) {
            ms = itms.next();
            tmp = this.getHeuristicValue(ms);
            if (tmp < min)
                min = tmp;
        }
        return min;
    }
    
    public float getEvaluationValue() {
        return pathCost + this.getHeuristicValue(problem.getGoalStates().get(0));
    }
    
    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 -------
     *  Creates a search state, that relates to an AI problem and
     *  corresponds to a maze state that has derived from the 
     *  parentNode search state through parentAction. */
    public SearchNode(AIProblem problem, MazeState state, 
            SearchNode parentNode, Action parentAction) throws Exception {
        this.problem = problem;
        this.state = state;
        this.setParentNode(parentNode, parentAction);
    }
    
    // ------------------ METHODS ------------------------------------
    /* The list of Actions is defined in the AI problem but the valid
     * ones from this node is this list minus the opposite of
        this SearchNode's parent action in order to avoid trivial loops */
    public ArrayList<Action> getListOfValidActions() {
        ArrayList<Action> lstAction = this.getState().getListOfValidActions(problem.getActions());
        lstAction.remove(parentAction.Opposite());
        return lstAction;
    }
    
    public LinkedList<SearchNode> EXPAND() throws Exception {
        LinkedList<SearchNode> lstsn = new LinkedList<>();
        ArrayList<Action> lstAction = this.getListOfValidActions();
        Iterator<Action> lstActit = lstAction.iterator();
        Action act;
        while (lstActit.hasNext()) {
            act = lstActit.next();
            lstsn.add(new SearchNode(problem, state.NextState(act), this, act));
        }
        return lstsn;
    }

    @Override
    public String toString() {
        return String.format("{action=%s => %s, cost=%d, heur=%.2f depth=%d}", 
          parentAction.toString(), state, pathCost, 
          this.getMinHeuristicValue(), depth);
    }
}