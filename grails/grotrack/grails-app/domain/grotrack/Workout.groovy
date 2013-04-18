package grotrack

class Workout {

	static hasMany = [exercises: Exercise]

	String name;
	
    static constraints = {
    }
}
