package grotrack

class Workout {
	static expose = 'Workout'
	static hasMany = [exercises: Exercise]

	Frequency frequency;
	String name;
	Date startDate;
	Date endDate;

	
    static constraints = {
    }
}
