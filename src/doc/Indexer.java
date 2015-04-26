package doc;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Indexer {

	protected ArrayList<Document> listOfDocument;
	protected HashMap<String, Integer> termSpace;
	protected HashMap<String, Integer> collectionFreq;//Apparence des terms dans la collections
	//protected double[][] termDoc;
	protected int numberOfDoc;
	protected boolean stemming = true;

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
		if(stemming)
			loader.stemmingOn();
		else
			loader.stemmingOff();
		termSpace = new HashMap<>();
		collectionFreq = new HashMap<>();
		File folder = new File(path);
        File[] fileList= folder.listFiles();
        numberOfDoc = fileList.length;
        listOfDocument = new ArrayList<>(numberOfDoc);
        HashMap<String, Integer> tmpIndex;
        ArrayList<String> tmpTransaction;
        int count =0;
        int index = 0;
        for (int i = 0; i < numberOfDoc; i++) {
        	//for each doc 
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
        for (int i = 0; i < numberOfDoc; i++) {//Calcul des poids
        	HashMap<String, Float> weight = new HashMap<>();
        	for(String key: listOfDocument.get(i).getTokens()){
        		w = (float) (Math.log10(listOfDocument.get(i).getTokenFreq(key)+1) *
        				Math.log10(numberOfDoc+1/collectionFreq.get(key)+1));
        		weight.put(key, w);
        	}
        	listOfDocument.get(i).termWeights = weight;
        }
	}
	
	public ArrayList<Document> getListOfDocument() {
		return listOfDocument;
	}

	public int getNumberOfDoc() {
		return numberOfDoc;
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
}