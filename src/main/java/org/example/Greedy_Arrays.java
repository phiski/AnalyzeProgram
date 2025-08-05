package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Greedy_Arrays {
    public static int ChooseVertex(int[] weight){
        int max = 0;
        List<Integer> s = new ArrayList<>();

        for(int i = 0; i < weight.length; i++){
            if(weight[i] > max){
                max = weight[i];
            }
        }

        if(max == 0){
            return -1;
        }else{
            for(int i = 0; i < weight.length; i++){
                if(weight[i] == max){
                    s.add(i);
                }
            }
            return s.get((int)(Math.random() * s.size()));
        }
    }

    public static void AdjustWeights(HashMap<Integer, List<Integer>> G, int[] weight, boolean[] covered, Integer vi){
        weight[vi] = 0;

        for(Integer vj : G.get(vi)){
            if(weight[vj] > 0){
                if(!covered[vi]){
                    weight[vj]--;
                }
                if(!covered[vj]){
                    covered[vj] = true;
                    weight[vj]--;
                    for(Integer vk : G.get(vj)){
                        if(weight[vk] > 0){
                            weight[vk]--;
                        }
                    }
                }
            }
        }
        covered[vi] = true;
    }

    public static List<Integer> Greedy(HashMap<Integer, List<Integer>> G){
        double t1 = System.currentTimeMillis();
        List<Integer> D = new ArrayList<>();

        int[] weight = new int[G.size()];
        boolean[] covered = new boolean[G.size()];

        for(int i = 0; i < G.size(); i++){
            weight[i] = G.get(i).size()+1;
            covered[i] = false;
        }

        int v;

        do{
            v = ChooseVertex(weight);
            if(v != -1){
                D.add(v);
                AdjustWeights(G, weight, covered, v);
            }
        }while(v != -1 && ((System.currentTimeMillis() - t1) <= 300000));

        if((System.currentTimeMillis() - t1) >= 300000){
            List<Integer> empty = new ArrayList<>();
            return empty;
        }
        return D;
    }
}
