package sportsEvents;

public class Event {
    private String date, city, type, medal, sport, country, game;
    private Athlete athlete;
    private Team team;
    private char isTeam;

    public Event(String date, String city, String type, String medal, String sport, String country, String game,
            Athlete athlete, Team team) {
        this.date = date;
        this.city = city;
        this.type = type;
        this.medal = medal;
        this.sport = sport;
        this.country = country;
        this.game = game;
        this.athlete = athlete;
        this.team = null;
        this.team = team;
        isTeam = 0;
        if (this.team != null) {
            isTeam = 1;
        }
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return type;
    }

    public String getMedal() {
        return medal;
    }

    public String getSport() {
        return sport;
    }

    public String getCountry() {
        return country;
    }

    public String getGame() {
        return game;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Team getTeam() {
        return team;
    }

    boolean isTeam() {
        if (isTeam == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isTeam() == true) {
            sb.append("(*)Tim:\n").append(getTeam());
            if (athlete != null) {
                sb.append(getAthlete()).append('\n');
            }
            sb.append(getDate()).append(" ").append(getCity()).append(" ").append(getSport()).append(" ")
                    .append(getGame()).append(" ").append(getCountry()).append(" ").append(getMedal());
        } else if (athlete != null) {
            sb.append("(-)Atleta: ").append(getAthlete()).append(" ").append(getDate()).append(" ").append(getCity())
                    .append(" ").append(getSport()).append(" ").append(getGame()).append(" ").append(getCountry())
                    .append(" ").append(getMedal());
        }
        return sb.toString();
    }

}
