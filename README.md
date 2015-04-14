# Incremental_text_clustering
We are intrested in developing a solution for clustering text documents in an incremental enviroment.
The current solution uses Cobweb [1][2] to cluster streaming documents. 

This system uses several other open source projects:
 - Weka's [3] for the implementation of Cobweb/Classit (with a little modification).
 - borgelt's Fp-growth[4][5] implementation to cluster words (which are the attributs of an instance).


[1]http://axon.cs.byu.edu/~martinez/classes/678/Papers/Fisher_Cobweb.pdf
[2]http://www.cs.cmu.edu/~callan/Papers/cikm06-nsahoo.pdf
[3]http://www.cs.waikato.ac.nz/ml/weka/
[4]http://www.borgelt.net//fpgrowth.html
[5}http://web.engr.illinois.edu/~hanj/pdf/dami04_fptree.pdf
