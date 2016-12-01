package cz.uhk.pgrf.canvas;

import java.util.List;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Tøída vykreslující N-úhelník.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class PolygonRenderer {

	private BufferedImage img;
	private int color;
	private LineRenderer a;

	public PolygonRenderer(BufferedImage img) {
		this(img, 0xffffff);
	}

	public PolygonRenderer(BufferedImage img, int color) {
		this.img = img;
		this.color = color;
		a = new LineRenderer(this.img, this.color);
	}

	/**
	 * Vykreslení spojených èar do N-úhelníku pomocí LineRendereru
	 * 
	 * @param points
	 */
	public void Draw(List<Point> points) {
		for (int i = 0; i < points.size() - 1; i++) {
			a.Draw((int) points.get(i).getX(), (int) points.get(i).getY(), (int) points.get(i + 1).getX(),
					(int) points.get(i + 1).getY());

			if (points.size() > 1) {
				a.Draw((int) points.get(0).getX(), (int) points.get(0).getY(),
						(int) points.get(points.size() - 1).getX(), (int) points.get(points.size() - 1).getY());
			}

		}
	}

	public int getColor() {
		return color;
	}
}
