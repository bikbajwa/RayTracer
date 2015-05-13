
public class Scene {

	private String scene_name;
	private double image_width;
	private double image_height;
	private double recursion_depth;
	
	public Scene(String scene_name, double image_width, double image_height, double recursion_depth){
		this.scene_name = scene_name;
		this.image_width = image_width;
		this.image_height = image_height;
		this.recursion_depth = recursion_depth;
	}

	public String getScene_name() {
		return scene_name;
	}

	public double getImage_width() {
		return image_width;
	}

	public double getImage_height() {
		return image_height;
	}

	public double getRecursion_depth() {
		return recursion_depth;
	}

}
