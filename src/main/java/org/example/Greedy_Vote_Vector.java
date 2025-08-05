package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Greedy_Vote_Vector {
    public static int ChooseVertex(Vector<Double> weight){
        //Initialisieren der Hilfsvariablen
        double max = 0; //Zum finden des Knotens mit dem größten Gewicht
        List<Integer> s = new ArrayList<>();

        for(int i = 0; i < weight.size(); i++){
            if(max < weight.elementAt(i)){
                max = weight.elementAt(i);
            }
        }

        if(max != 0){
            for(int i = 0; i < weight.size(); i++){
                if(weight.get(i) == max){
                    s.add(i);
                }
            }
            return s.get((int)(Math.random() * s.size()));
        }else{
            return -1;
        }
    }

    public static void AdjustWeights (HashMap<Integer, List<Integer>> G, Vector<Double> weight, Vector<Boolean> covered, Vector<Double> vote, Integer vi){
        weight.set(vi, 0.0);

        for(Integer vj : G.get(vi)) {
            if(weight.get(vj) > 0){
                if(!covered.get(vi)){
                    weight.set(vj, rint(weight.get(vj)) - rint(vote.get(vi)));
                }
                if(!covered.get(vj)){
                    covered.set(vj, true);
                    weight.set(vj, rint(weight.get(vj)) - rint(vote.get(vj)));
                    for(Integer vk : G.get(vj)) {
                        if(weight.get(vk) > 0){
                            weight.set(vk, rint(weight.get(vk)) - rint(vote.get(vj)));
                        }
                    }
                }
            }
        }
        covered.set(vi, true);
    }

    public static List<Integer> Greedy_Vote (HashMap<Integer, List<Integer>> G){
        List<Integer> D = new ArrayList<>();

        Vector<Double> weight = new Vector<>();
        Vector<Double> vote = new Vector<>();
        Vector<Boolean> covered = new Vector<>();


        for(int i = 0; i < G.size(); i++){
            vote.add(rint(1/(double)(G.get(i).size() + 1)));
            covered.add(false);
            weight.add(vote.get(i));
        }

        for(int i = 0; i < G.size(); i++){
            for(Integer vj : G.get(i)){
                weight.set(i, rint(weight.get(i)) + rint(vote.get(vj)));
            }
        }

        int v;

        do{
            v = ChooseVertex(weight);
            if(v != -1){
                D.add(v);
                AdjustWeights(G, weight, covered, vote, v);
            }
        }while(v != -1);
        return D;
    }

    //Aufrunden von doubles (TEST)
    private static double rint(double value) {
        double d = Math.pow(10, 2);
        return Math.round(value * d) / d;
    }
}
