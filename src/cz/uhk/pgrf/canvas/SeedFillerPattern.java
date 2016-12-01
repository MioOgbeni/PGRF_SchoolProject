package cz.uhk.pgrf.canvas;

import java.awt.image.BufferedImage;

/**
 * Tøída pro vyplnìní Seedfillem s patternem.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class SeedFillerPattern extends Filler {

	int cB; // barva pozadí

	// nastavení patternu
	int[][] pattern = { {0, 0, 0, 0, 0, 0, 0, 0, 0},
						{0, 1, 1, 0, 0, 0, 1, 1, 0}, 
						{0, 1, 1, 0, 0, 0, 1, 1, 0}, 
						{0, 0, 0, 1, 0, 1, 0, 0, 0}, 
						{0, 0, 0, 0, 1, 0, 0, 0, 0}, 
						{0, 0, 0, 1, 0, 1, 0, 0, 0},
						{0, 1, 1, 0, 0, 0, 1, 1, 0},
						{0, 1, 1, 0, 0, 0, 1, 1, 0},
						{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	int color1 = 0xff1aff8c;
	int color0 = 0xffff9933;

	// konstruktor
	public SeedFillerPattern(BufferedImage img) {
		super(img);
	}

	// poèáteèní seed
	public void StartFill(int X, int Y) {
		cB = img.getRGB(X, Y);
		Fill(X, Y);
	}

	// stejné zaplavování jako u "SeedFiler.java"
	public void Fill(int X, int Y) {
		if ((X > 0) && (Y > 0) && (X < img.getWidth() - 1) && (Y < img.getHeight() - 1)) {
			if ((img.getRGB(X, Y) == cB) && ((img.getRGB(X, Y) != color1) || (img.getRGB(X, Y) != color0))) {

				// urèení který pixel patternu vykreslit
				if (pattern[X / 2 % 8][Y / 2 % 8] == 1) {
					img.setRGB(X, Y, color1);
				} else {
					img.setRGB(X, Y, color0);
				}
				Fill(X + 1, Y);
				Fill(X - 1, Y);
				Fill(X, Y + 1);
				Fill(X, Y - 1);
			}
		}
	}
}
