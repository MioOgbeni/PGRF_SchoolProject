package cz.uhk.pgrf.canvas;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * T��da vykresluj�c� non-AA ��ru.
 * 
 * @author Tom� Nov�k
 * @version 2016
 */

public class LineRenderer {

	private BufferedImage img;
	private int color;

	public LineRenderer(BufferedImage img) {
		this(img, 0xffffff);
	}

	public LineRenderer(BufferedImage img, int color) {
		this.img = img;
		this.color = color;
	}

	/**
	 * Nastaven� barvy
	 * 
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Kreslen� ��ry
	 * 
	 * @param xA
	 * @param yA
	 * @param xB
	 * @param yB
	 */
	public void Draw(int xA, int yA, int xB, int yB) {

		// vypocet pro smernici
		double dx = xB - xA;
		double dy = yB - yA;

		if (Math.abs(yB - yA) <= Math.abs(xB - xA)) {

			// Pro jeden bod
			if ((xA == xB) && (yA == yB)) {
				img.setRGB(xA, yA, color);
			} else {

				// prohozeni vodicich os
				if (xB < xA) {
					int pomoc = xB;
					xB = xA;
					xA = pomoc;

					pomoc = yB;
					yB = yA;
					yA = pomoc;
				}

				// vypocet
				double k = (double) dy / dx;
				int int_y;
				double y = (double) yA;

				// tisk img
				for (int x = xA; x <= xB; x++) {
					int_y = (int) Math.round(y);
					img.setRGB(x, int_y, color);
					y += k;
				}
			}
		} else {

			// prohozeni vodicich os
			if (yB < yA) {
				int pomoc = xB;
				xB = xA;
				xA = pomoc;

				pomoc = yB;
				yB = yA;
				yA = pomoc;
			}

			// vypocet
			double k = (double) dx / dy;
			int int_x;
			double x = (double) xA;

			// tisk img
			for (int y = yA; y <= yB; y++) {
				int_x = (int) Math.round(x);
				img.setRGB(int_x, y, color);
				x += k;
			}
		}
	}

	/**
	 * P�ekreslen� p�ede�l�ch �ar
	 * 
	 * @param lines
	 */
	public void ReDraw(List<Point> lines) {
		for (int i = 0; i < lines.size() - 1; i = i + 2) {
			Draw((int) lines.get(i).getX(), (int) lines.get(i).getY(), (int) lines.get(i + 1).getX(),
					(int) lines.get(i + 1).getY());
		}
	}

	public int getColor() {
		return color;
	}
}
