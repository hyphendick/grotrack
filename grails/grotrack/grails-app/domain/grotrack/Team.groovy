package grotrack

class Team {
	String name;
	static hasMany = [athletes:Athlete]
	
    static constraints = {
    }
}
