package ihm_chart;

import java.util.HashSet;

public class TableLine {
	private String N;
	private String term;
	private String frequency;
	private String poids;
	
	private String wordset;
	private String support;
	
	public TableLine(int cpt, String t, int freq) {
		// TODO Auto-generated constructor stub
		N = ""+ cpt;
		term = t;
		frequency = ""+freq;
	}
	
	public TableLine(int cpt, String t, int freq, float p) {
		// TODO Auto-generated constructor stub
		N = ""+ cpt;
		term = t;
		frequency = ""+freq;
		poids = String.valueOf(p);
	}
	
	public TableLine(int n, HashSet<String> fws, int supp){
		N = ""+n;
		support = ""+supp;
		wordset = fws.toString();
	}
	public TableLine(int n, HashSet<String> fws, int supp, double p){
		N = ""+n;
		support = ""+supp;
		wordset = fws.toString();
		poids = String.valueOf(p);
	}
	
	
	public String getPoids() {
		return poids;
	}

	public void setPoids(String poids) {
		this.poids = poids;
	}

	public String getN() {
		return N;
	}


	public void setN(String n) {
		N = n;
	}


	public String getTerm() {
		return term;
	}


	public void setTerm(String term) {
		this.term = term;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getWordset() {
		return wordset;
	}

	public void setWordset(String wordset) {
		this.wordset = wordset;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}
	
	
}
