/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mazeai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author tioa
 */
public class MazeState {
    // --------------- DATA MEMBERS --------------------------------------
    private int g;  // state transition cost
    private String type; // state type (A, B, S, G, P)
    private Maze maze;  // the maze where this state belongs to
    private int row;    // row of maze where it is
    private int col;    // col of maze where it is

    // ------------ DATA ACCESSORS ---------------------------------------
    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    // heuristic estimation of cost to reach target
    public float getHeuristicValue(MazeState targetState) {
        return this.ManhattanDistance(targetState, this);
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) throws Exception {
        /* check if type is valid */
        boolean validType = type.equals("S") || // Start state
                            type.equals("G") || // Terminal state
                            type.equals("A") || // Αμφιθέατρο Α
                            type.equals("B") || // Αμφιθέατρο Β
                            type.equals("P");   // Plain state
        if (validType)
            this.type = type;
        else
            throw new Exception("<MazeNode>: Invalid state type " + type);
    }

    public Maze getMaze() {
        return maze;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    
    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 -------
     *  Creates a state based on a token read from a text file.
        This is were we implement the logic described by the Exercise.
        Does not initialize this.h (heuristic value) */
    public MazeState(String token, Maze maze, int row, int col) throws Exception {
        /* if token is [S, G, A, B] => g = 1, type = token 
        else g = int(token), type = P
        */
        if (token.equals("S") || token.equals("G") ||
            token.equals("A") || token.equals("B")) {
            this.g = 1;
            this.setType(token);
        } else {
            g = Integer.parseInt(token);
            this.setType("P");
        }
        this.maze = maze;
        this.row = row;
        this.col = col;
    }
    
    // ------------------ METHODS ------------------------------------
    /* This function calculates the Manhattan distance between two
     * states that we provide */
    private float ManhattanDistance(MazeState A, MazeState B) {
        return (Math.abs(A.getRow() - B.getRow())
                + Math.abs(A.getCol() - B.getCol()));
    }
        
    public boolean isWallState() {
        return (this.g == -1);
    }

    /* Get next State based on action if it is valid
       Maze position and it is not a wall */
    public MazeState NextState(Action action) {
        // get this State's row and col
        int cRow = this.row;
        int cCol = this.col;
        // based on action change row and col
        switch (action) {
            case Up:    cRow--;
                        break;
            case Left:  cCol--;
                        break;
            case Down:  cRow++;
                        break;
            case Right: cCol++;
                        break;
        }
        // check if row and col are valid in maze
        if (this.maze.isRowColInRange(cRow, cCol)) {
            MazeState next = this.maze.getState(cRow, cCol);
            // check if next state is a wall
            if (next.isWallState())
                return null;
            else
                return next;
        } else
            return null;
    }
    
    /* The maximum list of Actions is defined by the AI Problem {Up, Left, Down, Right}  */
    public ArrayList<Action> getListOfValidActions(ArrayList<Action> problemActionList) {
        ArrayList<Action> lstValidAction = new ArrayList<>(); 
        ListIterator<Action> lstit = problemActionList.listIterator();
        MazeState next;
        Action act;
        while (lstit.hasNext()) {
            act = lstit.next();
            // get next state based on action
            next = this.NextState(act);
            // if next is not null add it to the list of actions
            if (next != null)
                lstValidAction.add(act);
        }
        return lstValidAction;
    }   

    @Override
    public String toString() {
        return String.format("<(%d, %d), %s, %d>", 
                this.row, this.col, this.type, this.g); 
    }
}