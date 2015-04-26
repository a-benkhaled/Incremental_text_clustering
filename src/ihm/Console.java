package ihm;

import java.io.StringReader;

import javax.swing.JFrame;

import text_clustering.IncrementalClustering;
import weka.gui.graphvisualizer.GraphVisualizer;
import word_mining.PatternMiner;
import word_mining.WordsPattern;
import doc.Indexer;

public class Console {
	
	static IncrementalClustering classit;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		
		index.init("data\\train\\");
		
		PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
		fpgrowth.setTargetItemType('m');
		fpgrowth.setMinSupport((float) 9);
		fpgrowth.setMaxSupport((float) 10);
		fpgrowth.mine();
		
		if(fpgrowth.getNumberOfPatterns() == 0){//Pas d'ensemble fréquent
			return;
		}
		System.out.println(fpgrowth.getNumberOfPatterns());
		/*
		for(WordsPattern p: fpgrowth.getPatternsList()){
			System.out.println(p);
		}
		*/
		classit = new IncrementalClustering();
		classit.setAcuity(0.0001);
		//classit.setCutoff(0.002);//min cu
		classit.prepareInstances(fpgrowth.getPatternsList(), index.getTermSpace(), 
				index.getListOfDocument(), 'f',1);
		//classit.printInstances();
		try {
			classit.startClustering();
			System.out.println(classit.graph());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            try {
	            	createAndShowGUI();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
*/
	}
	
	private static void createAndShowGUI() throws Exception {
        //Create and set up the window.
        JFrame frame = new JFrame("Hierarchie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphVisualizer tree = new GraphVisualizer();
		StringReader reader = new StringReader(classit.graph());
		tree.readDOT(reader);
        frame.getContentPane().add(tree);
        //jp.repaint();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
}
