package cz.uhk.pgrf.geometry;

import java.util.ArrayList;
import java.util.Arrays;

import cz.uhk.pgrf.transforms.Point3D;

public class Cube extends GeometricObject{
	
	public Cube() {
		Integer[] pom = new Integer []{0,1,1,2,2,3,3,0,4,5,5,6,6,7,7,4,0,4,1,5,2,6,3,7};
		ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(pom));
		indexBuffer = integers;
		
		vertexBuffer.add(new Point3D(0,0,0));
		vertexBuffer.add(new Point3D(1,0,0));
		vertexBuffer.add(new Point3D(1,1,0));
		vertexBuffer.add(new Point3D(0,1,0));
		vertexBuffer.add(new Point3D(0,0,1));
		vertexBuffer.add(new Point3D(1,0,1));
		vertexBuffer.add(new Point3D(1,1,1));
		vertexBuffer.add(new Point3D(0,1,1));
		
		
		
	}
}
