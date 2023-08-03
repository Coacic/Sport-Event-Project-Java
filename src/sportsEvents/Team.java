package sportsEvents;

import java.util.ArrayList;

public class Team extends Competitor {
    private ArrayList<Athlete> team;
    @SuppressWarnings("unused")
	private String sport;

    public Team(){
        sport = null;
        team = new ArrayList<>();
    }

    public Team(String sport){
        this.sport = sport;
        team = new ArrayList<>();
    }

    public Team(int n){
        team = new ArrayList<>(n);
    }

    public int getSize(){
        return team.size();
    }

    public Athlete getAthlete(int i){
        if(i < team.size()){
            return team.get(i);
        }
        return null;
    }

    public void addAthlete(Athlete ath){
        team.add(ath);
    }

    public boolean containsAthlete(Athlete ath){
        return team.contains(ath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        team.forEach((e)->{
            sb.append(e).append(", ");
        });
        sb.reverse();
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        sb.reverse();
        sb.append("]");
        return sb.toString();
    }
}
