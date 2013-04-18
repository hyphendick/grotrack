package grotrack

import grails.converters.JSON;
 
class AthleteController {

	//static scaffold = true 
    def show() { 
		if(params.id){
			def ath = Athlete.get(params.id)
			if(ath != null){
				render ath as JSON
			} else {
				//render("404")
			}
			//if(Athlete.exists)
			
		} else {
			def athleteList = Athlete.list()
			render athleteList as JSON
		}
	}
	
//	def update() { }
//	def delete() { }
//	def save() {}
}
