
public class Camera {

	private String camera_name;
	private double prp_x;
	private double prp_y;
	private double prp_z;
	private double vpn_x;
	private double vpn_y;
	private double vpn_z;
	private double vup_x;
	private double vup_y;
	private double vup_z;
	private double near;
	private double far;
	
	public Camera(String camera_name, double prp_x, double prp_y, double prp_z, double vpn_x, double vpn_y, double vpn_z, double vup_x, double vup_y, double vup_z, double near, double far){
		this.camera_name = camera_name;
		this.prp_x = prp_x;
		this.prp_y = prp_y;
		this.prp_z = prp_z;
		this.vpn_x = vpn_x;
		this.vpn_y = vpn_y;
		this.vpn_z = vpn_z;
		this.vup_x = vup_x;
		this.vup_y = vup_y;
		this.vup_z = vup_z;
		this.near = near;
		this.far = far;
	}
	
	public String toString() {
		return camera_name + " " + prp_x + " " + prp_y + " " + prp_z + " "
				+ vpn_x + " " + vpn_y + " " + vpn_z + " "
				+ vup_x + " " + vup_y + " " + vup_x + " "
				+ near + " " + far;
	}

	public String getCamera_name() {
		return camera_name;
	}

	public double getPrp_x() {
		return prp_x;
	}

	public double getPrp_y() {
		return prp_y;
	}

	public double getPrp_z() {
		return prp_z;
	}

	public double getVpn_x() {
		return vpn_x;
	}

	public double getVpn_y() {
		return vpn_y;
	}

	public double getVpn_z() {
		return vpn_z;
	}

	public double getNear() {
		return near;
	}

	public double getFar() {
		return far;
	}
	
	public double getVup_x() {
		return vup_x;
	}

	public double getVup_y() {
		return vup_y;
	}

	public double getVup_z() {
		return vup_z;
	}
}
