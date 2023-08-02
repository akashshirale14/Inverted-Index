package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static TreeMap<String,  Integer> dictionary = new TreeMap<>();
    private static TreeMap<String, List> postings = new TreeMap<>();
    private static Map<String,Map<String,List<Integer>>> positionIndex = new TreeMap<>();
    public static void main(String[] args) throws FileNotFoundException {
        //Read doc file
        String[] docs = {"doc1.txt","doc2.txt","doc3.txt","doc4.txt"};
        String pathName = "src/documents";
        try {
            buildInvertedIndex(docs, pathName);
        } catch (Exception ex) {
            System.out.println("File not found...");
            throw ex;
        }
        printInvertedIndex();
        System.out.println();


        dictionary = new TreeMap<>();
        buildInvertedPositionalIndex(docs,pathName);
        printPositionalInvertedIndex();
    }

    public static void buildInvertedPositionalIndex(String[] docs, String pathName) throws FileNotFoundException {
        for (int i=0;i<docs.length;i++) {
            File file = new File(pathName +"/"+docs[i]);
            Scanner sc = new Scanner(file);
            int position = 1;
            while(sc.hasNext()){
                String line = sc.nextLine();
                line = line.replaceAll("\\s+"," ");
                line = line.replaceAll("[^\\sa-zA-Z0-9]", "");
                String[] terms = line.split(" ");
                for(String term: terms) {
                    term = term.toLowerCase();
                    dictionary.put(term,dictionary.getOrDefault(term,0)+1);
                    if(positionIndex.containsKey(term)) {
                        Map<String,List<Integer>> docWordPositionMap = positionIndex.get(term);
                        if(docWordPositionMap.containsKey(docs[i])){
                            List<Integer> positionArray = docWordPositionMap.get(docs[i]);
                            positionArray.add(position);
                        }else{
                            List<Integer> positionArray = new ArrayList<>();
                            positionArray.add(position);
                            docWordPositionMap.put(docs[i],positionArray);
                            positionIndex.put(term,docWordPositionMap);
                        }
                    }else{
                        List<Integer> positionArray = new ArrayList<>();
                        positionArray.add(position);
                        Map<String,List<Integer>>docWordPositionMap = new TreeMap<>();
                        docWordPositionMap.put(docs[i],positionArray);
                        positionIndex.put(term,docWordPositionMap);
                    }
                    position++;
                }
            }
        }
    }

    public static void buildInvertedIndex(String[]docs, String pathName) throws FileNotFoundException {
        for (int i=0;i<docs.length;i++) {
            File file = new File(pathName +"/"+docs[i]);
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String line = sc.nextLine();
                line = line.replaceAll("\\s+"," ");
                line = line.replaceAll("[^\\sa-zA-Z0-9]", "");
                String[] terms = line.split(" ");
                for(String term: terms) {
                    term = term.toLowerCase();
                    dictionary.put(term,dictionary.getOrDefault(term,0)+1);
                    if(postings.containsKey(term)) {
                        List<Integer> list = postings.get(term);
                        list.add(i + 1);
                    }else{
                        List list = new LinkedList();
                        list.add(i+1);
                        postings.put(term,list);
                    }
                }
            }
        }
    }

    public static void printInvertedIndex(){
        System.out.println("*** Printing the Inverted Index: ***");
        for(Map.Entry<String,Integer>entry: dictionary.entrySet()) {
            System.out.println("Dictionary-Key: " +entry.getKey() + " Count: "+entry.getValue());
            System.out.println("Posting-Key: "+entry.getKey()+ " Value: "+postings.get(entry.getKey()));
        }
    }

    public static void printPositionalInvertedIndex(){
        System.out.println("*** Printing the Positional Inverted Index: ***");
        for(Map.Entry<String,Integer>entry: dictionary.entrySet()) {
            System.out.println("Dictionary-Key: " +entry.getKey() + " Count: "+entry.getValue());
            System.out.println("Posting-Key: "+entry.getKey()+ " Value: "+ positionIndex.get(entry.getKey()));
        }
    }
}