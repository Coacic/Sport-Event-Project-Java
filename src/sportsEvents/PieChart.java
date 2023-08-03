package sportsEvents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

class Slice {
	double value;
	Color color;
	String name;
	static int onlyOne = 0;
	static String cnt = "";

	public Slice(double value, String name, Color color) {
		this.value = value;
		this.name = name;
		this.color = color;
	}
}

@SuppressWarnings("serial")
class PieChart extends JComponent {
	ArrayList<Slice> slices = new ArrayList<>();

	Color randColor() {
		return new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
	}

	Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.DARK_GRAY, Color.PINK, Color.YELLOW, Color.CYAN,
			Color.ORANGE, Color.MAGENTA, Color.LIGHT_GRAY };

	@SuppressWarnings("unlikely-arg-type")
	PieChart(Countries countries, ArrayList<Integer> percentPerCountries) {
		List<Color> colorList = Arrays.asList(colors);
		Collections.shuffle(colorList);
		colorList.toArray(colors);
		// Slice other = new Slice(0, "Other", colors[9]);
		Slice other = new Slice(0, "Other", randColor());
		ArrayList<Character> flags = new ArrayList<>(Collections.nCopies(countries.getSize(), '0'));
		for (int k = 0; k < 9; k++) {
			int position = 0;
			int value = 0;
			int found = 0;
			for (int i = 0; i < percentPerCountries.size(); i++) {
				if (value < percentPerCountries.get(i) && flags.get(i) == '0') {
					found = 1;
					value = percentPerCountries.get(i);
					position = i;
				}
			}
			if (found == 1) {
				flags.set(position, '1');
				if (!percentPerCountries.contains(countries.getCountry(position))) {
					slices.add(new Slice(value, countries.getCountry(position), randColor()));

				}
			}
		}
//		int count = 0;
//		for (Integer p : percentPerCountries) {
//			if (p != 0) {
//				count++;
//			}
//		}
		if (other.value > 5) {
			slices.add(other);
		}
	}

	public void paint(Graphics g) {
		drawPie((Graphics2D) g, new Rectangle(400, 400), slices);
	}

	void drawPie(Graphics2D g, Rectangle area, ArrayList<Slice> slices) {
		double total = 0.0D;
		for (int i = 0; i < slices.size(); i++) {
			total += slices.get(i).value;
		}

		double curValue = 0.0D;
		double startAngle = 0;
		double angle;
		ArrayList<Double> angles = new ArrayList<>();
		int a = 150;
		int b = 150;
		int r = 4 * 256 / 5;
//		int n = 256;
		a = getWidth() / 2;
		b = getHeight() / 2;
		a = 270;
		b = 300;
		int m = Math.min(a, b);
		r = 4 * m / 5;
		r = 240;

		for (int i = 0; i < slices.size(); i++) {
			startAngle = (curValue * 360 / total);
			double arcAngle = (slices.get(i).value * 360 / total) + 1;
			if (Slice.onlyOne == 1) {
				arcAngle = 360;
			}
			if (i > 0) {
				angle = (startAngle + arcAngle / 2);
			} else {
				angle = ((startAngle + arcAngle) / 2);
			}
			angles.add(angle);
			g.setColor(Color.BLACK);
			g.setColor(slices.get(i).color);
			g.fillArc(100, 100, area.width, area.height, (int) startAngle, (int) arcAngle);
			curValue += slices.get(i).value;
		}
		int i = 0;
		g.setColor(Color.BLACK);
		for (Double db : angles) {
			int x = (int) Math.round(a + r * Math.cos(Math.toRadians(-db)));
			int y = (int) Math.round(b + r * Math.sin(Math.toRadians(-db)));
			g.drawString(slices.get(i).name, x, y);
			i++;
		}
	}
}