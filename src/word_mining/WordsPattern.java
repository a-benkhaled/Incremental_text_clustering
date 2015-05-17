package word_mining;

import java.io.Serializable;
import java.util.HashSet;

public class WordsPattern implements Serializable{
	
	protected HashSet<String> wordSet;
	protected float support;
	public WordsPattern(HashSet<String> ws, float s) {
		// TODO Auto-generated constructor stub
		wordSet = ws;
		support = s;
	}
	
	public HashSet<String> getWordSet() {
		return wordSet;
	}

	@Override
	public String toString() {
		return "" + wordSet ;
	}
}
