
public class Vertex {
	
	private double x;
	private double y;
	private double z;
	private double w;
	
	public Vertex(double x, double y, double z, double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public String toString()
	{
		return x + " " + y + " " + z + " " + w;
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
}
