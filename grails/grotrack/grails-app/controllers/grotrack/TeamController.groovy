package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class TeamController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[teamInstanceList: Team.list(params), teamInstanceTotal: Team.count()]
        //teamInstanceList = Team.list(params)
        render Team.list(params) as JSON
    }

//    def create() {
//        [teamInstance: new Team(params)]
 //   }

    def save() {
        def teamInstance = new Team(params)
        if (!teamInstance.save(flush: true)) {
            render teamInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render teamInstance as JSON
    }

    def show(Long id) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
           response.status = 404
           render "Team not found"
           return
        }

        render teamInstance as JSON
    }

    def update(Long id, Long version) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
           response.status = 404
           render "Team not found"
           return
        }

        if (version != null) {
            if (teamInstance.version > version) {
                render message(error: "Another user has updated this Team while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        teamInstance.properties = params

        if (!teamInstance.save(flush: true)) {
           render teamInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render teamInstance as JSON
    }

    def delete(Long id) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
           response.status = 404
           render "Team not found"
            return
        }

        try {
            teamInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
