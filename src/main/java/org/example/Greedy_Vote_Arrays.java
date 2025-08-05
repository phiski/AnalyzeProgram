package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Greedy_Vote_Arrays {
    public static int ChooseVertex(double[] weight){
        //Initialisieren der Hilfsvariablen
        double max = 0; //Zum finden des Knotens mit dem größten Gewicht
        List<Integer> s = new ArrayList<>();

        for(int i = 0; i < weight.length; i++){
            if(max < weight[i]){
                max = weight[i];
            }
        }

        if(max != 0){
            for(int i = 0; i < weight.length; i++){
                if(weight[i] == max){
                    s.add(i);
                }
            }
            return s.get((int)(Math.random() * s.size()));
        }else{
            return -1;
        }
    }

    public static void AdjustWeights (HashMap<Integer, List<Integer>> G, double[] weight, boolean[] covered, double[] vote, Integer vi){
        weight[vi] = 0;

        for(Integer vj : G.get(vi)) {
            if(weight[vj] > 0){
                if(!covered[vi]){
                    weight[vj] = rint(weight[vj]) - rint(vote[vi]);
                }
                if(!covered[vj]){
                    covered[vj] = true;
                    weight[vj] = rint(weight[vj]) - rint(vote[vj]);
                    for(Integer vk : G.get(vj)) {
                        if(weight[vk] > 0){
                            weight[vk] = rint(weight[vk]) - rint(vote[vj]);
                        }
                    }
                }
            }
        }
        covered[vi] = true;
    }

    public static List<Integer> Greedy_Vote (HashMap<Integer, List<Integer>> G){
        double t1 = System.currentTimeMillis();
        List<Integer> D = new ArrayList<>();

        double[] weight = new double[G.size()];
        double[] vote = new double[G.size()];
        boolean[] covered = new boolean[G.size()];


        for(int i = 0; i < G.size(); i++){
            double tmp = (rint(1/(double)(G.get(i).size() + 1)));
            vote[i] = tmp;
            covered[i] = false;
            weight[i] = tmp;
        }

        for(int i = 0; i < G.size(); i++){
            for(Integer vj : G.get(i)){
                weight[i] = rint(weight[i]) + rint(vote[vj]);
            }
        }

        int v;

        do{
            v = ChooseVertex(weight);
            if(v != -1){
                D.add(v);
                AdjustWeights(G, weight, covered, vote, v);
            }
        }while(v != -1 && ((System.currentTimeMillis() - t1) <= 300000));
        if((System.currentTimeMillis() - t1) >= 300000){
            List<Integer> empty = new ArrayList<>();
            return empty;
        }
        return D;
    }

    //Aufrunden von doubles (TEST)
    private static double rint(double value) {
        double d = Math.pow(10, 2);
        return Math.round(value * d) / d;
    }
}