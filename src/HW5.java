import org.jblas.*;

import static org.jblas.DoubleMatrix.*;
import static org.jblas.MatrixFunctions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class HW5 {
	
	Scanner commandScanner;
	Scanner objScannerVertices;
	Scanner objScanner;
	Scanner mtlScanner;
	LinkedList<Camera> cameraInfo;
	LinkedList<Sphere> sphereInfo;
	LinkedList<Scene> sceneInfo;
	LinkedList<LightSource> lightInfo;
	ArrayList<Vertex> vertexInfo;
	HashSet<Group> groupInfo;
	LinkedList<Material> materialInfo;
	String materialFile;
	String commandFile;
 	
	public HW5(String commands, String obj) throws FileNotFoundException
	{
		commandScanner = new Scanner(new File(commands));
		objScannerVertices = new Scanner(new File(obj));
		objScanner = new Scanner(new File(obj));
		cameraInfo = new LinkedList<Camera>();
		sphereInfo = new LinkedList<Sphere>();
		sceneInfo = new LinkedList<Scene>();
		lightInfo = new LinkedList<LightSource>();
		vertexInfo = new ArrayList<Vertex>();
		groupInfo = new HashSet<Group>();
		materialInfo = new LinkedList<Material>();
		commandFile = commands;
		
		parseCommandFile();
		parseObjFileVertices();
		parseMtlFile();
		parseObjFile();
	}
	
	public void parseCommandFile()
	{
		while (commandScanner.hasNextLine())
		{
			String line = commandScanner.nextLine();
			String[] lineParts = line.split("\\s+");
			
			if (lineParts[0].equals("c"))
			{
				Camera cam = new Camera(lineParts[1], Double.parseDouble(lineParts[2]), 
						Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]), 
						Double.parseDouble(lineParts[5]), Double.parseDouble(lineParts[6]), 
						Double.parseDouble(lineParts[7]), Double.parseDouble(lineParts[8]), 
						Double.parseDouble(lineParts[9]), Double.parseDouble(lineParts[10]), 
						Double.parseDouble(lineParts[11]), Double.parseDouble(lineParts[12]));
				cameraInfo.add(cam);
			}
			else if(lineParts[0].equals("r"))
			{
				Scene scene = new Scene(lineParts[1], Double.parseDouble(lineParts[2]), 
						Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]));
				sceneInfo.add(scene);
			}
			else if(lineParts[0].equals("l"))
			{
				LightSource light = new LightSource(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]),
						Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]), Double.parseDouble(lineParts[5]),
						Double.parseDouble(lineParts[6]), Double.parseDouble(lineParts[7]));
				lightInfo.add(light);
			}
		}
	}
	
	public void parseObjFileVertices()
	{		
		while (objScannerVertices.hasNextLine())
		{
			String line = objScannerVertices.nextLine();
			String[] lineParts = line.split("\\s+");
			if(lineParts.length == 0){
				
			}
			else if(lineParts[0].equals("v"))
			{
				if(lineParts.length == 4)	// No Homogenous Coordinate
				{
					Vertex vertex = new Vertex(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]),
							Double.parseDouble(lineParts[3]), 1);
					vertexInfo.add(vertex);
				}
				else						// Vertex has Homogenous Coordinate
				{
					Vertex vertex = new Vertex(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]),
							Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]));
					vertexInfo.add(vertex);
				}
			}
			else if(lineParts[0].equals("mtllib"))
			{
				materialFile = lineParts[1];
			}
		}
	}
	
	public void parseMtlFile() throws FileNotFoundException
	{
		String folder = "";
		int slashIndex = 0;
		for(int i = commandFile.length()-1; i >=0; i--)
		{
			if(commandFile.charAt(i) == '/')
			{
				slashIndex = i;
				break;
			}
		}
		folder = commandFile.substring(0, slashIndex + 1);
		mtlScanner = new Scanner(new File(folder + materialFile));
		
		String name = null;
		Illumination Ka = null;
		Illumination Kd = null;
		Illumination Ks = null;
		double Ns = -1;
		double n1 = -1;
		double Tr = -1;
		double Kr = -1;
		double Krf = -1;
		
		while (mtlScanner.hasNextLine())
		{
			String line = mtlScanner.nextLine();
			String[] lineParts = line.split("\\s+");
			if(lineParts[0].equals("newmtl"))
			{
				name = lineParts[1];
			}
			else if(lineParts[0].equals("Ka") || lineParts[0].equals("ka"))
			{
				Illumination illum = new Illumination(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Double.parseDouble(lineParts[3]));
				Ka = illum;
			}
			else if(lineParts[0].equals("Kd") || lineParts[0].equals("kd"))
			{
				Illumination illum = new Illumination(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Double.parseDouble(lineParts[3]));
				Kd = illum;
			}
			else if(lineParts[0].equals("Ks") || lineParts[0].equals("ks"))
			{
				Illumination illum = new Illumination(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]), Double.parseDouble(lineParts[3]));
				Ks = illum;
			}
			else if(lineParts[0].equals("Ns"))
			{
				Ns = Double.parseDouble(lineParts[1]);
			}
			else if(lineParts[0].equals("n1"))
			{
				n1 = Double.parseDouble(lineParts[1]);
			}
			else if(lineParts[0].equals("Tr"))
			{
				Tr = Double.parseDouble(lineParts[1]);
			}
			else if(lineParts[0].equals("Kr"))
			{
				Kr = Double.parseDouble(lineParts[1]);
			}
			else if(lineParts[0].equals("Krf"))
			{
				Krf = Double.parseDouble(lineParts[1]);
			}
			
			// Add material if all info has been provided
			if(name != null && Ka != null && Kd != null
					&& Ks != null && Ns != -1 && n1 != -1
					&& Tr != -1 && Kr != -1 && Krf != -1)
			{
				
				Material material = new Material(name, Ka, Kd, Ks, Ns, n1, Tr, Kr, Krf);
				materialInfo.add(material);
				
				name = null;
				Ka = null;
				Kd = null;
				Ks = null;
				Ns = -1;
				n1 = -1;
				Tr = -1;
				Kr = -1;
				Krf = -1;
			}
		}
	}
	
	public void parseObjFile()
	{
		Material currentMaterial = null;
		String currentGroup = "default";
		
		while (objScanner.hasNextLine())
		{
			String line = objScanner.nextLine();
			String[] lineParts = line.split("\\s+");
			
			if(lineParts.length == 0)
			{
				
			}
			else if(lineParts[0].equals("usemtl"))
			{
				String materialName = lineParts[1];
				for(Material mat : materialInfo)
				{
					if(mat.getName().equals(materialName))
					{
						currentMaterial = mat;
					}
				}
			}
			else if(lineParts[0].equals("g"))
			{
				if(lineParts.length == 1)	// No Group name
				{
					Group group = new Group("default");
					groupInfo.add(group);
					currentGroup = "default";
				}
				else						// Group has name
				{
					Group group = new Group(lineParts[1]);
					groupInfo.add(group);
					currentGroup = lineParts[1];
				}
			}
			else if (lineParts[0].equals("s"))
			{
				Sphere sphere = new Sphere(lineParts[1], Double.parseDouble(lineParts[2]),
						Double.parseDouble(lineParts[3]), Double.parseDouble(lineParts[4]),
						Double.parseDouble(lineParts[5]), currentMaterial);
				sphereInfo.add(sphere);
			}
			else if(lineParts[0].equals("f"))
			{
				// Triangulate faces and add to group
				for(int i = 1; i < (lineParts.length - 2); i++)
				{
					Vertex v1 = vertexInfo.get(Integer.parseInt(lineParts[1]) - 1);
					Vertex v2 = vertexInfo.get(Integer.parseInt(lineParts[i+1]) -1);
					Vertex v3 = vertexInfo.get(Integer.parseInt(lineParts[i+2]) -1);
					
					Face face = new Face(v1, v2, v3, currentMaterial);
					
					for(Group g : groupInfo)	// Add Face to current group
					{
						if(g.getObject_name().equals(currentGroup))
						{
							g.addFace(face);
							break;
						}
					}
				}
			}
		}
	}
	
	public void ray_trace() throws FileNotFoundException, UnsupportedEncodingException
	{
		for(Camera cam : cameraInfo) {
			for(Scene scene: sceneInfo) {
				
				PrintWriter colorWriter = new PrintWriter(scene.getScene_name() + "_" + cam.getCamera_name() + "_color.ppm", "UTF-8");
				PrintWriter depthWriter = new PrintWriter(scene.getScene_name() + "_" + cam.getCamera_name() + "_depth.ppm", "UTF-8");
	
				colorWriter.println("P3 " + (int)scene.getImage_width() + " " + (int)scene.getImage_height() + " 256");
				depthWriter.println("P3 " + (int)scene.getImage_width() + " " + (int)scene.getImage_height() + " 256");
				
				int xWidth = (int)scene.getImage_width();
				int yHeight = (int)scene.getImage_height();
	
				for (int i = 0; i <= xWidth-1; i++)
				{
					for (int j = 0; j <= yHeight-1; j++)
					{
						double x = (2.0 / (double)(xWidth-1))*j - 1.0;
						double y = (2.0 / (double)(yHeight-1))*i - 1.0;
						
						double[][] arrayOrigin = new double[][] {{cam.getPrp_x()}, {cam.getPrp_y()}, {cam.getPrp_z()}};
						DoubleMatrix Origin = new DoubleMatrix(arrayOrigin);
						
						/* VPN */
						double vpnX = cam.getVpn_x();
						double vpnY = cam.getVpn_y();
						double vpnZ = cam.getVpn_z();
						
						double[][] arrayVPN = new double[][] {{vpnX}, {vpnY}, {vpnZ}};
						DoubleMatrix VPN = new DoubleMatrix(arrayVPN);
						//System.out.println(VPN);
						
						/* n */
						DoubleMatrix n = Geometry.normalize(VPN);
						//System.out.println(n);
						
						/* VUP */
						double[][] arrayVUP = {{cam.getVup_x()}, {cam.getVup_y()}, {cam.getVup_z()}};
						DoubleMatrix VUP = new DoubleMatrix(arrayVUP);
						//System.out.println(VUP);
						
						/* u */
						DoubleMatrix u = Geometry.normalize(crossProduct(VUP, n));
						//System.out.println(u);
						
						/* V */
						DoubleMatrix V = Geometry.normalize(crossProduct(n, u));
						//System.out.println(V);
	
						/* P */
						DoubleMatrix P = Origin.sub(VPN.mmul(cam.getNear())).add(u.mmul(x)).add(V.mmul(-y));
						//System.out.println(P);
						
						/* U */
						DoubleMatrix U = Geometry.normalize(P.sub(Origin));
						
						/* Recursion Depth */
						int recursionDepth = (int) scene.getRecursion_depth();
						
						Ray r = new Ray(Origin, U);
						
						Surface s = ray_intersection(r);
						Pixel p = ray_cast(s, recursionDepth);
						colorWriter.println((int)p.getR() + " " + (int)p.getG() + " " + (int)p.getB());
						depthWriter.println((int)p.getGScale() + " " + (int)p.getGScale() + " " + (int)p.getGScale());
					}
				}
				
				colorWriter.close();
				depthWriter.close();
			}
		}
	}
	
	public Surface ray_intersection(Ray ray)
	{
		Surface surface = null;
		
		DoubleMatrix U = ray.getDirection();
		DoubleMatrix Origin = ray.getOrigin();
		
		double minDistance = -1;
		for (Sphere sphere: sphereInfo)
		{
			
			double sphereX = sphere.getX();
			double sphereY = sphere.getY();
			double sphereZ = sphere.getZ();
			double radius = sphere.getRadius();
			
			double[][] arrayO = new double[][] {{sphereX}, {sphereY}, {sphereZ}};
			DoubleMatrix O = new DoubleMatrix(arrayO);
	
			Double v = (O.sub(Origin)).dot(U);
			
			Double c = O.distance2(Origin);
			
			Double dsquared = (radius*radius) - ((c*c) - (v*v));
			if(dsquared >=0)
			{
				Double d = sqrt(dsquared);
				double s = v - d;
				if ((s < minDistance || minDistance == -1) && s > 0)
				{
					minDistance = s;
					
					DoubleMatrix Q = Origin.add(U.mmul(s));
					DoubleMatrix N = Q.sub(O);
					N = Geometry.normalize(N);
		
					/* Store surface */
					surface = new Surface(s, null, sphere, true, N, Q, Origin);
				}
			}
		}
		
		for(Group group : groupInfo)
		{
			for(Face face : group.getFaces())
			{				
				Vertex A = face.getV1();
				Vertex B = face.getV2();
				Vertex C = face.getV3();
				
				double[][] arrayVertCalculations = new double[][]{{(B.getX() - A.getX()) , (C.getX() - A.getX()), -U.get(0,0)},
																{(B.getY() - A.getY()) , (C.getY() - A.getY()), -U.get(1,0)},
																{(B.getZ() - A.getZ()) , (C.getZ() - A.getZ()), -U.get(2,0)}};
				DoubleMatrix VertCalculations = new DoubleMatrix(arrayVertCalculations);
				DoubleMatrix inverseVertCalculation = inverse(VertCalculations);
				
				double[][] arrayQminusA = new double[][] {{Origin.get(0,0) - A.getX()},
														  {Origin.get(1,0) - A.getY()},
														  {Origin.get(2,0) - A.getZ()}};
				DoubleMatrix QminusA = new DoubleMatrix(arrayQminusA);
				DoubleMatrix BYt = inverseVertCalculation.mmul(QminusA);
				
				double B1 = BYt.get(0,0);
				double Y1 = BYt.get(1,0);
				double t1 = BYt.get(2,0);
				
				if(B1 >= 0 && Y1 >= 0
						&& (B1 + Y1) <= 1)
				{
					if ((t1 < minDistance || minDistance == -1) && t1 > 0)
					{
						minDistance = t1;
						
						/* Calculate the Normal */
						double[][] arrayE1 = new double[][] {{B.getX() - A.getX()},
															 {B.getY() - A.getY()},
															 {B.getZ() - A.getZ()}};
						DoubleMatrix E1 = new DoubleMatrix(arrayE1);
						
						double[][] arrayE2 = new double[][] {{C.getX() - B.getX()},
															 {C.getY() - B.getY()},
															 {C.getZ() - B.getZ()}};
						DoubleMatrix E2 = new DoubleMatrix(arrayE2);
						
						DoubleMatrix N = Geometry.normalize(crossProduct(E1, E2));
						
						/* Store the values of illumination and distance */
						surface = new Surface(t1, face, null, false, N, Origin.add(U.mmul(t1)), Origin);
					}
				}
			}
		}
		return surface;
	}
	
	public Pixel ray_cast(Surface surface, int recursionDepth)
	{
		Pixel pixel = new Pixel(0, 0, 0, 0);
		if(surface == null) {
			return pixel;
		}
		
		/* Variables necessary for illumination */
		Illumination Ka = null;
		Illumination Ks = null;
		Illumination Kd = null;
		DoubleMatrix intersectionPoint = null;
		DoubleMatrix Normal = null;
		double Ns = 0;
		double n1 = 0;
		double Tr = 0;
		double Kr = 0;
		double Krf = 0;
		DoubleMatrix Origin;
		
		if(surface.isSphere())
		{
			Ka = surface.getSphere().getMaterial().getKa();
			Ks = surface.getSphere().getMaterial().getKs();
			Kd = surface.getSphere().getMaterial().getKd();
			intersectionPoint = surface.getIntersectionPoint();
			Normal = surface.getNormal();
			Ns = surface.getSphere().getMaterial().getNs();
			n1 = surface.getSphere().getMaterial().getN1();
			Tr = surface.getSphere().getMaterial().getTr();
			Kr = surface.getSphere().getMaterial().getKr();
			Krf = surface.getSphere().getMaterial().getKrf();
			Origin = surface.getOrigin();
		}
		else {
			Ka = surface.getFace().getMaterial().getKa();
			Ks = surface.getFace().getMaterial().getKs();
			Kd = surface.getFace().getMaterial().getKd();
			intersectionPoint = surface.getIntersectionPoint();
			Normal = surface.getNormal();
			Ns = surface.getFace().getMaterial().getNs();
			n1 = surface.getFace().getMaterial().getN1();
			Tr = surface.getFace().getMaterial().getTr();
			Kr = surface.getFace().getMaterial().getKr();
			Krf = surface.getFace().getMaterial().getKrf();
			Origin = surface.getOrigin();
		}
		
		double R = 0;
		double G = 0;
		double B = 0;
		double GrayScale = 0;
		
		/* Illuminate the nearest object */
		if(Ka != null)
		{
			/* Ambient */
			double[][] arrayKAmbient = new double[][] {{Ka.getRed(), 0, 0},
													  {0, Ka.getGreen(), 0},
													  {0, 0, Ka.getBlue()}};
			DoubleMatrix KAmbient = new DoubleMatrix(arrayKAmbient);
			
			double AmbientVal = 20;
			
			double[][] arrayBAmbient = new double[][] {{AmbientVal},
													   {AmbientVal},
													   {AmbientVal}};
			
			DoubleMatrix BAmbient = new DoubleMatrix(arrayBAmbient);
			
			DoubleMatrix Ambience = KAmbient.mmul(BAmbient);
			
			
			
			/* Gray Scale Calculations */
				GrayScale = 255;
			
			R = Ambience.get(0, 0);
			G = Ambience.get(1, 0);
			B = Ambience.get(2, 0);
			
			for(LightSource light : lightInfo)
			{
				double[][] arrayBrightness = new double[][] {{light.getR()},
															 {light.getG()},
															 {light.getB()}};
				DoubleMatrix Brightness = new DoubleMatrix(arrayBrightness);
				
				double[][] arrayL = new double[][] {{light.getX()},
											   		{light.getY()},
											   		{light.getZ()}};
				DoubleMatrix L = new DoubleMatrix(arrayL);
				L = Geometry.normalize(L);
				
				if(light.getW() != 0) {	// Not infinitely far away
					arrayL = new double[][]{{light.getX() - intersectionPoint.get(0, 0)},
					   						{light.getY() - intersectionPoint.get(1, 0)},
					   						{light.getZ() - intersectionPoint.get(2, 0)}};
					L = new DoubleMatrix(arrayL);
					L = Geometry.normalize(L);
				}
				
				/* Calculate if there is a shadow */
				double[][] arrayLightLocation = new double[][] {{light.getX()},
															{light.getY()},
															{light.getZ()}};
				DoubleMatrix LightLocation = new DoubleMatrix(arrayLightLocation);
				
				boolean shadow = false;
				
				DoubleMatrix U = Geometry.normalize(LightLocation.sub(intersectionPoint));
				
				DoubleMatrix Epsilon = U.mmul(0.001);
				
				DoubleMatrix intPlusEpsilon = intersectionPoint.add(Epsilon);
				
				intersectionPoint = intPlusEpsilon;
				
				U = Geometry.normalize(LightLocation.sub(intersectionPoint));
				
				double distFromCastToLight = intersectionPoint.distance2(LightLocation);
				
				for (Sphere sphere: sphereInfo)
				{
					
					double sphereX = sphere.getX();
					double sphereY = sphere.getY();
					double sphereZ = sphere.getZ();
					double radius = sphere.getRadius();
					
					double[][] arrayO = new double[][] {{sphereX}, {sphereY}, {sphereZ}};
					DoubleMatrix O = new DoubleMatrix(arrayO);
			
					Double v = (O.sub(intersectionPoint)).dot(U);
					
					Double c = O.distance2(intersectionPoint);
					
					Double dsquared = (radius*radius) - ((c*c) - (v*v));
					if(dsquared >=0)
					{
						Double d = sqrt(dsquared);
						double s = v - d;
						if (s > 0 && s < distFromCastToLight)
						{
							shadow = true;
							break;
						}
					}
				}
				
				for(Group group : groupInfo)
				{
					for(Face face : group.getFaces())
					{				
						Vertex A = face.getV1();
						Vertex BVert = face.getV2();
						Vertex C = face.getV3();
						
						double[][] arrayVertCalculations = new double[][]{{(BVert.getX() - A.getX()) , (C.getX() - A.getX()), -U.get(0,0)},
																		{(BVert.getY() - A.getY()) , (C.getY() - A.getY()), -U.get(1,0)},
																		{(BVert.getZ() - A.getZ()) , (C.getZ() - A.getZ()), -U.get(2,0)}};
						DoubleMatrix VertCalculations = new DoubleMatrix(arrayVertCalculations);
						DoubleMatrix inverseVertCalculation = inverse(VertCalculations);
						
						double[][] arrayQminusA = new double[][] {{intersectionPoint.get(0,0) - A.getX()},
																  {intersectionPoint.get(1,0) - A.getY()},
																  {intersectionPoint.get(2,0) - A.getZ()}};
						DoubleMatrix QminusA = new DoubleMatrix(arrayQminusA);
						DoubleMatrix BYt = inverseVertCalculation.mmul(QminusA);
						
						double B1 = BYt.get(0,0);
						double Y1 = BYt.get(1,0);
						double t1 = BYt.get(2,0);
						
						if(B1 >= 0 && Y1 >= 0
								&& (B1 + Y1) <= 1)
						{
							
							if (t1 > 0 && t1 < distFromCastToLight)
							{
								shadow = true;
								break;
							}
						}
					}
				}
				
				/*------------------------------------------------------------------------*/
				
				if(!shadow)
				{
					/* Calculate diffuse illumination */
					double cosTheta = Normal.dot(L);
					
					if(cosTheta >=0)
					{
						double[][] arrayKDiffuse = new double[][] {{Kd.getRed(), 0, 0},
								  								   {0, Kd.getGreen(), 0},
								  								   {0, 0, Kd.getBlue()}};
						DoubleMatrix KDiffuse = new DoubleMatrix(arrayKDiffuse);
						
						DoubleMatrix Diffuse = KDiffuse.mmul(Brightness).mmul(cosTheta);
						R = R + Diffuse.get(0, 0);
						G = G + Diffuse.get(1, 0);
						B = B + Diffuse.get(2, 0);
					}
					
					/* Calculate specular reflection */			
					double[][] arrayVSpec = new double[][]{{Origin.get(0, 0) - intersectionPoint.get(0, 0)},
													   	   {Origin.get(1, 0) - intersectionPoint.get(1, 0)},
													   	   {Origin.get(2, 0) - intersectionPoint.get(2, 0)}};
					DoubleMatrix VSpec = new DoubleMatrix(arrayVSpec);
					VSpec = Geometry.normalize(VSpec);
					
					DoubleMatrix RSpec = Normal.mmul(2*(L.dot(Normal))).sub(L);
					double cosPhi = VSpec.dot(RSpec);
					
					if(cosPhi >= 0)
					{
						double[][] arrayKSpec = new double[][]{{Ks.getRed(), 0, 0},
								   							   {0, Ks.getGreen(), 0},
								   							   {0, 0, Ks.getBlue()}};
						DoubleMatrix KSpec = new DoubleMatrix(arrayKSpec);
						double cosPhiToNs = Math.pow(cosPhi, Ns);
						
						DoubleMatrix Specular = KSpec.mmul(Brightness).mmul(cosPhiToNs);
						R = R + Specular.get(0, 0);
						G = G + Specular.get(1, 0);
						B = B + Specular.get(2, 0);
					}
				}
			}
			
			
		}
		
		/* Add Reflection */
		if (recursionDepth != 1) {
	
			/* Direction of ray */
			DoubleMatrix L = Geometry.normalize(Origin.sub(intersectionPoint));
			DoubleMatrix N = Normal;
			double TwoLdotN = 2.0 * L.dot(N);
			DoubleMatrix TwoLdotNxN = N.mmul(TwoLdotN);
			DoubleMatrix Direction = Geometry.normalize(TwoLdotNxN.sub(L));
			DoubleMatrix Epsilon = Direction.mmul(0.001);
			
			/* Origin of ray */
			DoubleMatrix recursionOrigin = intersectionPoint.add(Epsilon);
			
			Ray ray = new Ray(recursionOrigin, Direction);
			if(surface.isSphere()) {
				System.out.println(recursionOrigin);
				System.out.println(Direction);
				System.exit(0);
			}
			Surface sur = ray_intersection(ray);
			if(sur != null) {
				Pixel pix = ray_cast(sur, recursionDepth - 1);
				
				R += (Kr * pix.getR());
				G += (Kr * pix.getG());
				B += (Kr * pix.getB());
				
			}
		}
		
		R = Tr*R;
		G = Tr*G;
		B = Tr*B;
		
		/* Add refraction */
		if(surface.isSphere()) {
			
			/* Exit point of a sphere */
			double x = surface.getSphere().getX();
			double y = surface.getSphere().getY();
			double z = surface.getSphere().getZ();
			
			double[][] arrayCenter = new double[][] {{x},
													{y},
													{z}};
			DoubleMatrix Center = new DoubleMatrix(arrayCenter);
			
			DoubleMatrix Pc = Center.sub(intersectionPoint);
			
			double u = 1.0/n1;
			
			DoubleMatrix W = Geometry.normalize(intersectionPoint.sub(Origin));
			double WdotN = W.dot(Normal);
			
			double Beta = -u * (WdotN) - Math.sqrt(1 - (u*u) + ((u*u) * (WdotN*WdotN)));
			double Alpha = u;
			
			DoubleMatrix T = W.mmul(Alpha).add(Normal.mmul(Beta));
			
			double Pcx = Pc.get(0, 0);
			double Pcy = Pc.get(1, 0);
			double Pcz = Pc.get(2, 0);
			double Tx = T.get(0, 0);
			double Ty = T.get(1, 0);
			double Tz = T.get(2, 0);
			double v = Pcx*Tx + Pcy*Ty + Pcz*Tz;
			
			double Px = intersectionPoint.get(0, 0);
			double Py = intersectionPoint.get(1, 0);
			double Pz = intersectionPoint.get(2, 0);
			double S2x = Px + 2*v*Tx;
			double S2y = Py + 2*v*Ty;
			double S2z = Pz + 2*v*Tz;
			double[][] arrayS2 = new double[][]{{S2x},
												{S2y},
												{S2z}};
			DoubleMatrix S2 = new DoubleMatrix(arrayS2);
			
			/* Angle of ray leaving Sphere */
			double ui = n1;
			double ut = 1;
			
			u = ui/ut;
			
			Normal = Geometry.normalize(Center.sub(S2));
			
			W = T;
			WdotN = W.dot(Normal);
			
			Beta = -u * (WdotN) - Math.sqrt(1 - (u*u) + ((u*u) * (WdotN*WdotN)));
			Alpha = u;
			
			DoubleMatrix T2 = W.mmul(Alpha).add(Normal.mmul(Beta));
			T2 = Geometry.normalize(T2);
			
			Ray ray = new Ray(S2, T2);
			
			Surface sur = ray_intersection(ray);
			if(sur != null) {
				Pixel pix = ray_cast(sur, recursionDepth);
				
				R += (1-Tr)*(Krf * pix.getR());
				G += (1-Tr)*(Krf * pix.getG());
				B += (1-Tr)*(Krf * pix.getB());
			}
		}
		
		if (R > 255){
			R = 255;
		}
		if (G > 255){
			G = 255;
		}
		if (B > 255){
			B = 255;
		}
		
		pixel = new Pixel(R, G, B, 255 - GrayScale);
		
		return pixel;
	}
	
	public DoubleMatrix crossProduct(DoubleMatrix a, DoubleMatrix b)
	{
		double[][] aArray = new double[][] {{0, -a.get(2), a.get(1)},
											{a.get(2), 0, -a.get(0)},
											{-a.get(1), a.get(0), 0}};
		DoubleMatrix aSkew = new DoubleMatrix(aArray);
		return aSkew.mmul(b);
	}
	
	public DoubleMatrix inverse(DoubleMatrix DM)
	{
		double a = DM.get(0, 0);
		double b = DM.get(0, 1);
		double c = DM.get(0, 2);
		double d = DM.get(1, 0);
		double e = DM.get(1, 1);
		double f = DM.get(1, 2);
		double g = DM.get(2, 0);
		double h = DM.get(2, 1);
		double i = DM.get(2, 2);
		double det = (a*((e*i) - (f*h))) - (b*((i*d) - (f*g))) + (c*((d*h) - (e*g)));
		double OneOverDet = 1/det;
		
		double A = (e*i) - (f*h);
		double B = -((d*i) - (f*g));
		double C = ((d*h) - (e*g));
		double D = -((b*i) - (c*h));
		double E = ((a*i) - (c*g));
		double F = -((a*h) - (b*g));
		double G = ((b*f) - (c*e));
		double H = -((a*f) - (c*d));
		double I = ((a*e) - (b*d));
		
		double[][] arrayTranspose = new double[][] {{A, D, G},
													{B, E, H},
													{C, F, I}};
		DoubleMatrix Transpose = new DoubleMatrix(arrayTranspose);
		
		DoubleMatrix inverseResult = Transpose.mmul(OneOverDet);
		
		return inverseResult;
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		String modelFile = args[0];
		String commandFile = args[1];
		
		HW5 hw4 = new HW5(commandFile , modelFile);
		hw4.ray_trace();
	}
}
