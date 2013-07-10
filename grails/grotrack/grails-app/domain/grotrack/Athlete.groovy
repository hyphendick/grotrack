package grotrack


class Athlete extends User {
	static expose = 'Athlete'
	BigDecimal height;
	BigDecimal weight;
	Integer age;
	Date dob;

    static constraints = {

    	dob (nullable:true)
    }
}
