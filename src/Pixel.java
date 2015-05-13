
public class Pixel {
	
	private double R;
	private double G;
	private double B;
	private double gScale;
	
	public Pixel(double R, double G, double B, double gScale)
	{
		this.R = R;
		this.G = G;
		this.B = B;
		this.gScale = gScale;
	}
	
	public double getR()
	{
		return R;
	}
	
	public double getG()
	{
		return G;
	}
	
	public double getB()
	{
		return B;
	}
	
	public double getGScale()
	{
		return gScale;
	}
}