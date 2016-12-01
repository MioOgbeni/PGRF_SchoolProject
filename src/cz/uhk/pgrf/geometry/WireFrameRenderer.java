package cz.uhk.pgrf.geometry;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cz.uhk.pgrf.transforms.Mat4;
import cz.uhk.pgrf.transforms.Point3D;
import cz.uhk.pgrf.transforms.Vec3D;

public class WireFrameRenderer implements Renderable {
	private BufferedImage img;
	private Mat4 modelMatrix;
	private Mat4 viewMatrix;
	private Mat4 projectionMatrix;
	
	@Override
	public void setBufferedImg(BufferedImage img) {
		this.img = img;
	}

	@Override
	public void setModel(Mat4 matrix) {
		modelMatrix = matrix;
		
	}

	@Override
	public void setView(Mat4 matrix) {
		viewMatrix = matrix;
		
	}

	@Override
	public void setProjection(Mat4 matrix) {
		projectionMatrix = matrix;
		
	}

	public ArrayList<Point3D> transformPoints(ArrayList<Point3D> points, Mat4 matrix){
		ArrayList<Point3D> hold = new ArrayList<>();
		for( Point3D point : points){
			hold.add(point.mul(matrix));
		}
		return hold;
	}
	
	
	@Override
	public void draw(GeometricObject o) {
		ArrayList<Point3D> points = o.getPoint();
		
		//aplikujeme transformace
			//Model
			//View
			//Projection
		transformPoints(points, modelMatrix);
		transformPoints(points, viewMatrix);
		transformPoints(points, projectionMatrix);
		
		//Clip (oøezání) podle W
				//4D -> 3D, dehomogenizace
		ArrayList<Vec3D> vectors = new ArrayList<>();
		for(Point3D p : points){
			vectors.add(p.dehomog().get());
		}
		
		//Clip ZO
		ArrayList<Vec3D> vectorsCliped = new ArrayList<>();
		for(int i = 0; i<vectors.size(); i++){
			Vec3D v = vectors.get(i);
			if ((Math.min((Math.min(v.getX(),v.getX()),C.x) > 1.0f)||(Math.max(Math.max(A.x,B.x),C.x) < -1.0f)) vectorsCliped.add(v);		
		}
		
		
		
		
		//3D -> 2D, dehomog
		//ViewPort transformace
		//rasterizace
		
	}

	@Override
	public void draw(List<GeometricObject> o) {
		for(GeometricObject object : o){
			draw(object);
		}
	}
	

}
