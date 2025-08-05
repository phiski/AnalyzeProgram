package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeuristicDSAlgorithm {
    public static List<Integer> heuristicApproach(HashMap<Integer, List<Integer>> G){
        double t1 = System.currentTimeMillis();
        double startTime = System.currentTimeMillis();
        //Initialisieren der Variablen
        int[] covCnt = new int[G.size()];
        int[] score = new int[G.size()];
        for(int i = 0; i < G.size(); i++){
            int tmp = G.get(i).size();
            covCnt[i] = tmp;
            score[i] = tmp;
        }
        List<Integer> D = new ArrayList<>();

        for(int i = 0; i < G.size() && ((System.currentTimeMillis() - t1) <= 300000); i++){
            //Wähle einen Knoten mit minimalem Score
            int x = 1000;
            int min = 1000;
            for (int j = 0; j < score.length; j++) {
                if (score[j] < min) {
                    x = j;
                    min = score[j];
                    if (min == 1 || min == 0) {
                        break;
                    }
                }
            }
            //Überprüfen ob x einen Nachbarn hat, welcher nur eine Kante besitzt
            boolean tmp = searchForOne(G.get(x), covCnt);

            if(tmp){
                D.add(x);
                for(Integer j : G.get(x)){
                    covCnt[j] = 0;
                }
            }else{
                for(Integer j : G.get(x)){
                    if(covCnt[j] > 0){
                        covCnt[j]--;
                        score[j]++;
                    }
                }
            }
            score[x] = 1000;
        }
        if((System.currentTimeMillis() - t1) >= 300000){
            List<Integer> empty = new ArrayList<>();
            return empty;
        }
        return D;
    }

    public static boolean searchForOne(List<Integer> tmp, int[] covCnt){
        for(Integer j : tmp){
            if(covCnt[j] == 1){
                return true;
            }
        }
        return false;
    }
}
