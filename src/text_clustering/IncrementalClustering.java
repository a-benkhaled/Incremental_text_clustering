package text_clustering;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.ProgressBar;
import doc.Document;
import doc.Indexer;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import word_mining.PatternMiner;
import word_mining.WordsPattern;

public class IncrementalClustering extends Cobweb {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String modelName;
	protected String pathLearningSet;
	protected String pathTermSpaceSet;
	protected boolean stemming = false;
	protected String pathStopListe;
	protected String pathScript;
	protected char repType = 'f';// Type de représentation ('f', 'p', 'e')
	protected int repTypeWS = 0;// ensemble de mots fréquents
	protected char repTypeWSForm;// ensemble de mots fréquents
	protected float minSupp;
	protected float maxSupp;
	protected int minTermNb;
	protected int maxTermNb;

	
	private Indexer index;
	
	public IncrementalClustering(String name, Indexer ind) {
		// TODO Auto-generated constructor stub
		super();
		index = ind;
		modelName = name;
	}

	public IncrementalClustering() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * Préparation du modèle de représentation des documents et transformation
	 * de ce modèle en forme intermédiare (Instance) pour pouvoir les
	 * communiquer à Weka
	 * 
	 * @param patt
	 *            ensemble de term fréquents
	 * @param vocabulary
	 *            espace des terms
	 * @param docx
	 *            liste des documents
	 * @param repType
	 *            type de représentation
	 * @param choice
	 *            cas des ensemble de term fréquents 0 : Fréquences, choice = 1:
	 *            poids
	 */
	
	public void prepareInstances() {
		//System.out.println("rep:" + repType);
		//System.out.println("repWS:" + repTypeWSForm);

		//System.out.println("repWS:" + repTypeWS);

		if (repType == 'e') {// Ensemble de terms fréquents
			// Extraction de motifs fréquents
			System.out.print("Extraction de motifs fréquents ");
			PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
			fpgrowth.setTargetItemType(repTypeWSForm);
			fpgrowth.setMinSupport((float) minSupp);
			fpgrowth.setMaxSupport((float) maxSupp);
			fpgrowth.setMinItemSize(minTermNb);
			fpgrowth.setMaxItemSize(maxTermNb);
			fpgrowth.mine();

			ArrayList<WordsPattern> patt = fpgrowth.getPatternsList();
			index.setWordPatterns(patt);
			System.out.println(patt.size());
			FastVector attributs = new FastVector(patt.size());
			for (int i = 0; i < patt.size(); i++) {
				attributs.addElement(new Attribute(patt.get(i).toString(), i));
			}
			initDocSet = new Instances("docCollection", attributs,
					index.getNumberOfDoc());

			switch (repTypeWS) {// choice = 0 : Fréquences, choice = 1:
									// poids
			case 0:// Fréquences
				for (Document d : index.getListOfDocument()) {
					d.getWordSetFreq(patt);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					initDocSet.add(e);
				}
				break;
			case 1:// Poids
				for (Document d : index.getListOfDocument()) {
					d.getWordSetWeight(patt);
					double tab[] = d.getWeightAttr();
					/*
					//misc.forsale_C14
		        	if(d.getFileName().equals("misc.forsale_C14.txt")){
		        		System.out.println("C14: w "+d.getTermWeights());
		        		System.out.println("C14: nbD"+index.getNumberOfDoc());
		        		for(String t:d.getTermWeights().keySet()){
		        			System.out.println("\t "+t+": "+index.getCollectionFreq().get(t));
		        		}
		        	}*/
					Instance e = new Instance(1, d.getWeightAttr(),
							d.getFileName());
					initDocSet.add(e);
				}
				break;
			default:
				for (Document d : index.getListOfDocument()) {
					d.getWordSetFreq(patt);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					initDocSet.add(e);
				}
				break;
			}
		} else {
			HashMap<String, Integer> vocabulary = index.getTermSpace();
			FastVector attributs = new FastVector(vocabulary.size());
			//System.out.println(vocabulary);
			for (String term : vocabulary.keySet()) {
				attributs.addElement(new Attribute(term, vocabulary.get(term)));
			}
			initDocSet = new Instances("docCollection", attributs, index
					.getListOfDocument().size());
			switch (repType) {
			case 'f':// Fréquences
				for (Document d : index.getListOfDocument()) {
					d.getWordFreq(vocabulary);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					// System.out.println(d.getFileName()+": "+d.getFreqAttr());
					initDocSet.add(e);
				}
				break;
			case 'p':// Poids
				for (Document d : index.getListOfDocument()) {
					d.getWordWeight(vocabulary);
					Instance e = new Instance(1, d.getWeightAttr(),
							d.getFileName());
					initDocSet.add(e);
				}
				break;
			}
		}
	}

	public void incClusterInstances(Indexer index) {
		int newdocx = index.getNewDoxIndex();
		if (repType == 'e') {// Ensemble de terms fréquents
			ArrayList<WordsPattern> patt = index.getWordPatterns();
			switch (repTypeWSForm) {// choice = 0 : Fréquences, choice = 1:
									// poids
			case 0:// Fréquences

				for (int i = newdocx; i < index.getListOfDocument().size(); i++) {
					Document d = index.getListOfDocument().get(i);
					d.getWordSetFreq(patt);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					e.setDataset(initDocSet);
					try {
						this.updateClusterer(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			case 1:// Poids
				for (int i = newdocx; i < index.getListOfDocument().size(); i++) {
					Document d = index.getListOfDocument().get(i);
					d.getWordSetWeight(patt);
					Instance e = new Instance(1, d.getWeightAttr(),
							d.getFileName());
					e.setDataset(initDocSet);
					try {
						this.updateClusterer(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			default:
				for (int i = newdocx; i < index.getListOfDocument().size(); i++) {
					Document d = index.getListOfDocument().get(i);
					d.getWordSetFreq(patt);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					e.setDataset(initDocSet);
					try {
						this.updateClusterer(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			}
		} else {
			HashMap<String, Integer> vocabulary = index.getTermSpace();
			switch (repType) {
			case 'f':// Fréquences
				for (int i = newdocx; i < index.getListOfDocument().size(); i++) {
					Document d = index.getListOfDocument().get(i);
					d.getWordFreq(vocabulary);
					Instance e = new Instance(1, d.getFreqAttr(),
							d.getFileName());
					e.setDataset(initDocSet);
					try {
						this.updateClusterer(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			case 'p':// Poids
				for (int i = newdocx; i < index.getListOfDocument().size(); i++) {
					Document d = index.getListOfDocument().get(i);
					d.getWordWeight(vocabulary);
					Instance e = new Instance(1, d.getWeightAttr(),
							d.getFileName());
					e.setDataset(initDocSet);
					try {
						this.updateClusterer(e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				break;
			}
		}
		this.updateFinished();
	}
	public void startClustering() {
		// TODO Auto-generated method stub
		try {
			buildClusterer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err
					.println("IncrementalClustering: erreur lors du clustering.");
		}
	}

	public void printInstances() {
		// TODO Auto-generated method stub
		for (int i = 0; i < initDocSet.numInstances(); i++)
			System.out.println(initDocSet.instance(i));
	}

	public String getModelName() {
		return modelName;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		IncrementalClustering classit = new IncrementalClustering();
		index.init("data\\train\\");
		classit.prepareInstances();
		try {
			classit.buildClusterer(classit.initDocSet);
			System.out.println(classit.graph());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(classit.toString());
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setPathLearningSet(String pathLearningSet) {
		this.pathLearningSet = pathLearningSet;
	}

	public void setPathTermSpaceSet(String pathTermSpaceSet) {
		this.pathTermSpaceSet = pathTermSpaceSet;
	}

	public void setStemming(boolean stemming) {
		this.stemming = stemming;
	}

	public void setPathStopListe(String pathStopListe) {
		this.pathStopListe = pathStopListe;
	}

	public void setPathScript(String pathScript) {
		this.pathScript = pathScript;
	}

	public void setRepType(char repType) {
		this.repType = repType;
	}

	public void setRepTypeWS(int repTypeWS) {
		this.repTypeWS = repTypeWS;
	}

	public void setRepTypeWSForm(char repTypeWSForm) {
		this.repTypeWSForm = repTypeWSForm;
	}

	public void setMinSupp(float minSupp) {
		this.minSupp = minSupp;
	}

	public void setMaxSupp(float maxSupp) {
		this.maxSupp = maxSupp;
	}

	public void setMinTermNb(int minTermNb) {
		this.minTermNb = minTermNb;
	}

	public void setMaxTermNb(int maxTermNb) {
		this.maxTermNb = maxTermNb;
	}
/*
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

	public void setAcuity(float acuity) {
		this.acuity = acuity;
	}
*/
	public void setInitDocSet(Instances initDocSet) {
		this.initDocSet = initDocSet;
	}

	// GETTERS
	public String getPathLearningSet() {
		return pathLearningSet;
	}

	public String getPathTermSpaceSet() {
		return pathTermSpaceSet;
	}

	public boolean getStemming() {
		return stemming;
	}

	public String getPathStopListe() {
		return pathStopListe;
	}

	public String getPathScript() {
		return pathScript;
	}

	public char getRepType() {
		return repType;
	}

	public int getRepTypeWS() {
		return repTypeWS;
	}

	public char getRepTypeWSForm() {
		return repTypeWSForm;
	}

	public float getMinSupp() {
		return minSupp;
	}

	public float getMaxSupp() {
		return maxSupp;
	}

	public int getMinTermNb() {
		return minTermNb;
	}

	public int getMaxTermNb() {
		return maxTermNb;
	}
/*
	public double getCutoff() {
		return cutoff;
	}

	public double getAcuity() {
		return acuity;
	}
*/
	public Instances getInitDocSet() {
		return initDocSet;
	}

	public Indexer getIndex() {
		return index;
	}
/*
	public void setState(ProgressBar pb) {
		// TODO Auto-generated method stub
		state = pb;
	}
*/

}