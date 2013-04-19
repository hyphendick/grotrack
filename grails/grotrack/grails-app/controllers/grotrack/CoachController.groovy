package grotrack

import org.springframework.dao.DataIntegrityViolationException

class CoachController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [coachInstanceList: Coach.list(params), coachInstanceTotal: Coach.count()]
    }

    def create() {
        [coachInstance: new Coach(params)]
    }

    def save() {
        def coachInstance = new Coach(params)
        if (!coachInstance.save(flush: true)) {
            render(view: "create", model: [coachInstance: coachInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'coach.label', default: 'Coach'), coachInstance.id])
        redirect(action: "show", id: coachInstance.id)
    }

    def show(Long id) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "list")
            return
        }

        [coachInstance: coachInstance]
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
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (coachInstance.version > version) {
                coachInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'coach.label', default: 'Coach')] as Object[],
                          "Another user has updated this Coach while you were editing")
                render(view: "edit", model: [coachInstance: coachInstance])
                return
            }
        }

        coachInstance.properties = params

        if (!coachInstance.save(flush: true)) {
            render(view: "edit", model: [coachInstance: coachInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'coach.label', default: 'Coach'), coachInstance.id])
        redirect(action: "show", id: coachInstance.id)
    }

    def delete(Long id) {
        def coachInstance = Coach.get(id)
        if (!coachInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "list")
            return
        }

        try {
            coachInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'coach.label', default: 'Coach'), id])
            redirect(action: "show", id: id)
        }
    }
}
