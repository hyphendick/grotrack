package grotrack

class Workout {

	static hasMany = [exercises: Exercise]

	String name;
	Date startDate;
	Date endDate;
	
    static constraints = {
    }
}
