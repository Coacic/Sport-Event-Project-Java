package sportsEvents;

public class Athlete extends Competitor{
	private int id;
	private String name, weight, height, age;
	private char gender;
	
	public Athlete(int id, String name, String weight, String height, String age, char gender){
		this.id = id;
		this.name = name; 
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getWeight() {
		return weight;
	}

	public String getHeight() {
		return height;
	}

	public String getAge() {
		return age;
	}

	public char getGender() {
		return gender;
	}
	
	public boolean equals(Athlete ath) {
		if(this.id == ath.getId())
			return true;
		return false;
	}
	
	public boolean less(Athlete ath) {
		 if(getAge().compareTo(ath.getAge()) < 0)
			 return true;
		 return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getId()).append(" ").append(getName()).append(" ").append(getGender()).append(" ").append(getAge()).append(" ").append(getHeight()).append(" ").append(getWeight());
		return sb.toString();
	}
	
}
