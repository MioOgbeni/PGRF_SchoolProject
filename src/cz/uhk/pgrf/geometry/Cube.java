package cz.uhk.pgrf.geometry;

import java.util.ArrayList;
import java.util.Arrays;

public class Cube extends GeometricObject{
	
	public Cube() {
		Integer[] pom = new Integer []{0,1,1,2,2,3,3,0,4,5,5,6,6,7,7,4,0,4,1,5,2,6,3,7};
		ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(pom));
		indexBuffer = integers;
		
		
		
	}
}
