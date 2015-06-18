package doc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import word_mining.WordsPattern;

public class Document  implements Serializable{
	public String getFileName() {
		return fileName;
	}

	protected String fileName;
	protected ArrayList<String> transaction;
	protected HashMap<String, Integer> termFrequencies;
	protected HashMap<String, Float> termWeights;

	//Ci-dessous deux vecteurs pour représenter le document
	//Ils peuvent être en fonction de l'espace des termes
	//ou l'ensemble des terms fréquents
	protected double[] freqAttr;
	protected double[] weightAttr;
	protected int numberOfItem = 0;
	protected int headerSize = 0;
	
	public Document(String fn, ArrayList<String> t, HashMap<String, Integer> freq){
		fileName = fn;
		transaction = t;
		termFrequencies = freq;
		numberOfItem = transaction.size();
		headerSize = termFrequencies.size();
	}
	
	/*
	 * Calcul des fréquences dans l'espace des mots
	 * 
	 * */
	public void getWordFreq(HashMap<String, Integer> vocabulary){
		freqAttr = new double[vocabulary.size()];
		for(String term: termFrequencies.keySet()){
			if(vocabulary.containsKey(term)){
				freqAttr[vocabulary.get(term)] = termFrequencies.get(term);
			}
		}
	}

	/*
	 * Calcul des poids dans l'espace des mots
	 * 
	 * */
	public void getWordWeight(HashMap<String, Integer> vocabulary){
		weightAttr = new double[vocabulary.size()];
		for(String term: termWeights.keySet()){
			if(vocabulary.containsKey(term)){
				weightAttr[vocabulary.get(term)] = termWeights.get(term);
			}
		}
	}
	
	/*
	 * Calcul des fréquences dans l'espace des mots fréquents
	 * 
	 * */
	public void getWordSetFreq(ArrayList<WordsPattern> patt){
		freqAttr = new double[patt.size()];
		for(String term: termFrequencies.keySet()){
			for(int i=0; i<patt.size(); i++){
				if(patt.get(i).getWordSet().contains(term)){
					freqAttr[i] += termFrequencies.get(term);
				}
			}
		}
	}

	/*
	 * Calcul des poids dans l'espace des mots fréquents
	 * 
	 * */
	public void getWordSetWeight(ArrayList<WordsPattern> patt){
		weightAttr = new double[patt.size()];
		for(String term: termWeights.keySet()){
			for(int i=0; i<patt.size(); i++){
				if(patt.get(i).getWordSet().contains(term)){
					weightAttr[i] += termWeights.get(term);
				}
			}
		}
	}
	public HashSet<String> getBestTerms(int x){
		if (x >= termWeights.size()){
			return (HashSet<String>) termWeights.keySet();
		}else{
			HashSet<String> best = new HashSet<>(x);
			float w = 0;
			for(String t:termWeights.keySet()){
				if((best.isEmpty()) || (best.size()<x))
					best.add(t);
				else{
					for(int i=0; i<best.toArray().length; i++){
						if (termWeights.get(best.toArray()[i])<termWeights.get(t)){
							best.remove(best.toArray()[i]);
							best.add(t);
						}
					}
					/*
					for(String b:best){
						if (termWeights.get(t)>termWeights.get(b)){
							best.remove(b);
							best.add(t);
						}
					}
					*/
				}
			}
			return best;
		}
	}
	
	public ArrayList<String> getTransaction() {
		return transaction;
	}

	public Set<String> getTokens(){
		return termFrequencies.keySet();
	}
	
	public int getTokenFreq(String t){
		if(termFrequencies.containsKey(t))
			return termFrequencies.get(t);
		else
			return 0;
	}
	
	public float getTokenWeight(String t){
		if(termWeights.containsKey(t))
			return termWeights.get(t);
		else
			return 0;
	}

	public double[] getFreqAttr() {
		return freqAttr;
	}

	public double[] getWeightAttr() {
		return weightAttr;
	}

	public HashMap<String, Float> getTermWeights() {
		return termWeights;
	}
	public HashMap<String, Integer> getTermFrequency() {
		return termFrequencies;
	}
}
