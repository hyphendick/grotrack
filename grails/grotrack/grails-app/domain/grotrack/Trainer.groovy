package grotrack

class Trainer extends User {
	static hasMany = [athletes: Athlete,teams:Team]
    static constraints = {
    }
}
