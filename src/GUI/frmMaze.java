/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mazeai.AIProblem;
import mazeai.Action;
import mazeai.Maze;
import mazeai.SearchAlgorithm;
import mazeai.SearchAstarAlgorithm;
import mazeai.SearchBFSAlgorithm;
import mazeai.SearchBestFSAlgorithm;
import mazeai.SearchDFSAlgorithm;
import mazeai.SearchIDSAlgorithm;

/**
 *
 * @author tioa
 */
public class frmMaze extends javax.swing.JFrame implements ActionListener {
    // --------------- DATA MEMBERS --------------------------------------
    private Maze maze;
    private AIProblem problem;
    private SearchAlgorithm searchAlgorithm;
    private JTable tableMaze;
    private CustomTableRenderer ctr;
    private JPanel pnlFrame, pnlButtons, pnlTable;
    private JButton btnBFS, btnDFS, btnIDS, btnBestFS, btnAstarFS;
    private JComboBox cmbActionSequence;
    private JScrollPane scrlpnTableMaze, scrlpnTxtAreaLog;
    private JTextArea txtareaLog;
    private Timer timer;

    public JTable getTableMaze() {
        return tableMaze;
    }
    
    public JTextArea getTxtareaLog() {
        return txtareaLog;
    }

    public SearchAlgorithm getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public Maze getMaze() {
        return maze;
    }

    // ------------ CONSTRUCTORS -----------------------------------------
    /* Constructor 1 -------
     * Κάνει μόνο αρχικοποιήση της φόρμας
     */
    public frmMaze() {
        initComponents();
    }

    /* Constructor 2 -------
     * Μετά την αρχικοποίηση της φόρμας μέσω του base constructor, διαβάζει
     * το Maze από το εξωτερικό αρχείο και ορίζει το πρόβλημα προς επίλυση που
     * σχετίζεται με το Maze.
     */
    public frmMaze(String  mazeFile) throws Exception {
        // αρχικοποίηση φόρμας μέσω του base constructor
        this();
        // δημιουργία του Maze από το εξωτερικό αρχείο
        this.maze = new Maze(mazeFile);
        // δημιουργία του προβλήματος προς επίλυση που σχετίζεται με το Maze
        problem = new AIProblem(maze);
        // Δημιουργία του JTable 
        // Προετοιμασία του TableModel για το JTable
        //  τα δεδομένα του TableModel
        Object[][] data = new Object[this.maze.getRows()][this.maze.getColumns()];
        int i, j;
        for(i=0; i<this.maze.getRows(); i++)
            for(j=0; j<this.maze.getColumns(); j++) {
                data[i][j] = this.maze.getState(i, j).getG();
            }
        //  οι επικεφαλίδες του TableModel είναι τα default - δεν τις ορίζω
        String[] columnNames =  new String [this.maze.getColumns()];
        // Δημιουργία του TableModel που θα χρησιμοποιηθεί από το JTable
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        // Δημιουργία του JTable
        this.tableMaze = new JTable(dtm);
        // Καθορισμός ύψους γραμμών για το JTable
        this.tableMaze.setRowHeight(30);
        // Καθορισμός πλάτους στηλών για το JTable (υποχρεωτικά κάνω χρήση του ColumnModel)
        TableColumnModel tcm = this.tableMaze.getColumnModel();
        for (i=0; i<tcm.getColumnCount(); i++)
            tcm.getColumn(i).setPreferredWidth(30);
        // Να μη γίνεται resize το JTable
        this.tableMaze.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // Καθορισμός χρώματος επικεφαλίδας JTable
        this.tableMaze.getTableHeader().setBackground(Color.MAGENTA);
        this.tableMaze.getTableHeader().setForeground(Color.WHITE);
        // Καθορισμός βασικού font για το JTable
        this.tableMaze.setFont(new Font("Courier New", Font.PLAIN, 12));
        /* Δημιουργία Custom Renderer για τον χρωματισμό των κελιών 
         * και τον τονισμό των καταστάσεων που ανήκουν στη λύση.
         * Ο constructor δέχεται ως παράμετρο τη φόρμα στην οποία ανήκει
         * και o Custom Renderer προκειμένου να έχει πρόσβαση σε άλλα αντικείμενα
         */
        this.ctr = new CustomTableRenderer(this);
        // Ορισμός του Custom Renderer ως default renderer του JTable
        this.tableMaze.setDefaultRenderer(Object.class, ctr);
        // Δημιουργία του scroll pane για να μπει το JTable
        this.scrlpnTableMaze = new JScrollPane();
        // Σύνδεση του JTable με το scroll pane
        this.scrlpnTableMaze.setViewportView(this.tableMaze);
        this.tableMaze.setPreferredScrollableViewportSize(this.tableMaze.getPreferredSize());
        /* Δημιουργία του JPanel όπου θα τοποθετηθεί το scroll pane του JTable
         * με BorderLayout */
        this.pnlTable = new JPanel();
        this.pnlTable.setLayout(new BorderLayout());
        this.pnlTable.setBorder(new BevelBorder(BevelBorder.RAISED));
        // Δημιουργία JTextArea για καταγραφή σημαντικών γεγονότων
        this.txtareaLog = new JTextArea("");
        this.txtareaLog.setOpaque(true);
        this.txtareaLog.setBackground(Color.WHITE);
        this.txtareaLog.setForeground(Color.BLACK);
        this.txtareaLog.setFont(new Font("Arial", 1, 10));
        // Δημιουργία του scroll pane όπου θα μπει το txtareaLog
        this.scrlpnTxtAreaLog = new JScrollPane(this.txtareaLog,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.scrlpnTxtAreaLog.setPreferredSize(new Dimension(this.tableMaze.getWidth(), 100));
        // Δημιουργία JPanel για τα button
        this.pnlButtons = new JPanel();
        this.pnlButtons.setLayout(new FlowLayout());
        this.pnlButtons.setBorder(new BevelBorder(BevelBorder.RAISED));
        // Δημιουργία του button BFS, καθορισμός του action και του listener
        this.btnBFS = new JButton("BFS");
        this.btnBFS.setPreferredSize(new Dimension(90, 25));
        this.btnBFS.setActionCommand("BFS");
        this.btnBFS.addActionListener(this);
        // Δημιουργία του button DFS, καθορισμός του action και του listener
        this.btnDFS = new JButton("DFS");
        this.btnDFS.setPreferredSize(new Dimension(90, 25));
        this.btnDFS.setActionCommand("DFS");
        this.btnDFS.addActionListener(this);
        // Δημιουργία του button IDS, καθορισμός του action και του listener
        this.btnIDS = new JButton("IDS");
        this.btnIDS.setPreferredSize(new Dimension(90, 25));
        this.btnIDS.setActionCommand("IDS");
        this.btnIDS.addActionListener(this);
        // Δημιουργία του button BestFS, καθορισμός του action και του listener
        this.btnBestFS = new JButton("BestFS");
        this.btnBestFS.setPreferredSize(new Dimension(90, 25));
        this.btnBestFS.setActionCommand("BestFS");
        this.btnBestFS.addActionListener(this);
        // Δημιουργία του button A*, καθορισμός του action και του listener
        this.btnAstarFS = new JButton("A*");
        this.btnAstarFS.setPreferredSize(new Dimension(90, 25));
        this.btnAstarFS.setActionCommand("AStar");
        this.btnAstarFS.addActionListener(this);
        // Δημιουργία combo box για το Action Sequence
        String[] strActionSequences = {"Anticlockwise", "Clockwise"};
        cmbActionSequence = new JComboBox(strActionSequences);
        cmbActionSequence.setSelectedIndex(0);
        cmbActionSequence.setActionCommand("ActionSequenceCOMBO");
        cmbActionSequence.addActionListener(this);
        // Πρόσθεση των button στο pnlButtons
        this.pnlButtons.add(this.btnBFS);
        this.pnlButtons.add(this.btnDFS);
        this.pnlButtons.add(this.btnIDS);
        this.pnlButtons.add(this.btnBestFS);
        this.pnlButtons.add(this.btnAstarFS);
        pnlButtons.add(cmbActionSequence);
        // Πρόσθεση των component στο pnlTable
        this.pnlTable.add(this.scrlpnTableMaze, BorderLayout.NORTH);
        this.pnlTable.add(this.scrlpnTxtAreaLog, BorderLayout.CENTER);
        // Δημιουργία JPanel για τη φόρμα
        this.pnlFrame = new JPanel();
        this.pnlFrame.setLayout(new BorderLayout());
        this.pnlFrame.setBorder(new BevelBorder(BevelBorder.LOWERED));
        // Πρόσθεση των panel με τα button και το JTable στο pnlFrame
        this.pnlFrame.add(this.pnlButtons, BorderLayout.NORTH);
        this.pnlFrame.add(this.pnlTable, BorderLayout.CENTER);
        // Ορισμός του pnlFrame ως content pane της φόρμας JFrame
        this.setContentPane(this.pnlFrame);
        this.pack();
        // Τοποθέτηση του JFrame αριστερά και πάνω
        this.setLocation(5, 5);
        // Δημιουργία timer με event ανά 1 δευτερόλεπτο = 1000msec
        timer = new Timer(1000, this);
        timer.setActionCommand("RepaintJTableTIMER");
        // Εκκίνηση του timer
        timer.start();
    }

    // ------------------ METHODS ------------------------------------

    /* Χειρισμός των event από τα 5 JButton, το 1 JComboBox και
     * τον timer με βάση το action command που έχει δηλώσει το κάθε component */
    public void actionPerformed(ActionEvent e) {
        if ("BFS".equals(e.getActionCommand())) {
            searchAlgorithm = new SearchBFSAlgorithm(problem);
            try {
                searchAlgorithm.SOLVE_TWO_PROBLEMS();              
                this.LogToGUI(searchAlgorithm.toString());
            } catch (Exception ex) {
                Logger.getLogger(frmMaze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("DFS".equals(e.getActionCommand())) {
            searchAlgorithm = new SearchDFSAlgorithm(problem);
            try {
                searchAlgorithm.SOLVE_TWO_PROBLEMS();              
                this.LogToGUI(searchAlgorithm.toString());
            } catch (Exception ex) {
                Logger.getLogger(frmMaze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("BestFS".equals(e.getActionCommand())) {
            searchAlgorithm = new SearchBestFSAlgorithm(problem);
            try {
                searchAlgorithm.SOLVE_TWO_PROBLEMS();              
                this.LogToGUI(searchAlgorithm.toString());
            } catch (Exception ex) {
                Logger.getLogger(frmMaze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("IDS".equals(e.getActionCommand())) {
            searchAlgorithm = new SearchIDSAlgorithm(problem);
            try {
                searchAlgorithm.SOLVE_TWO_PROBLEMS();              
                this.LogToGUI(searchAlgorithm.toString());
            } catch (Exception ex) {
                Logger.getLogger(frmMaze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("AStar".equals(e.getActionCommand())) {
            searchAlgorithm = new SearchAstarAlgorithm(problem);
            try {
                searchAlgorithm.SOLVE_TWO_PROBLEMS();              
                this.LogToGUI(searchAlgorithm.toString());
            } catch (Exception ex) {
                Logger.getLogger(frmMaze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if ("ActionSequenceCOMBO".equals((e.getActionCommand()))) {
            String actionSequence = cmbActionSequence.getSelectedItem().toString();
            if (actionSequence.equals("Clockwise")) {
                problem.setActions(Action.ClockwiseActionList());
            } else {
                problem.setActions(Action.AntiClockwiseActionList());
            }
        } else if ("RepaintJTableTIMER".equals((e.getActionCommand())))  {
            tableMaze.repaint();
        } 
    }
    
    // Προσθήκη μηνύματος με αλλαγή γραμμής στο JTextArea
    public void LogToGUI(String msg) {
        this.txtareaLog.append(msg + "\n");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ΛΑΒΥΡΙΝΘΟΣ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
