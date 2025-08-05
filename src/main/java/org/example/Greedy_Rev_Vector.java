package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Greedy_Rev_Vector {
    public static HashMap<Integer, List<Integer>> G;
    public static int[] intArr;
    public static Vector<Integer> coveredby;
    public static Vector<Boolean> uniquely;
    public static List<Integer> D;

    public static int ChooseVertex(int start){
        while((start < D.size()) && uniquely.get(D.get(start))){
            start++;
        }
        if(start >= D.size()){
            return -1;
        }else{
            int min = G.get(D.get(start)).size();
            for(int a : D){
                if(G.get(a).size() == min && !uniquely.get(a)){
                    return a;
                }
            }
        }
        return -1;
    }

    public static void Adjust(int vi){
        coveredby.set(vi, coveredby.get(vi) -1);
        for(int vj : G.get(vi)) {
            if(coveredby.get(vi) == 1 && D.contains(vj)){
                uniquely.set(vj, true);
            }
            coveredby.set(vj, coveredby.get(vi) -1);
            if(coveredby.get(vj) == 1){
                if(D.contains(vj)){
                    uniquely.set(vj, true);
                }else{
                    for(int vk : G.get(vj)){
                        if(D.contains(vk)){
                            uniquely.set(vk, true);
                        }
                    }
                }
            }
        }
    }

    public static List<Integer> Greedy_Rev(HashMap<Integer, List<Integer>> g){
        double t1 = System.currentTimeMillis();

        G = g;

        intArr = new int[G.size()];
        for(int i = 0; i < G.size() ; i++){
            intArr[i] = i;
        }
        D = new ArrayList<>();
        List<Integer> one = new ArrayList<>();
        List<Integer> two = new ArrayList<>();
        List<Integer> three = new ArrayList<>();
        List<Integer> four = new ArrayList<>();
        List<Integer> five = new ArrayList<>();
        List<Integer> six = new ArrayList<>();
        List<Integer> seven = new ArrayList<>();
        List<Integer> eight = new ArrayList<>();
        List<Integer> nine = new ArrayList<>();
        List<Integer> other = new ArrayList<>();

        for(int i = 0; i < intArr.length; i++){
            int temp = G.get(i).size();
            if(temp == 1){
                one.add(i);
            }else if(temp == 2){
                two.add(i);
            }else if(temp == 3){
                three.add(i);
            }else if(temp == 4){
                four.add(i);
            }else if(temp == 5){
                five.add(i);
            }else if(temp == 6){
                six.add(i);
            }else if(temp == 7){
                seven.add(i);
            }else if(temp == 8){
                eight.add(i);
            }else if(temp == 9){
                nine.add(i);
            }else if(temp > 9){
                other.add(i);
            }
        }

        D.addAll(one);
        D.addAll(two);
        D.addAll(three);
        D.addAll(four);
        D.addAll(five);
        D.addAll(six);
        D.addAll(seven);
        D.addAll(eight);
        D.addAll(nine);
        D.addAll(other);

        coveredby = new Vector<>();
        uniquely = new Vector<>();

        for(int i = 0; i < G.size(); i++){
            coveredby.add(G.get(i).size()+1);
            if(coveredby.get(i) == 1){
                uniquely.add(true);
            }else{
                uniquely.add(false);
            }
        }

        int v;
        int start = 0;
        int index = -1;

        do{
            v = ChooseVertex(start);
            for (int j = 0; j < D.size(); j++) {
                if (D.get(j) == v) {
                    index = j;
                    break;
                }
            }
            if(v != -1){
                D.remove(index);
                Adjust(v);
            }
        }while(v != -1 && ((System.currentTimeMillis() - t1) <= 295000));
        return D;
    }

    public static int[] merge_sort(int l, int r) {

        if (l < r) {
            int q = (l + r) / 2;

            merge_sort(l, q);
            merge_sort(q + 1, r);
            merge(l, q, r);
        }
        return intArr;
    }

    private static void merge(int l, int q, int r) {
        int[] arr = new int[intArr.length];
        int i, j;
        for (i = l; i <= q; i++) {
            arr[i] = intArr[i];
        }
        for (j = q + 1; j <= r; j++) {
            arr[r + q + 1 - j] = intArr[j];
        }
        i = l;
        j = r;
        for (int k = l; k <= r; k++) {
            if (G.get(arr[i]).size() <= G.get(arr[j]).size()) {
                intArr[k] = arr[i];
                i++;
            } else {
                intArr[k] = arr[j];
                j--;
            }
        }
    }
}
