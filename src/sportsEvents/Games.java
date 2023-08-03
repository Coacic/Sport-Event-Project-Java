package sportsEvents;

import java.util.ArrayList;

public class Games {
    private ArrayList<String> games;

    public Games(){
        games = new ArrayList<>();
    }

    public Games(Games _games) {
    	games = new ArrayList<>();
    	if(_games.games != null) {
    		games.addAll(_games.games);
    	}
	}

	public String findGame(String str){
        String result = null;
        for (String string : games) {
            if(string.equals(str)){
                return string;
            }
        } 
        return result;
    }

    public void addGame(String game){
        games.add(game);
    }

    public boolean containsGame(String game){
        return games.contains(game);
    }
}
