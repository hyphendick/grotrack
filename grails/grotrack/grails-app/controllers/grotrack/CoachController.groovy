package grotrack

import grails.converters.JSON;
import org.springframework.dao.DataIntegrityViolationException

class CoachController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[coachInstanceList: Coach.list(params), coachInstanceTotal: Coach.count()]
        render Coach.list(params) as JSON
    }

    def create() {
        [coachInstance: new Coach(params)]
    }

    def save() {
        def coachInstance = new Coach(params)
        if (!coachInstance.save(flush: true)) {
            render coachInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }
        render coachInstance as JSON
    }

    def show(Long id) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            response.status = 404
            render "Coach not found"
            return
        }

        render coachInstance as JSON
    }

    def edit(Long id) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "list")
            return
        }

        [coachInstance: coachInstance]
    }

    def update(Long id, Long version) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            response.status = 404
            render "Coach not found"
            return
        }

        if (version != null) {
            if (coachInstance.version > version) {
                render message(error: "Another user has updated this Coach while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        coachInstance.properties = params

        if (!coachInstance.save(flush: true)) {
            render coachInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

       render coachInstance as JSON
    }

    def delete(Long id) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            response.status = 404
            render "Coach not found"
            return
        }

        try {
            coachInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
