package cz.uhk.pgrf.canvas;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Tøída pro kreslení kruhové výseèe.
 * 
 * @author Tomáš Novák
 * @version 2016
 */
public class CircleRenderer {

	private BufferedImage img;
	private int color;
	private List<Point> fullcircle = new ArrayList<Point>();
	private LineRenderer cir;
	private double angle;
	int xA = 0;
	int yA = 0;
	int xB = 0;
	int yB = 0;
	double krajX;
	double krajY;
	int mysX;
	int mysY;

	public CircleRenderer(BufferedImage img) {
		this(img, 0xffffff);
	}

	public CircleRenderer(BufferedImage img, int color) {
		this.img = img;
		this.color = color;
		cir = new LineRenderer(this.img, this.color);
	}

	/**
	 * Vykreslení kruhu
	 */
	public void Draw() {
		krajX = xB - xA;
		krajY = yB - yA;
		mysX = mysX - xA;
		mysY = mysY - yA;

		// Kontorla, kde kreslit výseè
		int kontrola = (int) ((krajX * mysY) - (krajY * mysX));
		if (kontrola >= 0) {
			angle = 2 * Math.PI - angle;
		}

		// Výpoèty polomìru, pootoèení, úhlu o který pootoèit
		double r = (int) Math.sqrt(Math.abs(krajX * krajX) + Math.abs(krajY * krajY));
		double alfa = 10 / r;

		int n = (int) ((2 * Math.PI) / alfa);

		double dcos = Math.cos(alfa);
		double dsin = Math.sin(alfa);
		double controlangle = 0;

		// Zadání prvního bodu a dalších po sobì jdoucích bodù
		fullcircle.add(new Point(((int) krajX + xA), ((int) krajY + yA)));
		for (int k = 0; k <= n; k++) {
			controlangle = controlangle + alfa;
			double xn = (krajX * dcos) + (krajY * dsin);
			double yn = -(krajX * dsin) + (krajY * dcos);
			if (controlangle <= angle) {
				fullcircle.add(new Point(((int) (Math.round(xn) + xA)), ((int) (Math.round(yn) + yA))));
			}
			krajX = (int) Math.round(xn);
			krajY = (int) Math.round(yn);
		}

		// Spojení bodù vykreslenými èarami pomocí LineRendereru
		for (int i = 0; i < fullcircle.size() - 1; i++) {
			cir.Draw((int) fullcircle.get(i).getX(), (int) fullcircle.get(i).getY(), (int) fullcircle.get(i + 1).getX(),
					(int) fullcircle.get(i + 1).getY());
		}
		fullcircle.clear();
	}

	/**
	 * Nastavení souøadnic støedu
	 * 
	 * @param xA
	 * @param yA
	 */
	public void setX(int xA, int yA) {
		this.xA = xA;
		this.yA = yA;
	}

	/**
	 * Nastavení souøadnic kraje
	 * 
	 * @param xB
	 * @param yB
	 */
	public void setY(int xB, int yB) {
		this.xB = xB;
		this.yB = yB;
	}

	/**
	 * Nastavení úhlu pro výpoèet (podle pozice kurzoru)
	 * 
	 * @param x
	 * @param y
	 */
	public void setAngle(int x, int y) {
		this.mysX = x;
		this.mysY = y;
		double cosAngle = (((krajX * (x - xA)) + (krajY * (y - yA)))
				/ (Math.sqrt(Math.pow(krajX, 2) + Math.pow(krajY, 2))
						* Math.sqrt(Math.pow((x - xA), 2) + Math.pow((y - yA), 2))));
		angle = Math.acos(cosAngle);
	}

	// Pøetížená metoda pro nastavení úhlu
	public void setAngle(int x) {
		this.angle = x;
	}
}
