/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    // --------------- DATA MEMBERS --------------------------------------
    private final MazeState[][] statesMat;  /* state matrix */
    private final int rows; // number of rows
    private final int columns; // number of columns
    private MazeState initialState;    // initial state
    private ArrayList<MazeState> goalStates;
    private ArrayList<MazeState> kioskStates;

    // ------------ DATA ACCESSORS --------------------------------------- 
    public MazeState getState(int row, int col) {
        return statesMat[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public MazeState getInitialState() {
        return this.initialState;
    }

    public ArrayList<MazeState> getGoalStates() {
        return goalStates;
    }

    public void setGoalStates(ArrayList<MazeState> goalStates) {
        this.goalStates = goalStates;
    }

    public ArrayList<MazeState> getKioskStates() {
        return kioskStates;
    }

    public void setKioskStates(ArrayList<MazeState> kioskStates) {
        this.kioskStates = kioskStates;
    }

    /* Accessor designed to automatically create a MazeState
     and assign it to statesMat[row, col].
     It is used by the function which reads the Maze file in order
     to create the maze states and fill in the maze matrix */
    public void setState(int row, int col, String token) throws Exception {
        this.statesMat[row][col] = new MazeState(token, this, row, col);
        if (this.statesMat[row][col].getType().equals("S"))
            this.initialState = this.statesMat[row][col];
        else if (this.statesMat[row][col].getType().equals("G")) 
            this.goalStates.add(this.statesMat[row][col]);
        else if ((this.statesMat[row][col].getType().equals("A")) ||
                 (this.statesMat[row][col].getType().equals("B")))
            this.kioskStates.add(this.statesMat[row][col]);     
    }

    /* maze is valid only if there is a starting state, there exists one
     * target state and two kiosks */
    public boolean isValid() {
        return ((initialState != null) && 
                (goalStates.size() == 1) && 
                (kioskStates.size() == 2));
    }
    
    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- 
     Create maze directly from text file */

    public Maze(String fileName) throws FileNotFoundException, IOException, Exception {
        String readLine, // line read from the file
               readToken;   // element read from line
        int i, // counter of rows read
            j; // counter of columns read for a specific row
        
        // initialize the two arraylists
        kioskStates = new ArrayList<>();
        goalStates = new ArrayList<>();
        
        // read file
        BufferedReader in = null;
        Scanner sc = null;
        try {
            // open the file with a buffered reader
            in = new BufferedReader(new FileReader(fileName));
            // read the 1st line of the file where the number of rows
            //   and columns of the Maze are declared
            readLine = in.readLine();
            // associate a scanner with the 1st line just read
            sc = new Scanner(readLine);
            // read rows and columns from 1st line - initialize maze matrix
            this.rows = sc.nextInt();
            this.columns = sc.nextInt();
            this.statesMat = new MazeState[this.rows][this.columns];
            // inform the user about what is going on
            // System.out.println(String.format("Reading Maze %d x %d ...", this.rows, this.columns));
            // inform the user about what is going on
            // System.out.println(String.format("created Maze object %d x %d.\n\nMaze", this.rows, this.columns));
            // read maze matrix (rows >= 2)
            i = 0;
            while ((readLine = in.readLine()) != null) {
                // associate a scanner with the line just read
                sc = new Scanner(readLine);
                i++;    // increace the counter of the rows read
                // check if there are more maze rows read than the declared rows
                if (i > this.rows) {
                    throw new Exception("<Maze>: Wrong number (more) of rows in maze");
                }
                // read row tokens-values
                j = 0;
                while (sc.hasNext()) {
                    // read next token after having checked that there is one!
                    readToken = sc.next();
                    j++;    // increace the counter of the columns read for this row
                    // check if there are more columns in current row than declared columns
                    if (j > this.columns) {
                        throw new Exception(String.format("<Maze>: Wrong number of columns (more) in row %d of maze", i));
                    }
                    // print the token read, add a TAB at the end
                    // System.out.print(readToken + "\t");
                    // create Maze state[i-1][j-1] with token
                    //   REMEMBER i, j are NOT 0-based as the Maze.statesMat matrix
                    this.setState(i - 1, j - 1, readToken);
                }
                // print a NEWLINE but first make a BACKSPACE to delete the last TAB printed
                // System.out.print("\b\n");
                // check if there are less columns in current row than declared columns in 1st line
                if (j < this.columns) {
                    throw new Exception(String.format("<Maze>: Wrong number of columns (less) in row %d of maze", i));
                }
            }
            // check if there are less maze rows than declared rows in 1st line
            if (i < this.rows) {
                throw new Exception("<Maze>: Wrong number (less) of rows in maze");
            }
        } finally {
            // close any NOT NULL object
            if (in != null) {
                in.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    // ------------------ METHODS ------------------------------------
    /* This function confirms whether the point with coordinates
     * (row, col) is within the valid coordinates of the Maze */
    public boolean isRowColInRange(int row, int col) {
        return ((row >= 0) && (row < rows))
                && ((col >= 0) && (col < columns));
    }
}
