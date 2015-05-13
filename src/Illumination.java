
public class Illumination {
	
	private double red;
	private double green;
	private double blue;
	
	public Illumination(double red, double green, double blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String toString() {
		return red + " " + green + " " + blue;
	}
	
	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}
}
