package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Greedy_Ran_Vector {
    public static int ChooseVertex(Vector<Integer> weight){
        return RandomeSelection(weight);
    }

    public static void AdjustWeights(HashMap<Integer, List<Integer>> G, Vector<Integer> weight, Vector<Boolean> covered, Integer vi){
        weight.set(vi, 0);

        for(Integer vj : G.get(vi)){
            if(weight.get(vj) > 0){
                if(!covered.get(vi)){
                    weight.set(vj, weight.get(vj) - 1);
                }
                if(!covered.get(vj)){
                    covered.set(vj, true);
                    weight.set(vj, weight.get(vj) - 1);
                    for(Integer vk : G.get(vj)){
                        if(weight.get(vk) > 0){
                            weight.set(vk, weight.get(vk) - 1);
                        }
                    }
                }
            }
        }
        covered.set(vi, true);
    }

    public static List<Integer> Greedy_Ran(HashMap<Integer, List<Integer>> G){
        List<Integer> D = new ArrayList<>();

        Vector<Integer> weight = new Vector<>();
        Vector<Boolean> covered = new Vector<>();

        for(int i = 0; i < G.size(); i++){
            weight.add(G.get(i).size() + 1);
            covered.add(false);
        }

        int v;

        do{
            v = ChooseVertex(weight);
            if(v != -1){
                D.add(v);
                AdjustWeights(G, weight, covered, v);
            }
        }while(v != -1);

        return D;
    }

    private static int RandomeSelection(Vector<Integer> weight){
        List<Integer> randomeList = new ArrayList<>();

        for(int i = 0; i < weight.size(); i++){
            if(weight.get(i) > 0) {
                for (int j = 0; j < weight.get(i); j++) {
                    randomeList.add(i);
                }
            }
        }
        if(!randomeList.isEmpty()) {
            return randomeList.get((int)(Math.random() * randomeList.size()));
        }else{
            return -1;
        }
    }
}
