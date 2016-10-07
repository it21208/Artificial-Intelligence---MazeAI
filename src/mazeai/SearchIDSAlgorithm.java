/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeai;

import java.util.ListIterator;

/**
 *
 * @author tioa
 */

public class SearchIDSAlgorithm extends SearchAlgorithm {
    public static int SUCCESS = 0;
    public static int CUTOFF = 1;
    public static int FAILURE = 2;
    public SearchNode solutionNode;
    
    public SearchIDSAlgorithm(AIProblem problem) {
        super(problem, "IDS");
        solutionNode = null;
        this.fringe = null; // we don't need the Fringe
    }

    /* Redefine TREE-SEARCH for the child to hide the parent's real TREE-SEARCH.
       It is simple a "shell" for ITERATIVE_DEEPENING_SEARCH.
    */
    @Override
    protected SearchNode TREE_SEARCH(SearchNode initialNode) throws Exception {
        int result = ITERATIVE_DEEPENING_SEARCH(initialNode);
        if (result == SUCCESS)
            return solutionNode;
        else
            return null;
    }
    
    public int ITERATIVE_DEEPENING_SEARCH(SearchNode initialNode) throws Exception {
        int depth = initialNode.getDepth(), result = FAILURE;;
        while ((depth <= 100) && (result != SUCCESS)) {
            result = FAILURE;
            // search
            result = DEPTH_LIMITED_SEARCH(depth++, initialNode);
        }
        return result;
    }
    
    /* Corresponds to DEPTH-LIMITED-SEARCH() method required in pg112 */
    public int DEPTH_LIMITED_SEARCH(int limit, SearchNode initialNode) throws Exception{
        return RECURSIVE_DLS(initialNode, limit);
        
    }
    
    private int RECURSIVE_DLS(SearchNode node, int limit) throws Exception {
        boolean found = this.getProblem().isGoalState(node.getState());
        if (found) {
            solutionNode = node;
            return SUCCESS;
        }
        else if (node.getDepth() == limit)
            return CUTOFF;
        else {
            int result = FAILURE;
            ListIterator<SearchNode> itsn = node.EXPAND().listIterator();
            SearchNode sn = null;
            while ((itsn.hasNext()) && (result != SUCCESS)) {
                sn = itsn.next();
                result = RECURSIVE_DLS(sn, limit);
            }
            return result;
        }
    }    
}