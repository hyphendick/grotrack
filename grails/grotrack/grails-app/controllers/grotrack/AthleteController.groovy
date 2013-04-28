package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class AthleteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[athleteInstanceList: Athlete.list(params), athleteInstanceTotal: Athlete.count()]
        //athleteInstanceList = Athlete.list(params)
        render Athlete.list(params) as JSON
    }

//    def create() {
//        [athleteInstance: new Athlete(params)]
 //   }

    def save() {
        def athleteInstance = new Athlete(params)
        if (!athleteInstance.save(flush: true)) {
            render athleteInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render athleteInstance as JSON
    }

    def show(Long id) {
        def athleteInstance = Athlete.get(id)
        if (!athleteInstance) {
           response.status = 404
           render "Athlete not found"
           return
        }

        render athleteInstance as JSON
    }

    def update(Long id, Long version) {
        def athleteInstance = Athlete.get(id)
        if (!athleteInstance) {
           response.status = 404
           render "Athlete not found"
           return
        }

        if (version != null) {
            if (athleteInstance.version > version) {
                render message(error: "Another user has updated this Athlete while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        athleteInstance.properties = params

        if (!athleteInstance.save(flush: true)) {
           render athleteInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render athleteInstance as JSON
    }

    def delete(Long id) {
        def athleteInstance = Athlete.get(id)
        if (!athleteInstance) {
           response.status = 404
           render "Athlete not found"
            return
        }

        try {
            athleteInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
