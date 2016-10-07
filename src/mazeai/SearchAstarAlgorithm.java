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
public class SearchAstarAlgorithm extends SearchAlgorithm {
    public SearchAstarAlgorithm(AIProblem problem) {
        super(problem, "A*");
        this.fringe = new FringePriorityHeuristicFIFO(CompareSearchNodeBy.Evaluation);
    }
}
