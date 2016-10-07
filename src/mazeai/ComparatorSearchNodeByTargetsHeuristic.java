/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.Comparator;

/**
 *
 * @author tioa
 */
public class ComparatorSearchNodeByTargetsHeuristic implements Comparator<SearchNode> {
    private final CompareSearchNodeBy compareBy;

    public ComparatorSearchNodeByTargetsHeuristic(CompareSearchNodeBy compareBy) {
        this.compareBy = compareBy;
    }    
    
    @Override
    public int compare(SearchNode o1, SearchNode o2) {
        float h1, h2;
        if (this.compareBy.equals(CompareSearchNodeBy.Heuristic)) {
            h1 = o1.getMinHeuristicValue();
            h2 = o2.getMinHeuristicValue();
        } else {
            h1 = o1.getEvaluationValue();
            h2 = o2.getEvaluationValue();            
        }
        if (h1 < h2)
            return -1;
        else if (h2 < h1)
            return 1;
        else
            return 0;
    }         
}
