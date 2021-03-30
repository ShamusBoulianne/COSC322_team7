package ubc.cosc322;

import java.util.PriorityQueue;
import java.util.TreeSet;

public class Children {
    final int maxSize = 5;

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
        if (firstPass.size() < maxSize) {
            firstPass.add(node);
            if(isWhite)
                worstHeuristic = firstPass.first().getHeuristic();
            else
                bestHeruistic = firstPass.first().getHeuristic();
        }
        else{
            if(isWhite) {
                if (node.getHeuristic() < worstHeuristic) {
                    firstPass.pollFirst();
                    firstPass.add(node);
                    worstHeuristic = firstPass.first().getHeuristic();
                }
            } else {
                    if (node.getHeuristic() > bestHeruistic) {
                        firstPass.pollFirst();
                        firstPass.add(node);
                        bestHeruistic = firstPass.first().getHeuristic();
                    }
            }
        }
        bestChild = firstPass.last();
    }

    public TreeSet<GTNode> getFirstPass(){
        return this.firstPass;
    }

    public GTNode getBestChild(){
        double bestFound = Double.POSITIVE_INFINITY;
        double worstFound = Double.NEGATIVE_INFINITY;
        for(GTNode node : this.firstPass)
            if(isWhite){
                if(bestFound > node.getHeuristic()){
                    bestChild = node;
                    bestFound = node.getHeuristic();
                }
            }
            else {
                if (worstFound < node.getHeuristic()) {
                    bestChild = node;
                    worstFound = node.getHeuristic();
                }
            }
            return this.bestChild;
    }
}
