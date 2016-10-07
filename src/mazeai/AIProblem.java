/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.ArrayList;

/**
 * An AI PROBLEM according to page 122 of the book "AI" 2nd edition, 
 * by Stuart Russell & Peter Norvig, comprises:
 *  - the state space
 *  - the initial state
 *  - a collection of actions
 *  - a target-reached control (or a collection of target states)
 *  - a path cost function
 *  - a collection of solutions (a list of states from start to a target)
 * @author tioa
 */
public class AIProblem {
    // --------------- DATA MEMBERS --------------------------------------
    private Maze maze;  // state space
    private MazeState initialState;    // initial state
    private ArrayList<MazeState> goalStates; // goal states
    private ArrayList<Action> actions; // action list
    
    // ------------ DATA ACCESSORS --------------------------------------- 
    public MazeState getInitialState() {
        return initialState;
    }

    public void setInitialState(MazeState initialState) {
        this.initialState = initialState;
    }

    public ArrayList<MazeState> getGoalStates() {
        return goalStates;
    }

    public void setGoalStates(ArrayList<MazeState> goalStates) {
        this.goalStates = goalStates;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public Maze getMaze() {
        return maze;
    }

    // ------------ DATA ACCESSORS ---------------------------------------
    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- 
     * Constructs an AI problem based on a Maze, and uses the specified
     * initial state, goal states and valid action list */
    public AIProblem(Maze maze, MazeState initialState, 
            ArrayList<MazeState> goalStates, ArrayList<Action> actions) {
        this.maze = maze;
        this.initialState = initialState;
        this.goalStates = goalStates;
        this.actions = actions;
    }

    /* Constructor 2 ------- 
     * Calls Constructor 1 but uses the maze's initial state, goal states
     * and default Anticlockwise action list (up, left, down, right) */
    public AIProblem(Maze maze) {
        this(maze, maze.getInitialState(), 
            maze.getGoalStates(), Action.AntiClockwiseActionList());
    }

    // ------------------ METHODS ------------------------------------
    /* check if this is the initial state
     * Retrieves the initialState from the AIProblem and 
     * compares it the state */
    public boolean isInitialState(MazeState state) {
        return this.initialState.equals(state);
    }

    /* check if this is the goal state
        Retrieves the TargetState from the the AIProblem and 
        checks if it exists in the goal states list */
    public boolean isGoalState(MazeState state) {
        return this.goalStates.contains(state);
    }
 
}