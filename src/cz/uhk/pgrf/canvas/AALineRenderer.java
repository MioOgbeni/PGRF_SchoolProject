package cz.uhk.pgrf.canvas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Tøída pro kreslení vyhlazené (AA) èáry.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class AALineRenderer {

	private BufferedImage img;
	private List<Integer> colors = new ArrayList<Integer>();

	public AALineRenderer(BufferedImage img) {
		this.img = img;
		colors.add(0x1Aff0000);
		colors.add(0x4Dff0000);
		colors.add(0x80ff0000);
	}

	/**
	 * Pomocné výpoèty
	 * 
	 */
	private int zaokrouleno(double x) {
		return (int) Math.round(x + 0.5);
	}

	private double fpart(double x) {
		if (x < 0) {
			return (1 - (x - Math.floor(x)));
		}
		return (x - Math.floor(x));
	}

	private double rfpart(double x) {
		return 1 - fpart(x);
	}

	/**
	 * Kreslení AALine
	 * 
	 * @param xA
	 * @param yA
	 * @param xB
	 * @param yB
	 */
	public void Draw(int xA, int yA, int xB, int yB) {
		boolean steep = Math.abs(yB - yA) > Math.abs(xB - xA);

		// Prohození vodících os
		if (steep) {
			int pomoc = xA;
			xA = yA;
			yA = pomoc;

			pomoc = xB;
			xB = yB;
			yB = pomoc;
		}

		if (xA > xB) {
			int pomoc = xA;
			xA = xB;
			xB = pomoc;

			pomoc = yA;
			yA = yB;
			yB = pomoc;
		}

		// Výpoèty
		double dx = xB - xA;
		double dy = yB - yA;
		double gradient = dy / dx;

		int xend = zaokrouleno(xA);
		int yend = (int) (yA + gradient * (xend - xA));
		int xpxl1 = xend;
		double intery = yend + gradient;

		xend = zaokrouleno(xB);
		yend = (int) (yB + gradient * (xend - xB));
		int xpxl2 = xend;

		// Kreslení do daných kvadrantù
		if (steep) {
			for (int x = (int) (xpxl1 + 1); x <= xpxl2 - 1; x++) {
				drawing((int) (intery), x, rfpart(intery));
				drawing((int) (intery) + 1, x, fpart(intery));
				intery = intery + gradient;
			}
		} else {
			for (int x = (int) (xpxl1 + 1); x <= xpxl2 - 1; x++) {
				drawing(x, (int) (intery), rfpart(intery));
				drawing(x, (int) (intery) + 1, fpart(intery));
				intery = intery + gradient;
			}
		}
	}

	/**
	 * Vykreslení pixelù s alfa kanálem
	 * 
	 * @param x
	 * @param y
	 * @param c
	 */
	private void drawing(int x, int y, double c) {
		double alfa = Math.round(c * 100.0) / 100.0;
		int color = 0;
		if (0 <= alfa && alfa <= 0.3) {
			color = colors.get(0);
		} else if (0.3 < alfa && alfa <= 0.7) {
			color = colors.get(1);
		} else if (0.7 < alfa && alfa <= 1) {
			color = colors.get(2);
		}
		img.setRGB(x, y, color);
	}
}
