package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Integer> vertex = new ArrayList<>();
        List<Double> dominatingset = new ArrayList<>();
        List<Double> nt = new ArrayList<>();

        int graphLength = 0;
        List<Integer> DS = new ArrayList<>();
        List<Double> Zeit = new ArrayList<>();

        for (int t = 0; t < 1; t++) {
            HashMap<Integer, List<Integer>> test = new HashMap<>();

            double t1 = System.currentTimeMillis();

            //Hier wird die Instanz genannt, welche getestet werden soll
            FileReader fr = new FileReader("src\\main\\resources\\test.gr");
            BufferedReader br = new BufferedReader(fr);

            String tmp = "";

            //Initialisieren der Variablen, in welche Stückweise die Datei gespeichert wird
            String zeile;
            List<Integer> nodes = new ArrayList<>();
            //Zeilenweises lesen der Datei
            while ((zeile = br.readLine()) != null) {
                if (!(zeile.charAt(0) == 'c') && !(zeile.charAt(0) == ' ')) {
                    for (int i = 0; i < zeile.length(); i++) {

                        if (Character.isDigit(zeile.charAt(i))) {
                            tmp += zeile.charAt(i);
                        }
                        if ((!Character.isDigit(zeile.charAt(i)) || (i + 1) > zeile.length() - 1) && !tmp.isEmpty()) {
                            nodes.add(Integer.parseInt(tmp) - 1);
                            tmp = "";
                        }
                    }
                }
            }

            br.close();
            int GraphSize = nodes.getFirst();
            nodes.removeFirst();
            nodes.removeFirst();

            for (int g = 0; g <= GraphSize; g++) {
                test.put(g, new ArrayList<>());
            }

            for (int i = 0; i < nodes.size(); i += 2) {
                test.get(nodes.get(i)).add(nodes.get(i + 1));
                test.get(nodes.get(i + 1)).add(nodes.get(i));
            }
            System.out.println(System.currentTimeMillis() - t1);
            graphLength = test.size();

            List<Integer> U = Greedy_Rev_Arrays.Greedy_Rev(test); //Berechnen eines Dominating Sets
            t1 = System.currentTimeMillis() - t1; //Zeit stoppen
            DS.add(U.size()); //Größe des Dominating Sets zu der Liste DS hinzufügen

            if (t1 > 300000) { //Wenn mehr als 300 Sekunden benötigt wurden ...
                Zeit.add(310000.0); // füge 310 Sekunden der Liste hinzu ...
            } else {
                Zeit.add(t1); // ... ansonsten die eigentlich benötigte Zeit
            }
            //Erstellen von Lösungsdateien für den Verifier von PACE
            File datei = new File("SolNR" + t + "Graph.sol");
            FileWriter fw = new FileWriter(datei);
            fw.write(U.size() + "\n");
            for (int i = 0; i < U.size(); i++) {
                if (i + 1 < U.size()) {
                    fw.write((U.get(i) + 1) + "\n");
                } else {
                    fw.write((U.get(i) + 1) + "");
                }
            }
            fw.flush();
        }
        double avgDS = 0;
        int bestDS = DS.getFirst();
        for (int ds : DS) {
            avgDS = avgDS + ds;
            if (ds < bestDS) {
                bestDS = ds;
            }
        }
        avgDS = avgDS / DS.size();
        double avgTime = 0;
        double bestTime = Zeit.getFirst();
        for (double ds : Zeit) {
            avgTime = avgTime + ds;
            if (ds < bestTime) {
                bestTime = ds;
            }
        }
        //Erstellen von Dateien, welche die allgemeinen Informationen eines jeden Durchgangs enthalten
        avgTime = avgTime / Zeit.size();
        File datei = new File("GraphSolution.txt");
        FileWriter fw = new FileWriter(datei);
        fw.write("Graph\n");
        fw.write("AVG Dominating Set: (" + graphLength + "," + avgDS + ")\n");
        fw.write("AVGTime: (" + graphLength + "," + avgTime + ")\n");
        fw.write("Kleinstes DS: (" + graphLength + "," + bestDS + ")\n");
        fw.write("Beste Zeit: (" + graphLength + "," + bestTime + ")");
        fw.write("");
        fw.flush();
        vertex.add(graphLength);
        dominatingset.add(avgDS);
        nt.add(avgTime);
    }
}