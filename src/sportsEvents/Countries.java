package sportsEvents;

import java.util.ArrayList;

public class Countries{
    private ArrayList<String> countries;

    public Countries(){
        countries = new ArrayList<>();
    }
    
    public Countries(Countries c) {
    	countries = new ArrayList<>(c.countries);
    }

    public int getSize(){
        return countries.size();
    }

    public String getCountry(int i){
        return countries.get(i);
    }

    public String findCountry(String str){
        String result = null;
        for (String string : countries) {
            if(string.equals(str)){
                return string;
            }
        } 
        return result;
    }

    public void pushCountry(String cnt){
        countries.add(cnt);
    }
    
    public int removeCountry(String cnt) {
    	int pos = 0;
    	while(this.getCountry(pos).compareTo(cnt) != 0){
    		pos++;
    		if(pos == this.getSize() - 1 && this.getCountry(pos).compareTo(cnt) != 0) {
    			return -1;
    		}
    	}
    	countries.remove(pos);
    	return 1;
    }
}
