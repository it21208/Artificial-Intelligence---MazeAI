/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The generic tree search algorithm presented in pg106 in the book 
 * "AI" 2nd edition, by Stuart Russell & Peter Norvig, has 2 main
 * data structures that it deals with, the PROBLEM and the FRINGE. We define
 * these two as data members. The Fringe class deals with the NODE, 
 * ACTION, etc.
 * 
 * @author tioa
 */
public abstract class SearchAlgorithm {
    // --------------- DATA MEMBERS --------------------------------------
    private final String type;
    protected AIProblem problem;
    protected Fringe fringe;
    protected int solutionTotalPathCost;
    protected ArrayList<Action> solutionActionList = null;
    protected ArrayList<MazeState> solutionMazeStatesList = null;

    public String getType() {
        return type;
    }

    public AIProblem getProblem() {
        return problem;
    }

    public Fringe getFringe() {
        return fringe;
    }

    public int getSolutionTotalPathCost() {
        return solutionTotalPathCost;
    }

    public ArrayList<Action> getSolutionActionList() {
        return solutionActionList;
    }

    public ArrayList<MazeState> getSolutionStatesList() {
        return solutionMazeStatesList;
    }
    
    protected void FillSolutionNodesList() throws Exception {
        if (this.solutionActionList.isEmpty())
            return;
        problem.setInitialState(problem.getMaze().getInitialState());
        this.solutionMazeStatesList = new ArrayList<>();
        MazeState ms = null;
        Iterator<Action> litact = this.solutionActionList.iterator();
        Action action;
        while (litact.hasNext()) {
            action = litact.next();
            if (action.equals(Action.Start))
                ms = problem.getInitialState();
            else
                ms = ms.NextState(action);
            this.solutionMazeStatesList.add(ms);
        }
    }
    
    // ------------ DATA ACCESSORS ---------------------------------------

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- 
        Assign Maze only. The Fringe will be specialized for each
        type of Search Algorithm */
    public SearchAlgorithm(AIProblem problem, String type) {
        this.problem = problem;
        this.type = type;
    }
    
    /* Corresponds to TREE-SEARCH() method required in pg106 */
    protected SearchNode TREE_SEARCH(SearchNode initialNode) throws Exception {
        SearchNode node = null;
        boolean found;
        this.fringe.Reset();
        this.fringe.INSERT(initialNode);
        do {
            // display fringe
            // System.out.println(this.fringe.toString());
            // check if fringe is empty and signal failure
            if (this.fringe.EMPTY()) break;
            // remove and get first search node from fringe
            node = this.fringe.REMOVE_FIRST();
            // if this is the target node then return success
            found = problem.isGoalState(node.getState());
            if (!found)
                this.fringe.INSERT_ALL(node.EXPAND());
        } while (!found);
        // return the goal node found
        return node;
    }
    
    public void SOLVE_TWO_PROBLEMS() throws Exception {
        // initialize and solve PROBLEM 1
        problem.setInitialState(problem.getMaze().getInitialState());
        problem.setGoalStates(problem.getMaze().getKioskStates());
        SearchNode initialNode = new SearchNode(problem, problem.getInitialState(),
                    null, Action.Start);
        SearchNode goalNode1 = TREE_SEARCH(initialNode);
        if (goalNode1 == null) return;
        // initialize and solve PROBLEM 2
        problem.setInitialState(goalNode1.getState());
        problem.setGoalStates(problem.getMaze().getGoalStates());
        SearchNode goalNode2 = TREE_SEARCH(goalNode1);
        if (goalNode2 == null) return;
        // get list of actions for solution
        solutionTotalPathCost = goalNode2.getPathCost();
        solutionActionList = SOLUTION(goalNode2);
        FillSolutionNodesList();
    }
    
    public ArrayList<Action> SOLUTION(SearchNode targetNode) {
        SearchNode n = targetNode;
        ArrayList<Action> lla = new ArrayList<>();
        lla.add(0, n.getParentAction());
        while (n.getParentNode() != null) {
            n = n.getParentNode();
            lla.add(0, n.getParentAction());
        }  
        return lla;
    }

    // returns the solution action list with the cost and depth
    @Override
    public String toString() {
        return String.format("%s: %s\nTotal cost: %d", this.type,
                this.solutionActionList.toString(), 
                this.getSolutionTotalPathCost());
    }
}
