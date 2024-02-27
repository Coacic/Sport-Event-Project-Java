package sportsEvents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Events implements Runnable {

	public Athletes athletes;
	public Countries countries = new Countries();
	public Sports sports = new Sports();
	Games games = new Games();
	ArrayList<Event> events;
	List<Event> eventsMu;
	List<Event> eventsMu2;
	List<Event> eventsMu3;
	List<Event> eventsMu4;
	Thread thread, thread2, thread3, thread4;
	ArrayList<String> vecFileEve = new ArrayList<String>(), vecFileEve2 = new ArrayList<String>(),
			vecFileEve3 = new ArrayList<String>(), vecFileEve4 = new ArrayList<String>();
	int ptr = 0;

	public class Pair {
		private String country;
		private Athlete athlete;

		public Pair(String country, Athlete athlete) {
			this.country = country;
			this.athlete = athlete;
		}

		public String getCountry() {
			return country;
		}

		public Athlete getAthlete() {
			return athlete;
		}

		@Override
		public String toString() {
			String str = "[" + country + " - " + athlete + "]";
			return str;
		}
	}

	public Events(int n) {
		this.events = new ArrayList<Event>(n);
		this.eventsMu = new CopyOnWriteArrayList<Event>();
		this.eventsMu2 = new CopyOnWriteArrayList<Event>();
		this.eventsMu3 = new CopyOnWriteArrayList<Event>();
		this.eventsMu4 = new CopyOnWriteArrayList<Event>();
	}

	public Events(ArrayList<Event> eventsP) {
		events = new ArrayList<>(eventsP);
	}

	private void readEvent(String str, Athletes athletes, Countries countries, Sports sports, Games games) {
		Pattern p = Pattern.compile("([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!(\\w*)");
		Pattern p2 = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(str);
		m.matches();
		Matcher m2;
		Athlete athlete = null;
		String country, sport;
		Team team = new Team(m.group(3));
		if (m.group(5).equals("Individual")) {
			athlete = athletes.findAthleteById(Integer.valueOf(m.group(7)));
			team = null;
		} else if (m.group(5).equals("Team")) {
			m2 = p2.matcher(m.group(7));
			while (m2.find()) {
				team.addAthlete(athletes.findAthleteById(Integer.valueOf(m2.group())));
			}
			athlete = null;
		}
		country = countries.findCountry(m.group(6));
		if (country == null) {
			countries.pushCountry(m.group(6));
			country = countries.findCountry(m.group(6));
		}
		sport = sports.findSport(m.group(3));
		if (sport == null) {
			sports.addSport(m.group(3));
			sport = sports.findSport(m.group(3));
		}
		if (games.containsGame(m.group(4)) == false)
			games.addGame(m.group(4));
		events.add(
				new Event(m.group(1), m.group(2), m.group(5), m.group(8), sport, country, m.group(4), athlete, team));
	}

	public void readEvents(Athletes athletes, Countries countries, Sports sports, Games games) {
		try {
			String str;
			BufferedReader reader = new BufferedReader(new FileReader(
					"C:\\Users\\Coca\\git\\Sports-Events-Java\\Sports-Events-Java\\src\\sportsEvents\\events.txt"));
			while ((str = reader.readLine()) != null) {
				readEvent(str, athletes, countries, sports, games);
			}
			reader.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public Event getEvent(int i) {
		if (i < events.size()) {
			return events.get(i);
		}
		return null;
	}

	public int getSize() {
		return events.size();
	}

	public static void main(String[] argv) {
		BufferedReader reader;
		int linesAth = 0, linesEve = 0;
		try {
			reader = new BufferedReader(new FileReader(
					"C:\\Users\\Coca\\git\\Sports-Events-Java\\Sports-Events-Java\\src\\sportsEvents\\athletes.txt"));
			while (reader.readLine() != null) {
				linesAth++;
			}
			reader.close();
			reader = new BufferedReader(new FileReader(
					"C:\\Users\\Coca\\git\\Sports-Events-Java\\Sports-Events-Java\\src\\sportsEvents\\events.txt"));
			while (reader.readLine() != null) {
				linesEve++;
			}
			reader.close();
		} catch (FileNotFoundException e1) {
	        JOptionPane.showMessageDialog(null, e1.toString(), "InfoBox: " + "File not found", JOptionPane.INFORMATION_MESSAGE);
	        System.exit(-1);
		} catch (IOException io) {
		}
		Events mainEvents = new Events(linesEve);
		mainEvents.countries = new Countries();
		mainEvents.sports = new Sports();
		mainEvents.games = new Games();
		mainEvents.athletes = new Athletes(linesAth);
		mainEvents.events = new ArrayList<Event>(linesEve);
		Menu menu = new Menu(mainEvents);
		long start, end;
		try {
			start = System.currentTimeMillis();
			mainEvents.athletes.readAthletes();
			end = System.currentTimeMillis();
			menu.dataInputHalfDone();
			System.out.println("Operacija izvrsena u " + (end - start) + "ms");
			start = System.currentTimeMillis();
			mainEvents.readEventsMu(mainEvents.athletes, mainEvents.countries, mainEvents.sports, mainEvents.games);
			end = System.currentTimeMillis();
			System.out.println("Operacija izvrsena u " + (end - start) + "ms");
			menu.dataInputDone();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	void readEventsMu(Athletes athletes, Countries countries, Sports sports, Games games) {
		String str;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"C:\\Users\\Coca\\git\\Sports-Events-Java\\Sports-Events-Java\\src\\sportsEvents\\events.txt"));
			while ((str = reader.readLine()) != null) {
				vecFileEve.add(str);
				if ((str = reader.readLine()) == null) {
					break;
				}
				vecFileEve2.add(str);
				if ((str = reader.readLine()) == null) {
					break;
				}
				vecFileEve3.add(str);
				if ((str = reader.readLine()) == null) {
					break;
				}
				vecFileEve4.add(str);
			}
			reader.close();
			thread = new Thread(this);
			thread2 = new Thread(this);
			thread3 = new Thread(this);
			thread4 = new Thread(this);
			thread.start();
			thread2.start();
			thread3.start();
			thread4.start();

			thread.join();
			thread2.join();
			thread3.join();
			thread4.join();
			events.addAll(eventsMu);
			events.addAll(eventsMu2);
			events.addAll(eventsMu3);
			events.addAll(eventsMu4);
		} catch (FileNotFoundException e1) {
		} catch (IOException io) {
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void readEventMu(String str, Athletes athletes, Countries countries, Sports sports, Games games, int ptr) {
		Pattern p = Pattern.compile("([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!(\\w*)");
		Pattern p2 = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(str);
		m.matches();
		Matcher m2;
		Athlete athlete = null;
		String country, sport;
		Team team = new Team(m.group(3));
		if (m.group(5).equals("Individual")) {
			athlete = athletes.findAthleteById(Integer.valueOf(m.group(7)));
			team = null;
		} else if (m.group(5).equals("Team")) {
			m2 = p2.matcher(m.group(7));
			while (m2.find()) {
				synchronized (athletes) {
					team.addAthlete(athletes.findAthleteById(Integer.valueOf(m2.group())));
				}
			}
			athlete = null;
		}
		synchronized (countries) {
			country = countries.findCountry(m.group(6));
			if (country == null) {
				countries.pushCountry(m.group(6));
				country = countries.findCountry(m.group(6));
			}
		}
		synchronized (sports) {
			sport = sports.findSport(m.group(3));
			if (sport == null) {
				sports.addSport(m.group(3));
			}
			sport = sports.findSport(m.group(3));
		}
		synchronized (games) {
			if (games.containsGame(m.group(4)) == false) {
				games.addGame(m.group(4));
			}
		}
		switch (ptr) {
		case 0:
			eventsMu.add(new Event(m.group(1), m.group(2), m.group(5), m.group(8), sport, country, m.group(4), athlete,
					team));
			break;

		case 1:
			eventsMu2.add(new Event(m.group(1), m.group(2), m.group(5), m.group(8), sport, country, m.group(4), athlete,
					team));
			break;

		case 2:
			eventsMu3.add(new Event(m.group(1), m.group(2), m.group(5), m.group(8), sport, country, m.group(4), athlete,
					team));
			break;

		case 3:
			eventsMu4.add(new Event(m.group(1), m.group(2), m.group(5), m.group(8), sport, country, m.group(4), athlete,
					team));
			break;
		}
	}

	ArrayList<Athlete> getAllTheAthletesThatCompetedInPairOfGames(String game1, String game2, Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0;
		String name = "Get all the athletes that competed in the pair of games";
		for (Event eve : events) {
			if (game1.contains(eve.getGame()) && flags.get(pos) == '0') {
				for (Event eve2 : events) {
					if (game2.contains(eve2.getGame()) && !eve2.equals(eve)) {
						if (!eve.isTeam()) {
							if (!athletes.contains(eve.getAthlete())) {
								athletes.add(eve.getAthlete());
							}
						} else {
							for (int i = 0; i < eve.getTeam().getSize(); i++) {
								if (!athletes.contains(eve.getTeam().getAthlete(i))) {
									athletes.add(eve.getTeam().getAthlete(i));
								}
							}
						}
						if (!eve2.isTeam()) {
							if (!athletes.contains(eve2.getAthlete())) {
								athletes.add(eve2.getAthlete());
							}
						} else {
							for (int i = 0; i < eve2.getTeam().getSize(); i++) {
								if (!athletes.contains(eve2.getTeam().getAthlete(i))) {
									athletes.add(eve2.getTeam().getAthlete(i));
								}
							}
						}
					}
				}
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		System.out.println(athletes);
		return athletes;
	}

	ArrayList<Pair> getPairOfCountryAthleteThatWonAtLeastOneMedalInTeamAndIndividual(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Pair> parovi = new ArrayList<Pair>();
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0;
		String name = "Get all the athletes that won at least one medal in team and individual";
		for (Event eve : events) {
			if (!eve.getMedal().isEmpty() && !eve.isTeam() && flags.get(pos) == '0') {
				for (Event eve2 : events) {
					if (eve2.isTeam() && !eve2.getMedal().isEmpty()
							&& eve2.getTeam().containsAthlete(eve.getAthlete())) {
						Pair p = new Pair(eve2.getCountry(), eve.getAthlete());
						if (!parovi.contains(p)) {
							parovi.add(p);
						}
					}
				}
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags,name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		System.out.println(parovi);
		return parovi;
	}

	ArrayList<Team> getTeamsForGivenCountryAndGames(String givenCountry, String givenGame ,Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Team> timovi = new ArrayList<Team>();
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0;
		String name = "Get teams for given country and games";
		for (Event eve : events) {
			if (eve.isTeam() && givenCountry.compareTo(eve.getCountry()) != 0 && flags.get(pos) == '0' ) {
				Team team = new Team();
				for (int i = 0; i < eve.getTeam().getSize(); i++) {
					team.addAthlete(eve.getTeam().getAthlete((i)));
				}
				timovi.add(team);
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return timovi;
	}

	ArrayList<String> getCitiesThatHostedOlympicsAtLeastOnce(Countries countries2, Sports sports2, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<String> cities = new ArrayList<>();
		String name = "Get Cities that hosted Olympics games at least once";
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0;
		for (Event eve : events) {
			if (!eve.getCity().isEmpty() && !cities.contains(eve.getCity()) && flags.get(pos) == '0') {
				cities.add(eve.getCountry());
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return cities;
	}

	ArrayList<String> getCountriesThatHaveGoldMedal(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<String> arrayOfCountries = new ArrayList<>();
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0;
		String name = "Get all the countries that have gold medal";
		for (Event eve : events) {
			if (eve.getMedal().equals("Gold") && !arrayOfCountries.contains(eve.getCountry()) && flags.get(pos) == '0') {
				arrayOfCountries.add(eve.getCountry());
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return arrayOfCountries;
	}

	int getNumberOfDifferentSportsForCountryThatWonAtLeastOneMedal(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		String name = "Get the number of different sports for country that has won at least one medal";
		int pos = 0;
		ArrayList<String> vector = new ArrayList<String>();
		for (Event eve : events) {
			if (flags.get(pos) == '0' && eve.getMedal().compareTo("") != 0 && !vector.contains(eve.getSport())) {
				vector.add(eve.getSport());
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags,name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return vector.size();
	}

	int getNumberOfDifferentSports(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		ArrayList<String> vector = new ArrayList<String>();
		int pos = 0;
		String name = "Get the number of different sports";
		for (Event eve : events) {
			if (!vector.contains(eve.getSport()) && flags.get(pos) == '0') {
				vector.add(eve.getSport());
			}
			pos++;
		}
		if (pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return vector.size();
	}
	int getAverageWeightOfAllCompetitors(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0, count = 0;
		double weight = 0;
		String name = "Get the average weight of all the competitors";
		for (Event x : events) {
			if (flags.get(pos) == '0' && x.getAthlete() != null) {
				if (x.getAthlete().getWeight().compareTo("NA") != 0 || x.getAthlete().getWeight().isEmpty()) {
					weight += Double.valueOf(x.getAthlete().getWeight());
					count++;
				}
			} else if (flags.get(pos) == '0') {
				for (int i = 0; i < x.getTeam().getSize(); i++) {
					if (x.getTeam().getAthlete(i).getWeight().compareTo("NA") != 0) {
						weight += Double.valueOf(x.getTeam().getAthlete(i).getWeight());
						count++;
					}
				}
			}
			pos++;
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags, name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		if (count == 0)
			return 0;
		return (int) weight / count;

	}
	int getAverageHeightOfAllCompetitors(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int pos = 0, height = 0, count = 0;
		String name = "Get the average height of all the competitors";
		for (Event x : events) {
			if (flags.get(pos) == '0' && x.getAthlete() != null) {
				if (x.getAthlete().getHeight().compareTo("NA") != 0 || x.getAthlete().getHeight().isEmpty()) {
					height += Integer.valueOf(x.getAthlete().getHeight());
					count++;
				}
			} else if (flags.get(pos) == '0') {
				for (int i = 0; i < x.getTeam().getSize(); i++) {
					if (x.getTeam().getAthlete(i).getHeight().compareTo("NA") != 0
							|| x.getTeam().getAthlete(i).getHeight().isEmpty()) {
						height += Integer.valueOf(x.getTeam().getAthlete(i).getHeight());
						count++;
					}
				}
				pos++;
			}
		}
		if(pie == 1) {
			displayPieChart(countries, events, flags,name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		if (count == 0)
			return 0;
		return (int) height / count;
	}

	private static ArrayList<Integer> generatePercentageForCountries(Countries countries, Events events2) {
		int maxLen = Math.max(events2.getSize(), countries.getSize());
		ArrayList<Integer> percentages = new ArrayList<>(Collections.nCopies(maxLen, 0));
		String country;
		for (int i = 0; i < countries.getSize(); i++) {
			country = countries.getCountry(i);
			for (int j = 0; j < events2.getSize(); j++) {
				if (events2.getEvent(j).getCountry().equals(country)) {
					percentages.set(i, percentages.get(i) + 1);
				}
			}
		}
		return percentages;
	}

	int getCountOfCompetitors(Countries countries, Sports sports, String input, String sport, String country, String year, int type, String medal, int pie, int xy) {
		ArrayList<Character> flags = filterData(countries, sports, input, sport, country, year, type, medal);
		int n = 0, pos = 0;
		String name = "Get all the athletes that competed in the pair of games";
		for (Event eve : events) {
			if (eve.getAthlete() != null && flags.get(pos) == '0') {
				n++;
			} else if (flags.get(pos) == '0') {
				n += eve.getTeam().getSize();
			}
			pos++;
		}
		if (pie == 1) {
			displayPieChart(countries, events, flags,name);
		}
		if(xy == 1) {
			displayXYChart(countries, events, flags);
		}
		return n;
	}

	private void displayXYChart(Countries countries, ArrayList<Event> events, ArrayList<Character> flags) {
		GraphPanel.createAndShowGui(events, countries, flags);

	}

	private ArrayList<Character> filterData(Countries countries, Sports sports, String input, String sport,
			String country, String year, int type, String medal) {
		ArrayList<Character> flags = new ArrayList<>(Collections.nCopies(events.size(), '0'));
		ArrayList<Character> flagsNone = new ArrayList<>(Collections.nCopies(events.size(), '1'));
		int n = 0, wrongInput = 0, pos = 0;
		try {
			while (true) {
				wrongInput = 0;
				while (!input.isEmpty()) {
					n = input.charAt(0) - '0';
					input = input.substring(1);
					switch (n) {
					case 0:
						break;

					case 1:
						if(sports == null) {
							throw new Exception("Uneli ste ne postojeci sport!");
						}
						if (sports.findSport(sport).compareTo(sport) != 0) {
							System.out.println("Unesite validan sport");
							wrongInput = 1;
						}
						pos = 0;
						for (Event eve : events) {
							if (eve.getSport().compareTo(sport) == 0) {
								flags.set(pos, '1');
							}
							pos++;
						}
						break;

					case 2:
						if(country == null) {
							throw new Exception("Uneli ste ne postojecu drzavu!");
						}
						if (countries.findCountry(country) == null || !countries.findCountry(country).equals(country)) {
							System.out.println("Unesite validnu drzavu");
							wrongInput = 1;
							break;
						}
						pos = 0;
						for (Event eve : events) {
							if (!eve.getCountry().equals(country)) {
								flags.set(pos, '1');
							}
							pos++;
						}
						break;

					case 3:
						if (!year.matches("[0-9]+")) {
							System.out.println("Unesite brojcane vrednosti!");
							wrongInput = 1;
							break;
						}
						pos = 0;
						for (Event eve : events) {
							if (eve.getDate().split("\\ ")[0].compareTo(year) != 0) {
								flags.set(pos, '1');
							}
							pos++;
						}
						break;

					case 4:
						pos = 0;
						if(type != 0 && type != 1) {
							throw new Exception("Uneli ste ne postojecu drzavu!");
						}
						if (type == 0) {
							for (Event eve : events) {
								if (eve.getAthlete() == null) {
									flags.set(pos, '1');
								}
								pos++;
							}
						} else {
							for (Event eve : events) {
								if (eve.getAthlete() != null) {
									flags.set(pos, '1');
								}
								pos++;
							}
						}
						break;

					case 5:
						pos = 0;
						if (medal.equals("Gold") || medal.equals("Silver") || medal.equals("Bronze")
								|| medal.equals("!")) {
							if (medal == "!") {
								for (Event eve : events) {
									if (eve.getMedal().isEmpty()) {
										flags.set(pos, '1');
									}
									pos++;
								}
							} else {
								for (Event eve : events) {
									if (!eve.getMedal().equals(medal)) {
										flags.set(pos, '1');
									}
									pos++;
								}
							}
						} else {
							System.out.println("Unesite validne filtere medalja");
							wrongInput = 1;
						}
						break;

					default:
						System.out.println("Niste uneli filter koji postoji!");
						break;
					}
				}
				if (input.isEmpty() && wrongInput == 0) {
					return flags;
				}
				if (wrongInput == 1) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			return flagsNone;
		}
	}

	public void displayPieChart(Countries countries, ArrayList<Event> events2, ArrayList<Character> flags, String name) {
		Events eventsArr = new Events(events2);
		int cnt = 0;
		Iterator<Event> i = eventsArr.events.iterator();
		while (i.hasNext() && cnt < eventsArr.getSize()) {
			i.next();
			if (flags.get(cnt) == '1') {
				i.remove();
			}
			cnt++;
		}
		JFrame frame = new JFrame();
		frame.setTitle(name);
		frame.getContentPane().add(new PieChart(countries, generatePercentageForCountries(countries, eventsArr)));
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void run() {
		int thisPtr = ptr++;
		int cnt = 0, i = 1, lines = vecFileEve.size();
		switch (thisPtr) {
		case 0:
			for (String str : vecFileEve) {
				readEventMu(str, athletes, countries, sports, games, thisPtr);
				cnt++;
				if ((int) (i * lines / 10) == cnt) {
					ProgressBar.updateBar(5 + ProgressBar.getBarValue());
					i++;
				}
			}
			break;

		case 1:
			for (String str : vecFileEve2) {
				readEventMu(str, athletes, countries, sports, games, thisPtr);
			}
			break;

		case 2:
			for (String str : vecFileEve3) {
				readEventMu(str, athletes, countries, sports, games, thisPtr);
			}
			break;

		case 3:
			for (String str : vecFileEve4) {
				readEventMu(str, athletes, countries, sports, games, thisPtr);
			}
			break;
		}
	}
}
