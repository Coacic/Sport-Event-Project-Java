package sportsEvents;
//package PoopTreciLab;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.JFrame;
//
//import PoopTreciLab.Events.Pair;
//
//public class Main {
//
//	public Athletes athletes;
//	public Events events;
//	public Countries countries = new Countries();
//	public Sports sports = new Sports();
//	Games games = new Games();
//	ArrayList<Event> events0;
//	ArrayList<Event> eventsMu;
//	ArrayList<Event> eventsMu2;
//	ArrayList<Event> eventsMu3;
//	ArrayList<Event> eventsMu4;
//	Thread thread, thread2, thread3, thread4;
//	ArrayList<String> vecFileEve = new ArrayList<String>(), vecFileEve2 = new ArrayList<String>(),
//			vecFileEve3 = new ArrayList<String>(), vecFileEve4 = new ArrayList<String>();
//	int ptr = 0;
//	
//	public static void main(String[] args) {			
//			Countries countries = new Countries();
//			Sports sports = new Sports();
//			Games games = new Games();
//			BufferedReader reader;
//			int linesAth = 0, linesEve = 0, n = 0;
//			try {
//				reader = new BufferedReader(new FileReader(
//						"C:\\Users\\Coca\\eclipse-workspace\\Poop Projekat 2\\src\\PoopTreciLab\\athletes.txt"));
//				while (reader.readLine() != null) {
//					linesAth++;
//				}
//				reader.close();
//				reader = new BufferedReader(new FileReader(
//						"C:\\Users\\Coca\\eclipse-workspace\\Poop Projekat 2\\src\\PoopTreciLab\\events.txt"));
//				while (reader.readLine() != null) {
//					linesEve++;
//				}
//				reader.close();
//			} catch (FileNotFoundException e1) {
//			} catch (IOException io) {
//			}
//			Athletes athletes = new Athletes(linesAth);
//			Events events = new Events(linesEve);
//			Scanner sc = new Scanner(System.in);
//			ArrayList<String> vectorOfStrings = new ArrayList<String>();
//			ArrayList<Team> vectorOfTeams = new ArrayList<Team>();
//			ArrayList<Athlete> vectorOfAthletes = new ArrayList<Athlete>();
//			ArrayList<Pair> parovi = new ArrayList<Pair>();
//			int isDataLoaded1 = 0, isDataLoaded2 = 0;
//			while (true) {
//				int op = -1;
//				try {
//					System.out.println(
//							"-----------------------------------------------------------------------------------------------------------------------\nUnesite opciju:\n0. Exit\n1. Unos podataka\n2. Ispis podataka atleta\n3. Ucitavanje podataka eventova\n4. Ispis podataka eventova");
//					System.out.println(
//							"5. Ukupan broj ucesnika na olimpijskim igrama\n6. Ukupan broj razlicitih disciplina na Olimpijskim igrama\n7. Srednja vrednost visine svih takmicara\n8. Srednja vrednost tezine svih takmicara");
//					System.out.println(
//							"9. Odredjivanje broja razlicitih sportova u kojima je zadata drzava osvojila barem jednu medalju\n10. Odredjivanje drzava koje su na barem jednim Olimpijskim igrama ostvarile najbolji uspeh\n11. Odredjivanje svih gradova u kojima su olimpijske igre odrzane barem jednom");
//					System.out.println(
//							"12. Dohvatanje svih timova koje je zadata drzava imala na zadatim igrama\n13. Odredjivanje svih sportista koji su ucestvovali na zadatom paru Olimpijskih igara\n14. Dohvatanje svih parova drzava - sportista");
//					System.out.println(
//							"-----------------------------------------------------------------------------------------------------------------------");
//					if (sc.hasNextInt()) {
//						op = sc.nextInt();
//					}
//					if (sc.hasNextLine()) {
//						sc.nextLine();
//					}
//
//					switch (op) {
//					case 0:
//						System.out.println("Program uspesno zavrsen.");
//						sc.close();
//						return;
//
//					case 1:
//						athletes.readAthletes();
//						isDataLoaded1 = 1;
//						break;
//
//					case 2:
//						if (isDataLoaded1 == 0) {
//							System.out.println("Unesite podatke atleta prvo!");
//							break;
//						}
//						System.out.println("Unesite koliko linija zelite da se stampa:");
//						n = sc.nextInt();
//						sc.nextLine();
//						for (int i = 0; i < n; i++) {
//							System.out.println(athletes.getAthlete(i));
//						}
//						break;
//
//					case 3:
//						if (isDataLoaded1 == 0) {
//							System.out.println("Unesite podatke atleta prvo!");
//							break;
//						}
//						// events.readEvents(athletes, countries, sports, games);
//						events.readEventsMu(athletes, countries, sports, games);
//						isDataLoaded2 = 1;
//						break;
//
//					case 4:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						System.out.println("Unesite koliko linija zelite da se stampa:");
//						n = sc.nextInt();
//						sc.nextLine();
//						for (int i = 0; i < n; i++) {
//							System.out.println(events.getEvent(i));
//						}
//						break;
//
//					case 5:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						events.getCountOfCompetitors(countries, sports);
//						break;
//
//					case 6:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						System.out.println(events.getNumberOfDifferentSports(countries, sports));
//						break;
//
//					case 7:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						System.out.println(events.getAverageHeightOfAllCompetitors(countries, sports));
//						break;
//
//					case 8:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						System.out.println(events.getAverageWeightOfAllCompetitors(countries, sports));
//						break;
//
//					case 9:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						System.out.println(
//								events.getNumberOfDifferentSportsForCountryThatWonAtLeastOneMedal(countries, sports));
//						break;
//
//					case 10:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						vectorOfStrings = events.getCountriesThatHaveGoldMedal();
//						System.out.println(vectorOfStrings);
//						break;
//
//					case 11:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						vectorOfStrings = events.getCitiesThatHostedOlympicsAtLeastOnce();
//						System.out.println(vectorOfStrings);
//						break;
//
//					case 12:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						vectorOfTeams = events.getTeamsForGivenCountryAndGames(countries, games);
//						System.out.println(vectorOfTeams);
//						break;
//
//					case 13:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						vectorOfAthletes = events.getAllTheAthletesThatCompetedInPairOfGames();
//						System.out.println(vectorOfAthletes);
//						break;
//
//					case 14:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						parovi = events.getPairOfCountryAthleteThatWonAtLeastOneMedalInTeamAndIndividual();
//						System.out.println(parovi);
//						break;
//
//					case 15:
//						if (isDataLoaded1 == 0 || isDataLoaded2 == 0) {
//							System.out.println("Unesite podatke atleta i eventova prvo!");
//							break;
//						}
//						//TODO
//						break;
//
//					default:
//						break;
//					}
//				} catch (Exception e) {
//					System.out.println(e.getMessage());
//				}
//			}
//		}
//}
