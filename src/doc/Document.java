package doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import word_mining.WordsPattern;

public class Document {
	public String getFileName() {
		return fileName;
	}

	protected String fileName;
	protected ArrayList<String> transaction;
	protected HashMap<String, Integer> termFrequencies;
	protected HashMap<String, Float> termWeights;
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
	 * Calcul des fréquences dans l'espace des mots fréquents
	 * */
	public void initFreqAttr(ArrayList<WordsPattern> patt){
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
	public void initWeightAttr(ArrayList<WordsPattern> patt){
		weightAttr = new double[patt.size()];
		for(String term: termWeights.keySet()){
			for(int i=0; i<patt.size(); i++){
				if(patt.get(i).getWordSet().contains(term)){
					weightAttr[i] += termWeights.get(term);
				}
			}
		}
	}
	
	public ArrayList<String> getTransaction() {
		return transaction;
	}

	public Set<String> getTokens(){
		return termFrequencies.keySet();
	}
	
	public int getTokenFreq(String t){
		return termFrequencies.get(t);
	}
	
	public float getTokenWeight(String t){
		return termWeights.get(t);
	}

	public double[] getFreqAttr() {
		return freqAttr;
	}

	public double[] getWeightAttr() {
		return weightAttr;
	}
	
	
}
