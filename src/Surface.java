import org.jblas.DoubleMatrix;


public class Surface {

	private double distanceTo;
	private Face face;
	private Sphere sphere;
	private boolean isSphere;
	private DoubleMatrix Normal;
	private DoubleMatrix intersectionPoint;
	private DoubleMatrix Origin;
	
	public Surface(double distanceTo, Face face, Sphere sphere, boolean isSphere, DoubleMatrix Normal, DoubleMatrix intersectionPoint, DoubleMatrix Origin) {
		this.distanceTo = distanceTo;
		this.face = face;
		this.sphere = sphere;
		this.isSphere = isSphere;
		this.Normal = Normal;
		this.intersectionPoint = intersectionPoint;
		this.Origin = Origin;
	}
	
	public double getDistanceTo() {
		return distanceTo;
	}
	
	public Face getFace() {
		return face;
	}
	
	public Sphere getSphere() {
		return sphere;
	}
	
	public boolean isSphere() {
		return isSphere;
	}
	
	public DoubleMatrix getNormal() {
		return Normal;
	}
	
	public DoubleMatrix getIntersectionPoint() {
		return intersectionPoint;
	}
	
	public DoubleMatrix getOrigin() {
		return Origin;
	}
}
