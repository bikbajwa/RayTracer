
public class Face {
	
	private Vertex v1;
	private Vertex v2;
	private Vertex v3;
	private Material material;
	
	public Face(Vertex v1, Vertex v2, Vertex v3, Material material){
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.material= material;
	}

	public String toString() {
		return v1.toString() + "\n"
				+ v2.toString() + "\n"
				+ v3.toString() + "\n";
	}
	
	public Vertex getV1() {
		return v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public Vertex getV3() {
		return v3;
	}

	public Material getMaterial() {
		return material;
	}
}
