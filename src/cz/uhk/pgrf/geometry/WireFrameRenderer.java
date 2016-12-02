package cz.uhk.pgrf.geometry;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import cz.uhk.pgrf.canvas.LineRenderer;
import cz.uhk.pgrf.transforms.Mat4;
import cz.uhk.pgrf.transforms.Point3D;
import cz.uhk.pgrf.transforms.Vec2D;
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
			if ((v.getX() < 1.0f)|| (v.getY() < 1.0f) || (v.getZ() < 1.0f) || (v.getX() > -1.0f)|| (v.getY() > -1.0f) || (v.getZ() > -1.0f)) vectorsCliped.add(v);		
		}
		
		//3D -> 2D, dehomog
		ArrayList<Vec2D> vectors2D = new ArrayList<>();
		ArrayList<Double> vectors2D_Z = new ArrayList<>();
		for(int i = 0; i<vectorsCliped.size(); i++){
			Vec3D v = vectorsCliped.get(i);
			vectors2D_Z.add(v.getZ());
			vectors2D.add(v.ignoreZ());
		}
		
		//ViewPort transformace
		ArrayList<Vec2D> vectors2D_view = new ArrayList<>();
			//upravovací
			Vec2D A1 = new Vec2D(1,-1);
			Vec2D A2 = new Vec2D(1,1);
			Vec2D A3 = new Vec2D((img.getWidth()-1)/2,(img.getHeight()-1)/2);
		for(int i = 0; i<vectors2D.size(); i++){
			Vec2D v = vectors2D.get(i);
			v = v.mul(A1);
			v = v.add(A2);
			v = v.mul(A3);
			vectors2D_view.add(v);
		}
		//rasterizace
		LineRenderer line = new LineRenderer(img);
		for(int i = 0; i<vectors2D_view.size()-1; i++){
			Vec2D v = vectors2D_view.get(i);
			Vec2D v_1 = vectors2D_view.get(i+1);
			line.Draw((int)v.getX(), (int)v.getY(), (int)v_1.getX(), (int)v_1.getY());
		}
		
	}

	@Override
	public void draw(List<GeometricObject> o) {
		for(GeometricObject object : o){
			draw(object);
		}
	}
	

}
