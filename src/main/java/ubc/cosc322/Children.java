package ubc.cosc322;

import java.util.PriorityQueue;
import java.util.TreeSet;

public class Children {
    final int maxSize = 100;

    double bestHeruistic;
    double worstHeuristic;
    GTNode bestChild;
    TreeSet<GTNode> firstPass;
    boolean isWhite;

    public Children(boolean isWhite){
        this.isWhite = isWhite;
        this.bestHeruistic = isWhite? Double.NEGATIVE_INFINITY: Double.POSITIVE_INFINITY;
        this.worstHeuristic = isWhite? Double.POSITIVE_INFINITY: Double.NEGATIVE_INFINITY;
        this.firstPass = new TreeSet();
    }

    public void addFirstPass(GTNode node){
        if(firstPass.size() < maxSize ){
            firstPass.add(node);
            worstHeuristic = firstPass.first().getHeuristic();
        }
        else
            if(node.getHeuristic() > worstHeuristic){
                firstPass.pollFirst();
                firstPass.add(node);
                worstHeuristic = firstPass.first().getHeuristic();
            }
        bestChild = firstPass.last();
    }

    public TreeSet<GTNode> getFirstPass(){
        return this.firstPass;
    }

    public GTNode getBestChild(){
        for(GTNode node : this.firstPass)
            if(isWhite){
                if(bestHeruistic > node.getHeuristic()){
                    bestChild = node;
                    bestHeruistic = node.getHeuristic();
                }
            }
            else {
                if (worstHeuristic < node.getHeuristic()) {
                    bestChild = node;
                    worstHeuristic = node.getHeuristic();
                }
            }
            return this.bestChild;
    }
}
