/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.LinkedList;

/**
 *
 * @author tioa
 */
public class SearchBFSAlgorithm extends SearchAlgorithm {

    public SearchBFSAlgorithm(AIProblem problem) {
        super(problem, "BFS");
        this.fringe = new FringeFIFO();
    }
}
