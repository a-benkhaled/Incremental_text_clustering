package text_clustering;

public class EvalNode {
	float precision;
	float tp;
	float tn;
	String name;
	public EvalNode(float prec, float p, float n) {
		precision = prec;
		tp = (float)p;
		tn = (float) n;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrecision() {
		return precision;
	}

	public float getTp() {
		return tp;
	}

	public float getTn() {
		return tn;
	}

	public String getName() {
		return name;
	}
}
