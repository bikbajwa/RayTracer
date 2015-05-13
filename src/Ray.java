import org.jblas.DoubleMatrix;


public class Ray {
	
	private DoubleMatrix origin;
	
	private DoubleMatrix direction;
	
	public Ray(DoubleMatrix origin, DoubleMatrix direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public DoubleMatrix getOrigin() {
		return origin;
	}
	
	public DoubleMatrix getDirection() {
		return direction;
	}
	
	public DoubleMatrix calculatePoint(double dist) {
		DoubleMatrix Q = origin.add(direction.mmul(dist));
		return Q;
	}
}
