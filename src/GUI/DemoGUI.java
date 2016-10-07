/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.io.IOException;

/**
 *  ΕΚΚΙΝΗΣΗ ΠΡΟΓΡΑΜΜΑΤΟΣ
 *  Δημιουργία φόρμας με ταυτόχρονο διάβασμα του Maze από το εξωτερικό αρχείο
 *  από τον constructor της φόρμας.
 */
public class DemoGUI {
        public static void main(String[] args) throws IOException, Exception {
            frmMaze fm = new frmMaze("mazeai.txt");
            fm.setVisible(true);
        }
}
