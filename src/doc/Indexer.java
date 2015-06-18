package doc;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import word_mining.WordsPattern;

public class Indexer implements Serializable{

	protected ArrayList<Document> listOfDocument;
	protected HashMap<String, Integer> termSpace;
	protected HashMap<String, Integer> collectionFreq;//Apparence des terms dans la collections
	protected ArrayList<WordsPattern> wordPatterns;
	protected int numberOfDoc;
	protected int newDoxIndex;
	private boolean stemming = true;
	private String pathScript = "";
	private String pathAdditionnalVocabulary = "";

	public Indexer() {}
	
	public void printVocabulary(){
		System.out.println("Termspace size: " + termSpace.size());
		System.out.println(termSpace);
	}
	/**
	 * Charger les fichiers (tokenization + stemming(paramétrable))
	 * en même temps:
	 * 		- calcul du nombre d'apparition des terms dans la collection
	 * 		- et initialisation de l'espace des terms
	 * @param path
	 */
	public void init(String path){
		FileLoader loader = new FileLoader();
        HashMap<String, Integer> tmpIndex;
        ArrayList<String> tmpTransaction;
        int index = 0;
		if(stemming)
			loader.stemmingOn();
		else
			loader.stemmingOff();
		termSpace = new HashMap<>();
		collectionFreq = new HashMap<>();
		File folder = new File(path);
        File[] fileList= folder.listFiles();
        /*Script de prétraitement*/
    	if (!pathScript.isEmpty()){
    		for (int i = 0; i < fileList.length; i++) {
        		ApplyScript(path+fileList[i].getName());
    		}
    	}
    	/*Additionnal vocabulary*/
    	if (!pathAdditionnalVocabulary.isEmpty()){
    		File fol = new File(pathAdditionnalVocabulary);
            File[] fList= fol.listFiles();
           // if (!pathScript.isEmpty()){
        		for (int i = 0; i < fList.length; i++) {
                	tmpIndex = loader.asIndex(pathAdditionnalVocabulary+fList[i].getName());
                	for(String key:tmpIndex.keySet()){
                    	if(!termSpace.containsKey(key)){//Màj du vocabulaire
                			termSpace.put(key, index);
                			index++;
                		}
                	}
            		//ApplyScript(pathAdditionnalVocabulary+fList[i].getName());
        			//loader.asIndex
        		}
        	//}
    	}
    	
		folder = new File(path);
        fileList= folder.listFiles();
        numberOfDoc = fileList.length;
        listOfDocument = new ArrayList<>(numberOfDoc);
        int count = 0;
        for (int i = 0; i < numberOfDoc; i++) {
        	//for each doc
        	//Apply script
        	tmpIndex = loader.asIndex(path+fileList[i].getName());
        	tmpTransaction = loader.asTransaction(path+fileList[i].getName());
        	Document doc = new Document(fileList[i].getName(), tmpTransaction, tmpIndex);
        	listOfDocument.add(doc);
        	for(String key:tmpIndex.keySet()){//Màj des stats
        		if((collectionFreq.containsKey(key))){
        			//Incrémenter la fréquences d'apparition du term (key) ds la collection
        			count = collectionFreq.get(key);
        			count++;
        			collectionFreq.put(key, count);
        		}else
        			collectionFreq.put(key, 1);
        		if(!termSpace.containsKey(key)){//Màj du vocabulaire
        			termSpace.put(key, index);
        			index++;
        		}
        	}
        	
        }
        //Math.log10(freq[i]+1)*Math.log10((numberOfDoc+1)/(appeared[i] + 1));
        float w = 0;
        //System.out.println("Originale");
        for (int i = 0; i < numberOfDoc; i++) {//Calcul des poids
        	HashMap<String, Float> weight = new HashMap<>();
        	for(String key: listOfDocument.get(i).getTokens()){
        		w = (float) (Math.log10(listOfDocument.get(i).getTokenFreq(key)+1) *
        				Math.log10((collectionFreq.get(key) + 1)));
        		//if (w>0.5)
        		weight.put(key, w);
        	}
        	listOfDocument.get(i).termWeights = weight;
        }
	}
	
	private void ApplyScript(String filePath) {
		String cmd[] = {"python", pathScript, filePath};
        String aff = "";
        Runtime run = Runtime.getRuntime();
        try {
			run.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Utilisée dans le cas incrémental pour la représentation
	 * des documents
	 *  - Maj des comptage (fréquence par doc et par collection)
	 * @return void
	 */
	
	public void incIndex(String path){
		//collectionFreq = new HashMap<>();
		File folder = new File(path);
        File[] fileList= folder.listFiles();
		FileLoader loader = new FileLoader();
		newDoxIndex = numberOfDoc;
        numberOfDoc = numberOfDoc + fileList.length;
        HashMap<String, Integer> tmpIndex;
        ArrayList<String> tmpTransaction;
        int count =0;
        for (int i = 0; i < fileList.length; i++) {
        	if (!pathScript.isEmpty())
        		ApplyScript(path+fileList[i].getName());
			tmpIndex = loader.asIndex(path+fileList[i].getName());
			tmpTransaction = loader.asTransaction(path+fileList[i].getName());
			Document doc = new Document(fileList[i].getName(), tmpTransaction, tmpIndex);
        	listOfDocument.add(doc);
        	for(String key:tmpIndex.keySet()){//Màj des stats
        		if((collectionFreq.containsKey(key))){
        			//Incrémenter la fréquences d'apparition du term (key) ds la collection
        			count = collectionFreq.get(key);
        			count++;
        			collectionFreq.put(key, count);
        		}else
        			collectionFreq.put(key, 1);
        	}
        }
        float w = 0;
        for (int i = newDoxIndex; i < numberOfDoc; i++) {//Calcul des poids
        	HashMap<String, Float> weight = new HashMap<>();
        	for(String key: listOfDocument.get(i).getTokens()){
        		if (termSpace.containsKey(key)){
        			w = (float) (Math.log10(listOfDocument.get(i).getTokenFreq(key)+1) *
        				Math.log10(collectionFreq.get(key)+1));
        			weight.put(key, w);
        		}
        	}
        	/*
        	//misc.forsale_C14
        	if(listOfDocument.get(i).getFileName().equals("misc.forsale_C14.txt")){
        		System.out.println("C14: w "+weight);
        		System.out.println("C14: nbD"+numberOfDoc);
        		for(String t:weight.keySet()){
        			System.out.println("\t "+t+": "+collectionFreq.get(t));
        		}
        	}*/
        	listOfDocument.get(i).termWeights = weight;
        }
	}
	
	public HashMap<String, Integer> getKbestTerms(int k){
		HashMap<String, Integer> tmp = new HashMap<>();
		for(Document d: listOfDocument){
			for(String t:d.getBestTerms(k)){
				tmp.put(t, collectionFreq.get(t));
			}
		}
		return tmp;
	}
	public ArrayList<Document> getListOfDocument() {
		return listOfDocument;
	}

	public int getNumberOfDoc() {
		return numberOfDoc;
	}
	
	public int getNewDoxIndex() {
		return newDoxIndex;
	}
	public HashMap<String, Integer> getCollectionFreq() {
		return collectionFreq;
	}

	public void setStemming(boolean stemming) {
		this.stemming = stemming;
	}
	public HashMap<String, Integer> getTermSpace(){
		return termSpace;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		index.init("data\\train\\");
		index.printVocabulary();
	}

	public void setWordPatterns(ArrayList<WordsPattern> patt) {
		// TODO Auto-generated method stub
		wordPatterns = patt;
	}
	public ArrayList<WordsPattern> getWordPatterns() {
		// TODO Auto-generated method stub
		return wordPatterns;
	}

	public void setPathScript(String ps) {
		// TODO Auto-generated method stub
		pathScript = ps;
	}
	
	public void setAdditionnelVocabulary(String av) {
		// TODO Auto-generated method stub
		pathAdditionnalVocabulary = av;
		//System.out.println("VOCABULAIRE/"+av);
	}
	
}