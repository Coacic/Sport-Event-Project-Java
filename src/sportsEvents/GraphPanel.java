package sportsEvents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GraphPanel extends JPanel {

//	private int width = 800;
//	private int heigth = 400;
	private int padding = 25;
	private int labelPadding = 25;
	private Color lineColor = new Color(44, 102, 230, 180);
//    private Color pointColor = new Color(100, 100, 100, 180);
	private Color pointColor = Color.BLUE;
	private Color gridColor = new Color(200, 200, 200, 200);
//	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 8;
	private int numberYDivisions = 10;
	private List<Double> scores;
//	private static int startYear = 1800;
//	private static int endYear = 2000;
	private static ArrayList<Event> events;
	@SuppressWarnings("unused")
	private static Countries countries;
	@SuppressWarnings("unused")
	private static ArrayList<Character> flags;

	public GraphPanel() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ArrayList<Integer> listOfYears = new ArrayList<Integer>();
		ArrayList<Double> forEachYearAvgHeight = new ArrayList<Double>();
		ArrayList<Double> forEachYearAvgWeight = new ArrayList<Double>();

		events.forEach((eve) -> {
			int year = Integer.parseInt(eve.getDate().split("\\ ")[0]);
			if (!listOfYears.contains(year)) {
				listOfYears.add(year);
			}
		});
		Collections.sort(listOfYears);

		for (int i = 0; i < listOfYears.size(); i++) {
			String year = String.valueOf(listOfYears.get(i));
			int cntW = 0, cntH = 0;
			double height = 0.0, weight= 0.0;
			for (Event eve : events) {
				if (!eve.getDate().contains(year)) {
					if (!eve.isTeam()) {
						if (eve.getAthlete().getWeight().compareTo("NA") != 0) {
							weight += Double.parseDouble(eve.getAthlete().getWeight());
							cntW++;
						}
						if (eve.getAthlete().getHeight().compareTo("NA") != 0) {
							height += Double.parseDouble(eve.getAthlete().getHeight());
							cntH++;
						}
					} else if (eve.isTeam()) {
						for (int j = 0; j < eve.getTeam().getSize(); j++) {
							if (eve.getTeam().getAthlete(j).getHeight().compareTo("NA") != 0) {
								height += Double.parseDouble(eve.getTeam().getAthlete(j).getHeight());
								cntH++;
							}
							if (eve.getTeam().getAthlete(j).getWeight().compareTo("NA") != 0) {
								weight += Double.parseDouble(eve.getTeam().getAthlete(j).getWeight());
								cntW++;
							}
						}
					}
				}
			}
			forEachYearAvgHeight.add((height / cntH));
			forEachYearAvgWeight.add((weight / cntW));
		}

		for (int i = 0; i < listOfYears.size(); i++) {
			System.out.println(listOfYears.get(i) + " - " + " W " + forEachYearAvgWeight.get(i) + " H "
					+ forEachYearAvgHeight.get(i));
		}
		
		scores = forEachYearAvgWeight;
		scores.addAll(forEachYearAvgHeight);
		ArrayList<Double> graphScores = new ArrayList<Double>();
		graphScores.addAll(forEachYearAvgHeight);
		graphScores.addAll(forEachYearAvgWeight);

		double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
		double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < graphScores.size(); i++) {
			int x1 = (int) (i * xScale + padding + labelPadding);
			int y1 = (int) ((getMaxScore() - graphScores.get(i)) * yScale + padding);
			graphPoints.add(new Point(x1, y1));
		}

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding,
				getHeight() - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = pointWidth + padding + labelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
			int y1 = y0;
			if (scores.size() > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int) ((getMinScore()
						+ (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}
		

		// and for x axis
		for (int i = 0; i < scores.size(); i++) {
			if (scores.size() > 1) {
				int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - labelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					int mod = (i % (listOfYears.size()));
					String xLabel = String.valueOf(listOfYears.get(mod));
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}

		// create x and y axes
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding,
				getHeight() - padding - labelPadding);

		g2.setColor(lineColor);
//        g2.setStroke(GRAPH_STROKE);
//        for (int i = 0; i < graphPoints.size() - 1; i++) {
//            int x1 = graphPoints.get(i).x;
//            int y1 = graphPoints.get(i).y;
//            int x2 = graphPoints.get(i + 1).x;
//            int y2 = graphPoints.get(i + 1).y;
//            g2.drawLine(x1, y1, x2, y2);
//        }

//		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
			if(i >= forEachYearAvgHeight.size() - 1) {
				g2.setColor(Color.RED);
			}
		}
	}

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
	private double getMinScore() {
		double minScore = Double.MAX_VALUE;
		for (Double score : scores) {
			minScore = Math.min(minScore, score);
		}
		return minScore;
	}

	private double getMaxScore() {
		double maxScore = Double.MIN_VALUE;
		for (Double score : scores) {
			maxScore = Math.max(maxScore, score);
		}
		return maxScore;
	}

	public void setScores(List<Double> scores) {
		this.scores = scores;
		invalidate();
		this.repaint();
	}

	public List<Double> getScores() {
		return scores;
	}

	public static void createAndShowGui(ArrayList<Event> events, Countries countries, ArrayList<Character> flags) {
		GraphPanel.events = events;
		GraphPanel.countries = countries;
		GraphPanel.flags = flags;
		List<Double> scores = new ArrayList<>();
//        Scanner sc = new Scanner(System.in);
//        startYear = sc.nextInt();
//        sc.nextLine();
//        endYear = sc.nextInt();
//        sc.nextLine();
//		Random random = new Random();
		int maxDataPoints = 40;
//		int maxScore = 10;
		for (int i = 0; i < maxDataPoints; i++) {
//            scores.add((double) random.nextDouble() * maxScore);
//            scores.add((double) i);
			if (!events.get(i).isTeam() && events.get(i).getAthlete().getWeight().compareTo("NA") != 0) {
				scores.add(Double.valueOf(events.get(i).getAthlete().getWeight()));
			}
		}
		GraphPanel mainPanel = new GraphPanel();
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

//    public static void main(String[] args) {
//    	List<Double> list = new ArrayList<Double>();
//    	GraphPanel gp = new GraphPanel(list);
//    	createAndShowGui(null, null, null);
//      SwingUtilities.invokeLater(new Runnable() {
//         public void run() {
//            //createAndShowGui();
//         }
//      });
//   }
}
