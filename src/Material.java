
public class Material {

	private String name;
	private Illumination Ka;
	private Illumination Kd;
	private Illumination Ks;
	private double Ns;
	private double n1;
	private double Tr;
	private double Kr;
	private double Krf;
	
	public Material(String name, Illumination Ka, Illumination Kd, Illumination Ks, double Ns, double n1, double Tr, double Kr, double Krf)
	{
		this.name = name;
		this.Ka = Ka;
		this.Kd = Kd;
		this.Ks = Ks;
		this.Ns = Ns;
		this.n1 = n1;
		this.Tr = Tr;
		this.Kr = Kr;
		this.Krf = Krf;
	}

	public String getName() {
		return name;
	}
	
	public Illumination getKa() {
		return Ka;
	}

	public Illumination getKd() {
		return Kd;
	}

	public Illumination getKs() {
		return Ks;
	}

	public double getNs() {
		return Ns;
	}
	
	public double getN1() {
		return n1;
	}
	
	public double getTr() {
		return Tr;
	}
	
	public double getKr() {
		return Kr;
	}
	
	public double getKrf() {
		return Krf;
	}
	
	public String toString()
	{
		return name + "\n" +
			   "Ka " + Ka + "\n" +
			   "Kd " + Kd + "\n" +
			   "Ks " + Ks + "\n" +
			   "Ns " + Ns;
	}
}
