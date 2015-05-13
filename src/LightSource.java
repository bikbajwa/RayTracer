
public class LightSource {
	private double x;
	private double y;
	private double z;
	private double w;
	private double r;
	private double g;
	private double b;
	
	public LightSource(double x, double y, double z, double w, double r, double g, double b)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getW() {
		return w;
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}
}
