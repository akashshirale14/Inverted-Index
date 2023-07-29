package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static TreeMap<String,  Integer> dictionary = new TreeMap<>();
    private static TreeMap<String, List> postings = new TreeMap<>();
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
        System.out.println("Printing the Inverted Index: ");
        for(Map.Entry<String,Integer>entry: dictionary.entrySet()) {
            System.out.println("Dictionary-Key: " +entry.getKey() + " Count: "+entry.getValue());
            System.out.println("Posting-Key: "+entry.getKey()+ " Value: "+postings.get(entry.getKey()));
        }
    }

    public static void buildInvertedIndex(String[] docs, String pathName) throws FileNotFoundException {
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
}