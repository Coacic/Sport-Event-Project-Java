package sportsEvents;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

@SuppressWarnings("serial")
public class Menu extends Frame {

	private class QuitDialog extends Dialog {

		private Button ok = new Button("OK"), cancel = new Button("Cancel");

		public void paint(Graphics g) {
			super.paint(g);
			g.drawString("Are you sure you want to quit?", 20, 70);
		}

		public QuitDialog(Frame owner) {
			super(owner);
			setTitle("Quit");
			setBounds(owner.getX() + owner.getWidth() / 2, owner.getY() + owner.getHeight() / 2, 200, 150);
			setResizable(true);
			setModalityType(ModalityType.APPLICATION_MODAL);
			Panel buttons = new Panel();
			ok.addActionListener((ae) -> {
				if(events.thread != null && events.thread.isAlive())
					events.thread.interrupt();
				if(events.thread != null && events.thread2.isAlive())
					events.thread2.interrupt();
				if(events.thread != null && events.thread3.isAlive())
					events.thread3.interrupt();
				if(events.thread != null && events.thread4.isAlive())
					events.thread4.interrupt();
				System.exit(0);
			});
			cancel.addActionListener((ae) -> {
				QuitDialog.this.dispose();
			});
			buttons.add(ok);
			buttons.add(cancel);
			add(buttons, BorderLayout.SOUTH);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					events.thread.interrupt();
					events.thread2.interrupt();
					events.thread3.interrupt();
					events.thread4.interrupt();
					System.exit(0);
					dispose();

				}
			});
			setVisible(true);
		}

	}

	Menu(Events _events) {
		events = _events;
		setTitle("Quit");
		setBounds(getX() + getWidth() / 2, getY() + getHeight() / 2, 200, 150);
		setResizable(true);
		setLocation(300, 200);
		setTitle("Glavni program");

		populateWindow();

		pack();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new QuitDialog(Menu.this);
			}

		});

		setVisible(true);
	}
	
	private Button button5 = new Button("Get Count Of Competitors");
	private Button button6 = new Button("Get number of different sports");
	private Button button7 = new Button("Get average height of all competitors");
	private Button button8 = new Button("Get average wieght of all competitors");
	private Button button9 = new Button("Get count of different sports for country that won at least one medal");
	private Button button10 = new Button("Get countries that have won gold medals");
	private Button button11 = new Button("Get cities that hosted olympics at least once");
	private Button button12 = new Button("Get teams for given country and games");
	private Button button13 = new Button("Get all the athletes that competed in the pair of games");
	private Button button14 = new Button("Get pair of country athlete that won at least one medal in team and individual");
	Checkbox checkbox0 = new Checkbox("Ni jedan", false);
	Checkbox checkbox1 = new Checkbox("Filter na osnovu sporta", false);
	Checkbox checkbox2 = new Checkbox("Filter na osnovu drzave", false);
	Checkbox checkbox3 = new Checkbox("Filter na osnovu godine", false);
	Checkbox checkbox4 = new Checkbox("Filter na osnovu tima ili sportiste", false);
	Checkbox checkbox5 = new Checkbox("Filter na osnovu medalje", false);
	TextField txtfield1 = new TextField(15);
	TextField txtfield2 = new TextField(15);
	TextField txtfield3 = new TextField(15);
	TextField txtfield4 = new TextField(15);
	TextField txtfield5 = new TextField(15);
	TextField txtfield6 = new TextField(15);
	TextField txtfield7 = new TextField(15);
	TextField txtfield8 = new TextField(15);
	TextField txtfield9 = new TextField(15);
	int pie, type, xy, areGamesLoaded = 0;
	private Label passStrength = new Label("");
	Panel content;
	Panel userPanel;
	Label loadingText = new Label("");
	Label loadingTime = new Label("");
	Label loading = new Label();
	Events events;
	String sport = "", country = "", year = "", input = "", medal = "", givenCountry = "", givenGame = "", game1 = "", game2 = "";
	long startTime, endTime;

	private void populateWindow() {

		content = new Panel(new GridLayout(0, 1, 0, 5));
		content.setBackground(Color.GRAY);
		userPanel = new Panel();

		loading.setText("Ucitavanje podataka je u toku...");
		userPanel.add(loading, CENTER_ALIGNMENT);
		ProgressBar pb = new ProgressBar();
		userPanel.add(pb);
		content.add(userPanel);
		userPanel.setBackground(Color.RED);
		Panel passPanel = new Panel();
		button5.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getCountOfCompetitors(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});

		
		button6.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getNumberOfDifferentSports(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button7.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getAverageHeightOfAllCompetitors(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button8.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getAverageWeightOfAllCompetitors(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button9.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getNumberOfDifferentSportsForCountryThatWonAtLeastOneMedal(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button10.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getCountriesThatHaveGoldMedal(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button11.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getCitiesThatHostedOlympicsAtLeastOnce(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button12.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getTeamsForGivenCountryAndGames(givenCountry, givenGame, events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
			
		});
		
		button13.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			areGamesLoaded = 0;
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getAllTheAthletesThatCompetedInPairOfGames(game1, game2, events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy));
			doneTextLoading();
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		button14.addActionListener((ae) -> {
			getFields();
			beginTextLoading();
			passStrength.setForeground(Color.GREEN);
			String text = String.valueOf(events.getPairOfCountryAthleteThatWonAtLeastOneMedalInTeamAndIndividual(events.countries, events.sports, input, sport,
					country, year, type, medal, pie, xy).toString());
			doneTextLoading();
			System.out.println(text);
			passStrength.setText(text);
			passStrength.revalidate();
		});
		
		passPanel.setBackground(Color.GREEN);
		passPanel.add(checkbox0);
		passPanel.add(checkbox1);
		passPanel.add(checkbox2);
		passPanel.add(checkbox3);
		passPanel.add(checkbox4);
		passPanel.add(checkbox5);

		content.add(passPanel);

		Panel fieldPanel = new Panel();
		fieldPanel.add(txtfield1);
		fieldPanel.add(txtfield2);
		fieldPanel.add(txtfield3);
		fieldPanel.add(txtfield4);
		fieldPanel.add(txtfield5);
		content.add(fieldPanel);
		
		Panel buttonPanel = new Panel();
		buttonPanel.setBackground(Color.DARK_GRAY);
		button5.setEnabled(false);
		button6.setEnabled(false);
		button7.setEnabled(false);
		button8.setEnabled(false);
		button9.setEnabled(false);
		button10.setEnabled(false);
		button11.setEnabled(false);
		button12.setEnabled(false);
		button13.setEnabled(false);
		button14.setEnabled(false);
		buttonPanel.add(button5);
		buttonPanel.add(button6);
		buttonPanel.add(button7);
		buttonPanel.add(button8);
		buttonPanel.add(button9);
		
		Panel buttonPanel2 = new Panel();
		buttonPanel2.setBackground(Color.DARK_GRAY);
		buttonPanel2.add(button10);
		buttonPanel2.add(button11);
		buttonPanel2.add(button12);
		buttonPanel2.add(button13);
		buttonPanel2.add(button14);
		
		content.add(buttonPanel);
		content.add(buttonPanel2);

		Panel countryAndGamesPanel = new Panel();
		Label countryAndGamesLabel = new Label("Input given country and games: ");
		countryAndGamesPanel.add(countryAndGamesLabel);
		countryAndGamesPanel.add(txtfield6);
		countryAndGamesPanel.add(txtfield7);
		content.add(countryAndGamesPanel);
		
		Panel pairOfGamesPanel = new Panel();
		Label pairOfGamesLabel = new Label("Input pair of games: ");
		pairOfGamesPanel.add(pairOfGamesLabel);
		pairOfGamesPanel.add(txtfield8);
		pairOfGamesPanel.add(txtfield9);
		content.add(pairOfGamesPanel);
		
		
		Panel termsPanel = new Panel();

		Checkbox drawChartPie = new Checkbox("Display ChartPie");
		Checkbox drawXYChart = new Checkbox("Display XYChart");
		termsPanel.add(drawChartPie);
		termsPanel.add(drawXYChart);
		content.add(termsPanel);

		drawChartPie.addItemListener((ie) -> {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				pie = 1;
			} else {
				pie = 0;
			}
		});
		
		drawXYChart.addItemListener((ie) -> {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				xy = 1;
			} else {
				xy = 0;
			}
		});
		
		Panel loadingPanel = new Panel();
		loadingPanel.add(loadingText);
		content.add(loadingPanel);
		
		Panel timePanel = new Panel();
		timePanel.add(loadingTime);
		content.add(timePanel);
		
		Panel submitPanel = new Panel();

		content.add(submitPanel);

		passStrength.setFont(new Font("Arial", Font.BOLD, 20));
		Panel passStrengthPanel = new Panel();
		passStrengthPanel.setBackground(Color.GRAY);
		passStrengthPanel.add(passStrength);
		content.add(passStrengthPanel);

		add(content, BorderLayout.CENTER);
	}

	private void getFields() {
		input = getInput();
		sport = txtfield1.getText();
		country = txtfield2.getText();
		year = txtfield3.getText();
			try {
		        type = Integer.parseInt(txtfield4.getText());
		    } catch (NumberFormatException nfe) {}
		medal = txtfield5.getText();
		givenCountry = txtfield6.getText();
		givenGame = txtfield7.getText();
		game1 = txtfield8.getText();
		game2 = txtfield9.getText();
	}

	private void doneTextLoading() {
		loadingText.setForeground(Color.CYAN);
		loadingText.setText("Done loading");
		endTime = System.currentTimeMillis();
		float end = (float)(endTime-startTime)/1000;
		String s = String.format(Locale.US, "%.2f", end);
		loadingTime.setText("Done in " + s + " seconds");
		loadingTime.setForeground(Color.CYAN);
		loadingTime.revalidate();
	}
	
	private void beginTextLoading() {
		loadingText.setText("Loading...");
		loadingText.setForeground(Color.GREEN);
		loadingText.revalidate();
		loadingTime.setText("");
		loadingTime.revalidate();
		startTime = System.currentTimeMillis();
	}
	
	private String getInput() {
		String _input = "";
		if (checkbox0.getState()) {
			_input += "0";
		}
		if (checkbox1.getState()) {
			_input += "1";
		}
		if (checkbox2.getState()) {
			_input += "2";
		}
		if (checkbox3.getState()) {
			_input += "3";
		}
		if (checkbox4.getState()) {
			_input += "4";
		}
		if (checkbox5.getState()) {
			_input += "5";
		}
		if(_input.compareTo("") == 0) {
			_input = "0";
		}
		return _input;
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public void dataInputDone() {
		loading.setText("Podaci uspesno ucitani");
		loading.setBackground(Color.CYAN);
		userPanel.setBackground(Color.CYAN);
		button5.setEnabled(true);
		button6.setEnabled(true);
		button7.setEnabled(true);
		button8.setEnabled(true);
		button9.setEnabled(true);
		button10.setEnabled(true);
		button11.setEnabled(true);
		button12.setEnabled(true);
		button13.setEnabled(true);
		button14.setEnabled(true);
		repaint();

	}

	public void dataInputHalfDone() {
		loading.setBackground(Color.YELLOW);
		userPanel.setBackground(Color.YELLOW);
		repaint();
		
	}

}
