package cz.uhk.pgrf.geometry;

import java.awt.image.BufferedImage;
import java.util.List;

import cz.uhk.pgrf.transforms.Mat4;

public interface Renderable {
	
	void setBufferedImg(BufferedImage img);
	
	void setModel(Mat4 matrix);
	void setView(Mat4 matrix);
	void setProjection(Mat4 matrix);
	
	void draw(GeometricObject objekt);
	void draw(List<GeometricObject> objekt);
	
	
}
