package cz.uhk.pgrf.canvas;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tøída pro vyplnìní pomocí Scanline.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class ScanLineFiller extends Filler {

	// konstruktory
	public ScanLineFiller(BufferedImage img) {
		super(img);
	}

	public ScanLineFiller(BufferedImage img, int color) {
		super(img, color);
	}

	// vyplòovací metoda
	public void Fill(List<Point> points) {

		// prvotní inicializace min a max rozsahu vyplòování
		int ymin = (int) points.get(0).getY();
		int ymax = -1;

		List<SLine> lines = new ArrayList<>();

		// nové nastavení min a max
		// utvoøení hran z bodù
		// jejich odstranìní v pøípadì vodorovnosti a zmìna smìru (aby byl u
		// všech stejný)
		for (int i = 0; i < points.size(); i++) {
			if (ymin > points.get(i).getY())
				ymin = (int) points.get(i).getY();
			if (ymax < points.get(i).getY())
				ymax = (int) points.get(i).getY();

			SLine temp = new SLine(points.get(i), points.get((i + 1) % points.size()));

			if (!temp.isHorizontal()) {
				temp.changeDirection();
				lines.add(temp);
			}
		}

		List<Integer> prusecik = new ArrayList<>();

		// zjištìní prùseèíkù
		for (int y = ymin; y < ymax; y++) {
			for (SLine sl : lines) {
				if (sl.isIntersection(y)) {
					prusecik.add(sl.getIntersection(y));
				}
			}

			// setøízení pom podle x
			Collections.sort(prusecik);

			// pospojování liché -> sudé
			LineRenderer a = new LineRenderer(this.img, this.color);
			for (int i = 0; i < prusecik.size(); i += 2) {
				a.Draw(prusecik.get(i), y, prusecik.get(i + 1), y);
			}
			prusecik.clear();
		}
	}
}