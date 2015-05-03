package word_mining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import doc.Document;
import doc.Indexer;
import edu.stanford.nlp.patterns.Pattern;

public class PatternMiner {
	protected ArrayList<WordsPattern> patternsList;
	protected ArrayList<ArrayList<String>> transactionsList;
	protected int numberOfPatterns;
	
	private String algoPath = "data\\mine\\fpgrowth.exe";
	private String defaultDirectory = "data\\mine\\";
	private String inputFile = "data\\mine\\infile.txt";
	private String outputFile = "data\\mine\\outfile.txt";
	private char targetItemType = 'm';// t('s','c','m','g','r')
	private int minItemSize ;// m
	private int maxItemSize ;// n
	private float minSupport;// s
	private float maxSupport;// S
	private String[] otherOptions;
	private String cmd;
	
	public PatternMiner(ArrayList<Document> docx) {
		minItemSize = maxItemSize = -1;
		minSupport = maxSupport = -1;
		targetItemType = '0';
		transactionsList = new ArrayList<>(docx.size());
		patternsList = new ArrayList<>();
		for (Document d : docx) {
			//System.out.println(d.getTransaction());
			transactionsList.add(d.getTransaction());
		}
	}

	public void mine() {
		//Ecrire les mots dans un fichier
		writeTrasactions();
		//Lancer le traitement
		run();
	}
	
	private String buildCMD(){
		StringBuilder tmp = new StringBuilder();
		tmp.append(algoPath + " ");
		if(targetItemType != '0')
			tmp.append("-t" + targetItemType + " ");
		if(minSupport != -1)
			tmp.append("-s" + minSupport + " ");
		if(maxSupport != -1)
			tmp.append("-S" + maxSupport + " ");
		if(minItemSize != -1)
			tmp.append("-m" + minItemSize + " ");
		if(maxItemSize != -1)
			tmp.append("-n" + maxItemSize + " ");
		//********* CONSIDERER AUTRES OPTIONS
		
		tmp.append(inputFile + " ");
		tmp.append(outputFile + " ");
		System.err.println(tmp.toString());
		return tmp.toString();
	}
	
	public void run(){
		cmd = buildCMD();
		Runtime run = Runtime.getRuntime();
		BufferedReader buffer;
		String line="";
		try {
			Process pr = run.exec(cmd);
			buffer = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				System.err.println(line);
			}
			buffer.close();
			buffer = new BufferedReader(new FileReader(outputFile));
			float support = 0;
			while ((line = buffer.readLine()) != null) {
				String[] tmp = line.split(" ");
				HashSet<String> wordSet = new HashSet<>();
				for (int i=0; i < tmp.length - 1; i++) {
					wordSet.add(tmp[i]);
				}
				support = Float.valueOf( tmp[tmp.length-1].substring(1, tmp[tmp.length-1].length()-1));
				patternsList.add(new  WordsPattern(wordSet, support) );
			}
			if(!patternsList.isEmpty())
				numberOfPatterns = patternsList.size();
			buffer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("PatternMiner: file error ("+algoPath+") or ("
					+inputFile+") or ("
					+outputFile+")");
		}
	}

	private void writeTrasactions() {
		// TODO Auto-generated method stub
		// Enable file writing
		FileWriter file;
		try {
			file = new FileWriter(inputFile);
			PrintWriter writer = new PrintWriter(file);
			String sb;
			for (ArrayList<String> patt : transactionsList) {
				sb = patt.toString();
				sb = sb.substring(1, sb.length() - 1);// Se débarasser des crochets '[', ']'
				writer.println(sb);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumberOfPatterns() {
		return numberOfPatterns;
	}

	public ArrayList<WordsPattern> getPatternsList() {
		return patternsList;
	}

	public ArrayList<ArrayList<String>> getTransactionsList() {
		return transactionsList;
	}

	public void setAlgoPath(String algoPath) {
		this.algoPath = algoPath;
	}

	public void setDefaultDirectory(String defaultDirectory) {
		this.defaultDirectory = defaultDirectory;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public void setTargetItemType(char targetItemType) {
		this.targetItemType = targetItemType;
	}

	public void setMinItemSize(int minItemSize) {
		this.minItemSize = minItemSize;
	}

	public void setMaxItemSize(int maxItemSize) {
		this.maxItemSize = maxItemSize;
	}

	public void setMinSupport(float minSupport) {
		this.minSupport = minSupport;
	}

	public void setMaxSupport(float maxSupport) {
		this.maxSupport = maxSupport;
	}

	public void setOtherOptions(String[] otherOptions) {
		this.otherOptions = otherOptions;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		index.init("data\\train\\");
		PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
		fpgrowth.minSupport = (float) 60;
		fpgrowth.targetItemType = 'c';
		fpgrowth.mine();
		System.out.println(fpgrowth.patternsList.size());
		for(WordsPattern p: fpgrowth.patternsList){
			System.out.println(p);
		}
	}

}
