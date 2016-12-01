package cz.uhk.pgrf.canvas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import java.awt.Point;

/**
 * Tøída pro kreslení na plátno, interface plátna.
 * 
 * @author Tomáš Novák
 * @version 2016
 */

public class Canvas {

	private static int CLEAR_COLOR = 0xff2f2f2f;
	private static Color COLOR_OBJECTS = new Color(204, 255, 255);
	private static Color COLOR_FILLING = new Color(204, 204, 204);
	private JFrame frame;
	private JPanel panel;
	private BufferedImage img;
	private JCheckBox Jfilling;

	private int xA;
	private int yA;
	private int xB;
	private int yB;
	private int CoKreslim = 0;
	private int CoPlnim = 0;
	private boolean vysec;

	LineRenderer lr;
	CircleRenderer cr;
	PolygonRenderer poly;
	AALineRenderer AAlr;
	SeedFiller sf;
	SeedFillerPattern sfp;
	ScanLineFiller slf;

	private List<Point> points = new ArrayList<Point>();
	private List<Point> lines = new ArrayList<Point>();

	public Canvas(int width, int height) {
		/**
		 * Nastavení okna
		 */
		frame = new JFrame();
		frame.setTitle("UHK FIM PGRF : Canvas");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		/**
		 * Vytvoøení toolbaru
		 */
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		JToolBar tb = new JToolBar();
		frame.add(tb, BorderLayout.NORTH);
		vytvorTlacitka(tb);

		/**
		 * Vytvoøení instancí graf.objektù
		 */
		lr = new LineRenderer(img, 0xffff0000);
		poly = new PolygonRenderer(img, 0xff00ff00);
		cr = new CircleRenderer(img, 0xffffffff);
		AAlr = new AALineRenderer(img);
		sf = new SeedFiller(img, 0xff0000ff);
		sfp = new SeedFillerPattern(img);
		slf = new ScanLineFiller(img, 0xff6600cc);

		/**
		 * Kreslení
		 */
		MouseAdapter mouse = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if (Jfilling.isSelected()) {
					switch (CoKreslim) {
					case 0:
						switch (CoPlnim) {
						case 0:
							sf.StartFill(e.getX(), e.getY());
							break;
						case 1:
							sfp.StartFill(e.getX(), e.getY());
							break;
						case 2:

							break;
						}
						break;
					case 1:
						switch (CoPlnim) {
						case 0:
							sf.StartFill(e.getX(), e.getY());
							break;
						case 1:
							sfp.StartFill(e.getX(), e.getY());
							break;
						case 2:

							break;
						}
						break;
					}
				} else {
					xA = e.getX();
					yA = e.getY();
					switch (CoKreslim) {
					case 0:
						lines.add(new Point(xA, yA));
						break;
					case 1:
						points.add(new Point(xA, yA));
						poly.Draw(points);
						break;
					case 2:
						if (vysec == false) {
							cr.setAngle(0);
							cr.setX(xA, yA);
							break;
						} else {
							break;
						}
					case 3:
						break;
					}
				}
				present();
			}

			public void mouseReleased(MouseEvent e) {
				if (Jfilling.isSelected()) {

				} else {
					clear(CLEAR_COLOR);
					xB = e.getX();
					yB = e.getY();
					switch (CoKreslim) {
					case 0:
						lines.add(new Point(xB, yB));
						lr.Draw(xA, yA, xB, yB);
						lr.ReDraw(lines);
						break;
					case 1:
						points.add(new Point(xB, yB));
						poly.Draw(points);
						break;
					case 2:
						if (vysec == false) {
							cr.setY(xB, yB);
							cr.Draw();
							vysec = true;
							break;
						} else {
							vysec = false;
							cr.setAngle(e.getX(), e.getY());
							cr.Draw();
							present();
							break;
						}
					case 3:
						AAlr.Draw(xA, yA, xB, yB);
						break;
					}
				}
				present();
			}

			public void mouseDragged(MouseEvent e) {
				if (Jfilling.isSelected()) {

				} else {
					clear(CLEAR_COLOR);
					xB = e.getX();
					yB = e.getY();
					switch (CoKreslim) {
					case 0:
						lr.Draw(xA, yA, xB, yB);
						lr.ReDraw(lines);
						break;
					case 1:
						lr.Draw(xA, yA, xB, yB);
						poly.Draw(points);
						break;
					case 2:
						if (vysec == false) {
							lr.Draw(xA, yA, xB, yB);
							cr.setY(xB, yB);
							cr.Draw();
							break;
						} else {
							break;
						}

					case 3:
						AAlr.Draw(xA, yA, xB, yB);
						break;
					}
				}
				present();
			}

			public void mouseMoved(MouseEvent e) {
				if (Jfilling.isSelected()) {

				} else {
					switch (CoKreslim) {
					case 2:
						if (vysec == true) {
							clear(CLEAR_COLOR);
							lr.Draw(xA, yA, xB, yB);
							cr.setAngle(e.getX(), e.getY());
							cr.Draw();
							present();
							break;
						} else {
							break;
						}
					}
				}
			}
		};
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mouse);
	}

	/**
	 * Vytváøení interface (GUI)
	 * 
	 * @param kontejner
	 */
	private void vytvorTlacitka(JComponent kontejner) {
		JButton btnVycistiPlatno = new JButton("Vyèisti plátno");
		kontejner.add(btnVycistiPlatno);
		btnVycistiPlatno.addActionListener(e -> vycistiPlatno());

		JRadioButton Jline = new JRadioButton("Èára");
		Jline.setSelected(true);
		Jline.setBackground(COLOR_OBJECTS);

		JRadioButton Jpolygon = new JRadioButton("N-úhelník");
		Jpolygon.setBackground(COLOR_OBJECTS);

		JRadioButton Jcircle = new JRadioButton("Kruhová výseè");
		Jcircle.setBackground(COLOR_OBJECTS);

		JRadioButton Jaaline = new JRadioButton("AA Èára");
		Jaaline.setBackground(COLOR_OBJECTS);

		Jfilling = new JCheckBox("Filling");
		Jfilling.setBackground(COLOR_FILLING);

		JRadioButton Jseedfill = new JRadioButton("Seedfill");
		Jseedfill.setSelected(true);
		Jseedfill.setBackground(COLOR_FILLING);

		JRadioButton Jseedfillpattern = new JRadioButton("SF pattern");
		Jseedfillpattern.setBackground(COLOR_FILLING);
		Jseedfill.setBackground(COLOR_FILLING);

		JButton btnJscanline = new JButton("Scanline");
		btnJscanline.addActionListener(e -> doSLine());
		btnJscanline.setBackground(COLOR_FILLING);

		ButtonGroup group = new ButtonGroup();
		group.add(Jline);
		group.add(Jpolygon);
		group.add(Jcircle);
		group.add(Jaaline);

		ButtonGroup seedgroup = new ButtonGroup();
		seedgroup.add(Jseedfill);
		seedgroup.add(Jseedfillpattern);

		kontejner.add(Jline);
		kontejner.add(Jpolygon);
		kontejner.add(Jcircle);
		kontejner.add(Jaaline);

		kontejner.add(Jfilling);
		kontejner.add(Jseedfill);
		kontejner.add(Jseedfillpattern);
		kontejner.add(btnJscanline);

		Jline.addActionListener(e -> vyberLine());
		Jpolygon.addActionListener(e -> vyberPolygon());
		Jcircle.addActionListener(e -> vyberCircle());
		Jaaline.addActionListener(e -> vyberAALine());
		Jseedfill.addActionListener(e -> vyberSFill());
		Jseedfillpattern.addActionListener(e -> vyberSFillP());
	}

	/**
	 * Vybírání právì kreslených graf.objektù
	 */
	private void vyberLine() {
		CoKreslim = 0;
		vykresliPlatno();
	}

	private void vyberPolygon() {
		CoKreslim = 1;
		vykresliPlatno();
	}

	private void vyberCircle() {
		CoKreslim = 2;
		vykresliPlatno();
	}

	private void vyberAALine() {
		CoKreslim = 3;
		vykresliPlatno();
	}

	private void vyberSFill() {
		CoPlnim = 0;
		vykresliPlatno();
	}

	private void vyberSFillP() {
		CoPlnim = 1;
		vykresliPlatno();
	}

	/**
	 * Volání vyplnìní Scanline
	 */
	private void doSLine() {
		if (points == null || points.isEmpty()) {
			points.add(new Point(0, 0));
			points.add(new Point(0, img.getHeight() - 1));
			points.add(new Point(img.getWidth() - 1, img.getHeight() - 1));
			points.add(new Point(img.getWidth() - 1, 0));
			slf.Fill(points);
			points.clear();
		} else {
			slf.Fill(points);
			poly.Draw(points);
		}
		present();
	}

	/**
	 * Vyèištìní listù a plátna
	 */
	private void vycistiPlatno() {
		switch (CoKreslim) {
		case 0:
			lines.clear();
			clear(CLEAR_COLOR);
			present();
			break;
		case 1:
			points.clear();
			clear(CLEAR_COLOR);
			present();
			break;
		case 2:
			clear(CLEAR_COLOR);
			present();
			break;
		case 3:
			clear(CLEAR_COLOR);
			present();
			break;
		}
	}

	private void vykresliPlatno() {
		switch (CoKreslim) {
		case 0:
			clear(CLEAR_COLOR);
			lr.ReDraw(lines);
			xA = 0;
			yA = 0;
			xB = 0;
			yB = 0;
			present();
			break;
		case 1:
			clear(CLEAR_COLOR);
			poly.Draw(points);
			xA = 0;
			yA = 0;
			xB = 0;
			yB = 0;
			present();
			break;
		case 2:
			clear(CLEAR_COLOR);
			xA = 0;
			yA = 0;
			xB = 0;
			yB = 0;
			vysec = false;
			present();
			break;
		case 3:
			clear(CLEAR_COLOR);
			xA = 0;
			yA = 0;
			xB = 0;
			yB = 0;
			present();
			break;
		}
	}

	/**
	 * Vyèištìní plátna
	 * 
	 * @param color
	 */
	public void clear(int color) {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(color));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	/**
	 * Refresh, vykreslení prázdného plátna a startovací metoda
	 */
	public void present() {
		if (panel.getGraphics() != null)
			panel.getGraphics().drawImage(img, 0, 0, null);
	}

	public void start() {
		clear(CLEAR_COLOR);
		present();
	}

	public static void main(String[] args) {
		Canvas canvas = new Canvas(800, 600);
		SwingUtilities.invokeLater(() -> {
			SwingUtilities.invokeLater(() -> {
				SwingUtilities.invokeLater(() -> {
					SwingUtilities.invokeLater(() -> {
						canvas.start();
					});
				});
			});
		});
	}
}
