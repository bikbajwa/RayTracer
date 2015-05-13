import java.util.LinkedList;

public class Group {
	
	private String object_name;
	private LinkedList<Face> faces;
	
	public Group(String object_name)
	{
		this.object_name = object_name;
		faces = new LinkedList<Face>();
	}
	
	public LinkedList<Face> getFaces()
	{
		return faces;
	}
	
	public String getObject_name()
	{
		return object_name;
	}
	
	public void addFace(Face face)
	{
		this.faces.add(face);
	}
	
	public boolean equals(Object o)
	{
		if (o == this) {
			return true;
		}
		
		if(!(o instanceof Group)) {
			return false;
		}
		
		Group g = (Group) o;
		
		return object_name.equals(g.getObject_name());
	}
}
