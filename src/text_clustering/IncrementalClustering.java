package text_clustering;

import java.util.ArrayList;

import doc.Document;
import doc.Indexer;
import weka.clusterers.Cobweb;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import word_mining.PatternMiner;
import word_mining.WordsPattern;

public class IncrementalClustering extends Cobweb{

	protected Instances initDocSet;
	
	public IncrementalClustering() {
		// TODO Auto-generated constructor stub
		super();
		
	}
	
	public void prepareInstances(ArrayList<WordsPattern> patt, ArrayList<Document> docx, 
								int choice){
		//choice = 0 : Fréquences, choice = 1: poids
		FastVector attributs = new FastVector(patt.size());
		for(int i=0; i<patt.size(); i++){
			attributs.addElement(new Attribute(patt.get(i).toString(), i));
		}
		initDocSet = new Instances("docCollection", attributs, 
				docx.size());
		switch (choice){
		case 0://Fréquences
			for(Document d:docx){
				d.initFreqAttr(patt);
				Instance e = new Instance(1, d.getFreqAttr(), d.getFileName());
				initDocSet.add(new Instance(e));
			}
		break;
		case 1://Poids
			for(Document d:docx){
				d.initWeightAttr(patt);
				Instance e = new Instance(1, d.getWeightAttr(), d.getFileName());
				initDocSet.add(new Instance(e));
			}
		break;
		default:
			for(Document d:docx){
				d.initFreqAttr(patt);
				Instance e = new Instance(1, d.getFreqAttr(), d.getFileName());
				initDocSet.add(new Instance(e));
			}
		break;
		}
	}

	public void startClustering() {
		// TODO Auto-generated method stub
		try {
			buildClusterer(initDocSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("IncrementalClustering: erreur lors du clustering.");
		}
	}
	
	public void printInstances() {
		// TODO Auto-generated method stub
		for(int i=0; i<initDocSet.numInstances(); i++)
			System.out.println(initDocSet.instance(i));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		index.init("data\\train\\");
		PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
		fpgrowth.setMinSupport( (float) 30);
		fpgrowth.setTargetItemType('m');
		fpgrowth.mine();
		System.out.println(fpgrowth.getNumberOfPatterns());
		for(WordsPattern p: fpgrowth.getPatternsList()){
			System.out.println(p);
		}
		IncrementalClustering classit = new IncrementalClustering();
		classit.prepareInstances(fpgrowth.getPatternsList(), index.getListOfDocument(), 0);
		for(int i=0; i<classit.initDocSet.numInstances(); i++)
			System.out.println(classit.initDocSet.instance(i));
		try {
			classit.buildClusterer(classit.initDocSet);
			System.out.println(classit.graph());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(classit.toString());
	}
}