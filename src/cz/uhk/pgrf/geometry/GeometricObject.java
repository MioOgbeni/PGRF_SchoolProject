package cz.uhk.pgrf.geometry;

import java.util.ArrayList;
import java.util.List;

import cz.uhk.pgrf.transforms.Point3D;

public abstract class GeometricObject {
	protected ArrayList<Point3D> vertexBuffer;
	protected List<Integer> indexBuffer;
	
	public ArrayList<Point3D> getPoint(){
		return vertexBuffer;
		
	}
}
