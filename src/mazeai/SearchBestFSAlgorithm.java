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
public class SearchBestFSAlgorithm extends SearchAlgorithm {
    
    public SearchBestFSAlgorithm(AIProblem problem) {
        super(problem, "BestFS");
        this.fringe = new FringePriorityHeuristicFIFO(CompareSearchNodeBy.Heuristic);
    }
}
