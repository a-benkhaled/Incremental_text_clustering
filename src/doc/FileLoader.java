package doc;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.TokenSequenceMatcher;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class FileLoader  extends DocPreprocessor{
	protected String tokenRegularExpression = "/[a-zA-Z]{2,}?/";
	//protected DocPreprocessor dp;
	public FileLoader(){
		//dp = new DocPreprocessor();
	}
	
	/*
	 * Calcul des fréquences
	 * 
	 * */
	public HashMap<String, Integer> asIndex(String filePath){
		HashMap<String, Integer> tmp = new HashMap<>();
		TokenSequencePattern reWords = TokenSequencePattern.compile(tokenRegularExpression);
		TokenSequenceMatcher m;
		//dp.setStemming(true);
		try {
			PTBTokenizer ptbt = new PTBTokenizer( new FileReader(filePath), new CoreLabelTokenFactory(), "");
			List<CoreLabel> tokens = ptbt.tokenize();
			m = new TokenSequenceMatcher(reWords, tokens);
			String token="";
			int c = 0;
			while (m.find()) {
				token = this.cleanWord(m.group());
				if(!token.isEmpty())
					if(tmp.containsKey(token)){
						c = tmp.get(token);
						++c;
						tmp.put(token, c);
					}else
						tmp.put(token, 1);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("FileLoader: file not found ("+filePath+")");
		}
		
		return tmp;
	}
	
	/*
	 * Extraction des tokens selon l'expression régulière 
	 * par défaut : retourne les caractère alphabetique en
	 * minuscule
	 * 
	 */
	public ArrayList<String> asTransaction(String filePath){
		ArrayList<String> tmp = new ArrayList<>();
		TokenSequencePattern reWords = TokenSequencePattern.compile(tokenRegularExpression);
		TokenSequenceMatcher m;
		String token;
		try {
			PTBTokenizer ptbt = new PTBTokenizer( new FileReader(filePath), new CoreLabelTokenFactory(), "");
			List<CoreLabel> tokens = ptbt.tokenize();
			m = new TokenSequenceMatcher(reWords, tokens);
			while (m.find()) {
				token = this.cleanWord(m.group());
				if(!token.isEmpty())
					tmp.add(token);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("FileLoader: file not found ("+filePath+")");
		}
		return tmp;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileLoader loader = new FileLoader();
		//System.out.println(loader.asIndex("data\\test.txt"));
		//System.out.println(loader.asTransaction("data\\test.txt"));
	}
	
	public void setTokenRegularExpression(String re) {
		this.tokenRegularExpression = re;
	}
}
