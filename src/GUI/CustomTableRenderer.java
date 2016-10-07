/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import mazeai.Maze;
import mazeai.MazeState;

/**
 * Δημιουργεί την διαφορετική χρωματική απεικόνιση των κελιών-καταστάσεων για
 * να ξεχωρίζουν ο τοίχος, τα κυλικεία, ο αρχικός και τελικός σταθμός.
 * Επιπλέον μέσω της εναλλαγής του font size και font style αναδεικνύονται
 * τα κελιά-καταστάσεις που είναι μέρος της λύσης κάθε φορά.
 */
public class CustomTableRenderer extends DefaultTableCellRenderer {
    // --------------- DATA MEMBERS --------------------------------------
    private frmMaze formMaze;
    private Maze maze;
    
    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 ------- */
    public CustomTableRenderer(frmMaze formMaze) {
        this.formMaze = formMaze;
        this.maze = formMaze.getMaze();
        // Κεντράρισμα όλων των τιμών στα κελιά του JTable
        this.setHorizontalAlignment(JLabel.CENTER);

    }
    
    // ------------------ METHODS ------------------------------------
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, 
            boolean isSelected, boolean hasFocus, int row, int col) {
        Component c = super.getTableCellRendererComponent(jtable, o, isSelected, 
                hasFocus, row, col);
        // εύρεση της κατάστασης που αντιστοιχεί στο (row, col)
        MazeState ms = this.maze.getState(row, col);
        // εύρεση της λίστας καταστάσεων που αντιστοιχούν στην λύση με βάση τον αλγόριθμο
        ArrayList<MazeState> mslst;
        if (this.formMaze.getSearchAlgorithm() == null)
            mslst = null;
        else
            mslst = this.formMaze.getSearchAlgorithm().getSolutionStatesList();
        // χρωματισμός ανάλογα με τον τύπο του κελιού-κατάσταση και της τιμής g
        if (ms.getType().equals("P")) {
            if(ms.getG() == -1) { // wall block
                c.setBackground(Color.BLACK);
                c.setForeground(Color.BLACK);
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }
        } else if (ms.getType().equals("S")) {
            c.setBackground(Color.RED);
            c.setForeground(Color.BLACK);
        } else if (ms.getType().equals("G")) {
            c.setBackground(Color.GREEN);
            c.setForeground(Color.BLACK);
        } else if (ms.getType().equals("A")) {
            c.setBackground(Color.BLUE);
            c.setForeground(Color.WHITE);
        } else if (ms.getType().equals("B")) {
            c.setBackground(Color.BLUE);
            c.setForeground(Color.WHITE);
        }
        // τονισμός κελιών-καταστάσεων που ανήκουν στη λύση αν υπάρχει
        if ((mslst != null) && (mslst.contains(ms)))
            c.setFont(new Font("Courier New", Font.BOLD, 20));
        else
            c.setFont(new Font("Courier New", Font.PLAIN, 12));
        return c;
    }
}
