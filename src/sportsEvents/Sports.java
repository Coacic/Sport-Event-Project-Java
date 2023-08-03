package sportsEvents;

import java.util.ArrayList;

public class Sports {
    private ArrayList<String> sports;

    public Sports(){
        sports = new ArrayList<>();
    }

    public Sports(Sports _sports) {
    	sports = new ArrayList<>();
    	if(_sports.sports != null) {
    		sports.addAll(_sports.sports);    		
    	}
	}

	public String findSport(String str){
        String result = null;
        for (String string : sports) {
            if(string.compareTo(str) == 0){
                return string;
            }
        } 
        return result;
    }

    public void addSport(String sport){
        sports.add(sport);
    }
}
