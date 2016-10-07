/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

/**
 *
 * @author tioa
 */
public class SearchDFSAlgorithm extends SearchAlgorithm {

    public SearchDFSAlgorithm(AIProblem problem) {
        super(problem, "DFS");
        this.fringe = new FringeLIFO();
   }
}
