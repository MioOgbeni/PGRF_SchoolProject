package cz.uhk.pgrf.canvas;

import java.awt.Point;

/**
 * Tøída hrany n-uhelníku, zodpovìdná za kroky nutné ke Scanline algoritmu.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class SLine {

	private Point a;
	private Point b;

	// konstruktor
	public SLine(Point a, Point b) {
		this.a = a;
		this.b = b;
	}

	// zmìna smìru
	public void changeDirection() {
		if (a.getY() > b.getY()) {
			Point pomoc = a;
			a = b;
			b = pomoc;
		}
	}

	// zjištìní zda mùže existovat prùseèík, zárovìò podmínka na zkrácení
	public boolean isIntersection(int y) {
		return (y >= a.getY() && y < b.getY());
	}

	// urèení prùseèíku v Y
	public int getIntersection(int y) {
		float dx = (float) (b.getX() - a.getX());
		float dy = (float) (b.getY() - a.getY());
		float k = dx / dy;
		float q = (float) (a.getX() - (k * a.getY()));
		// Obec. rovnice pøímky k urèení prùseèíku
		float x = (k * y) + q;
		return (int) x;
	}

	// zjištìní zda jsou vodorovné
	public boolean isHorizontal() {
		return (a.getY() == b.getY());
	}
}