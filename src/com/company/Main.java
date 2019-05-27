package com.company;

import java.io.*;
import java.util.*;


public class Main {

    static ArrayList<Integer> weights = new ArrayList<>();
    static ArrayList<Integer> prices = new ArrayList<>();
    static int capacity;
    static int permutations;
    static Map<Integer, Integer> scores;

    static int[] characteristicVector;

    static {
        File sourceData = new File("5");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(sourceData));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            capacity = Integer.parseInt(reader.readLine());
            reader.lines().forEach(line -> {
                String[] itemPair = line.split(" ");
                weights.add(Integer.parseInt(itemPair[0]));
                prices.add(Integer.parseInt(itemPair[1]));
            });

            permutations = (int) Math.pow(2, weights.size());
            scores = new HashMap<>();

        } catch (IOException e) {
            e.printStackTrace();
        }
        characteristicVector = new int[weights.size()];
        for (int i = 0; i < characteristicVector.length; i++) {
            characteristicVector[i] = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Date());

        int max_score = 0;

        for (int i = 0; i < permutations; i++) {
            setCharacteristicVector(characteristicVector, i);

            int totalWeight = 0;
            int totalValue = 0;

            for (int k = characteristicVector.length - 1; k >= 0; k--) {
                if (characteristicVector[k] == 1) {
                    totalWeight += weights.get(k);
                    if (totalWeight <= capacity) {
                        totalValue += prices.get(k);
                    } else {
                        break;
                    }
                }
            }
            if (totalValue > max_score) {
                max_score = totalValue;
                scores.put(totalValue, i);
            }
        }

        int largestIndex = getIndexOfLargest(scores);
        System.out.println(largestIndex);
        System.out.println(getKeyFromValue(scores, largestIndex));
        StringBuilder result = new StringBuilder();
        String resultVector = Integer.toBinaryString(largestIndex);
        for (int i = 0; i < weights.size() - resultVector.length(); i++) {
            result.append(0);
        }
        result.append(resultVector);
        System.out.println(result
        );
        System.out.println(new Date());
    }

    private static int getIndexOfLargest( Map<Integer, Integer> map )
    {
        if ( map == null || map.size() == 0 ) return -1; // null or empty

        int largest = 0;
        for (Integer key: map.keySet()) {
            if (key > largest) {
                largest = key;
            }
        }
        return map.get(largest); // position of the first largest found
    }

    private static int getKeyFromValue(Map<Integer, Integer> map, int value) {
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return 0;
    }

    private static void setCharacteristicVector(int[] vector, int index) {
        int counter = vector.length - 1;
        while (index != 0) {
            vector[counter] = index % 2;
            counter--;
            index /= 2;
        }
    }
}
