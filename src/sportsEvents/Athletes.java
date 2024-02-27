package sportsEvents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Athletes {
    private ArrayList<Athlete> listOfAthletes = null;
    int lines;

    public Athletes() {
        listOfAthletes = new ArrayList<>();
    }

    public Athletes(int n) {
        listOfAthletes = new ArrayList<>(n);
        lines = n;
    }

    public Athletes(Athletes athletes) {
    	listOfAthletes = new ArrayList<>();
    	if(athletes.listOfAthletes != null) {
    		listOfAthletes.addAll(athletes.listOfAthletes);
    		this.lines = athletes.lines;
    	}
	}

	private void readAthlete(String str) {
        Pattern p = Pattern.compile("([0-9]*)!([^!]*)!(NA|[MF])!([^!]*)!([^!]*)!([^\n]*)");
        Matcher m = p.matcher(str);
        m.matches();
        listOfAthletes.add(new Athlete(Integer.valueOf(m.group(1)), m.group(2), m.group(6), m.group(5), m.group(4), m.group(3).charAt(0)));
    }

    public ArrayList<Athlete> readAthletes() {
    	int cnt = 0, i = 1;
        try {
        	String str;
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Coca\\git\\Sports-Events-Java\\Sports-Events-Java\\src\\sportsEvents\\athletes.txt"));
            while ((str = reader.readLine()) != null) {
                readAthlete(str);
                cnt++;
                if((int)(i * lines/10) == cnt) {
                	ProgressBar.updateBar(i*10/2);
                	i++;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return listOfAthletes;
    }

    public Athlete getAthlete(int i) {
        if (i < listOfAthletes.size()) {
            return listOfAthletes.get(i);
        }
        return null;
    }

    public int getSize() {
        return listOfAthletes.size();
    }

    public Athlete findAthleteById(int id) {
        for (Athlete ath : listOfAthletes) {
            if (ath.getId() == id) {
                return ath;
            }
        }
        return null;
    }
}
