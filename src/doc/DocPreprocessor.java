package doc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
/**
 * Cette classe impl�mente toutes 
 * les techniques de pr�traitement utilis�es par
 * le syst�me 
 * */
public class DocPreprocessor {
	
	private HashSet<String> stopList;
	private String stoplistPath = "data\\english.txt";
	private boolean stemming = true;//Activer ou d�sactiver le stemming
	
	public DocPreprocessor() {
		// TODO Auto-generated constructor stub
		stopList = new HashSet<>();
		//Charger la liste des mots vides
		BufferedReader buffer = null;
		try{	
    		buffer = new BufferedReader(new FileReader(stoplistPath));
    		String line;
    		while ((line = buffer.readLine()) != null){
    			stopList.add(line);
    		}
    		buffer.close();
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("DocPreprocessor: file not found ("+stoplistPath+")");
		}
	}
	/**
	 * Pr�traitement d'un mot � la fois
	 * 		- Tester s'il appartient � la stoplist
	 * 		- Radicalisation (optionnelle)
	 * */
	public String cleanWord(String word){
		word = word.toLowerCase();
		if(stopList.contains(word))
			return "";
		else{
			if(stemming){
				Stemmer stemmer = new Stemmer();
				stemmer.addAll(word.toCharArray());
				stemmer.stem();
				return stemmer.toString();
			}else
				return word;
		}
	}
	public void stemmingOn() {
		this.stemming = true;
	}
	public void stemmingOff() {
		this.stemming = false;
	}
}
