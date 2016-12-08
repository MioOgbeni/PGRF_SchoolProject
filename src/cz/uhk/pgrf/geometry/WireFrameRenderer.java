package cz.uhk.pgrf.geometry;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import cz.uhk.pgrf.canvas.LineRenderer;
import cz.uhk.pgrf.transforms.Mat4;
import cz.uhk.pgrf.transforms.Point3D;
import cz.uhk.pgrf.transforms.Vec3D;

public class WireFrameRenderer implements Renderable {
	private BufferedImage img;
	private Mat4 modelMatrix;
	private Mat4 viewMatrix;
	private Mat4 projectionMatrix;
	
	public WireFrameRenderer(BufferedImage img) {
		this.img = img;
	}
	
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
	
	@Override
	public void draw(GeometricObject o) {
		LineRenderer line = new LineRenderer(img);
		Mat4 finalmat = modelMatrix.mul(viewMatrix).mul(projectionMatrix);
		
		for(int i = 0; i < o.indexBuffer.size(); i+=2){
			int iA = o.indexBuffer.get(i);
			int iB = o.indexBuffer.get(i+1);
			
			Point3D vA = o.vertexBuffer.get(iA);
			Point3D vB = o.vertexBuffer.get(iB);
			
			vA = vA.mul(finalmat);
			vB = vB.mul(finalmat);	
			
			//Clip (oøezání) podle W
			//TODO: oøezat 
			
			//4D -> 3D, dehomogenizace
			Optional<Vec3D> vTempA = vA.dehomog();
			Optional<Vec3D> vTempB = vB.dehomog();
			
			Vec3D vA3D = null;
			if(vTempA.isPresent()){
				vA3D = vTempA.get();
			}
			
			Vec3D vB3D = null;
			if(vTempB.isPresent()){
				vB3D = vTempB.get();
			}
			
			//Clip ZO
			//3D -> 2D, dehomog
			//ViewPort transformace
			int w = img.getWidth();
			int h = img.getHeight();
			
			int x1 = (((int)(vA3D.getX())+1)*(w-1)/2);
			int y1 = (((int)(1-vA3D.getY()))*(h-1)/2);
			
			int x2 = (((int)(vB3D.getX())+1)*(w-1)/2);
			int y2 = (((int)(1-vB3D.getY()))*(h-1)/2);
			
			line.Draw(x1, y1, x2, y2);
		}	
	}

	@Override
	public void draw(List<GeometricObject> o) {
		for(GeometricObject object : o){
			draw(object);
		}
	}
	

}
