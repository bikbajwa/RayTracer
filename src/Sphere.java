
public class Sphere {

	private String sphere_name;
	private double x;
	private double y;
	private double z;
	private double radius;
	private Material material;
	
	public Sphere(String sphere_name, double x, double y, double z, double radius, Material material){
		this.sphere_name = sphere_name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.material = material;
	}

	public String getSphere_name() {
		return sphere_name;
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

	public double getRadius() {
		return radius;
	}
	
	public Material getMaterial() {
		return material;
	}
}
