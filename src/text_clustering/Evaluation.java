package text_clustering;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import weka.clusterers.Cobweb;

public class Evaluation {
	IncrementalClustering classit;
	ArrayList<HashMap<String, Integer>> clusters = new ArrayList<>();
	public HashMap<String, Integer> mainClass;
	public HashMap<String, Integer> nbEltClass;

	public Evaluation(IncrementalClustering c, String file) {
		// TODO Auto-generated constructor stub
		classit = c;
		mainClass = classit.getMainClasses();
		nbEltClass = classit.getAllClasses();
		try {
			for (String line:Files.readAllLines(Paths.get(file))){
				nbEltClass.put(line.split(":")[0],
						Integer.valueOf(line.split(":")[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clusters = classit.getClusters(mainClass);
		System.out.println("Nombre de clusters: "+clusters.size());
	}
	
/**
 * Evaluation des clusters 
 * Macro et Micro moyenne
 */
	
	public float clusterEvalMacroFscore() {
		float pM = 0, rM = 0, fscore = 0;
		float fscoreB = 1, fscoreM = 1;
		for (HashMap<String, Integer> c : clusters) {
			pM = 0; rM = 0;
			String m = "";
			int max = 0;
			int clusterSize = 0;
			for (String key : c.keySet()) {
				if (c.get(key) > max) {
					max = c.get(key);
					m = key;
				}
				clusterSize += c.get(key);
			}
			pM = (float) max / clusterSize;
			rM = (float) max / mainClass.get(m);
			fscore += ((2 * pM * rM) / (pM + rM));
		}
		return (fscore / clusters.size());
	}
	
	public float clusterEvalMicroFscore(){
		  float p =  0;
		  float p1 = 0;
		  float r = 0;
		  float pM;
		  float rM;
		  for(HashMap<String, Integer> c: clusters){
			  String m =  "";
			  int max = 0;
			  int clusterSize = 0;
			  for(String key:c.keySet()){
				  if(c.get(key)>max){
					  max = c.get(key);
					  m = key;
				  }
				  clusterSize+=c.get(key);
			  }
			  p += (float)max;
			  p1 +=clusterSize;
			  r += mainClass.get(m);
		  }
		  pM = p/p1;
		  rM = p/r;
		  return ((2*pM*rM)/(pM + rM));
	  }
	
	/**
	 * Evaluation de la hiérarchie
	 */
	
	public ArrayList<ArrayList<EvalNode>> levelPrecisions(){
		return classit.extractLevels();
	}
	
	public float HierarchieEvalMicro(){
		return classit.hierarchieMicroPrecision();
	}
	
	public float HierarchieEvalMacro(){
		return classit.hierarchieMacroPrecision();
		
	}
}
