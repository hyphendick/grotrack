package grotrack

import org.springframework.dao.DataIntegrityViolationException

class TeamController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [teamInstanceList: Team.list(params), teamInstanceTotal: Team.count()]
    }

    def create() {
        [teamInstance: new Team(params)]
    }

    def save() {
        def teamInstance = new Team(params)
        if (!teamInstance.save(flush: true)) {
            render(view: "create", model: [teamInstance: teamInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.id])
        redirect(action: "show", id: teamInstance.id)
    }

    def show(Long id) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "list")
            return
        }

        [teamInstance: teamInstance]
    }

    def edit(Long id) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "list")
            return
        }

        [teamInstance: teamInstance]
    }

    def update(Long id, Long version) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (teamInstance.version > version) {
                teamInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'team.label', default: 'Team')] as Object[],
                          "Another user has updated this Team while you were editing")
                render(view: "edit", model: [teamInstance: teamInstance])
                return
            }
        }

        teamInstance.properties = params

        if (!teamInstance.save(flush: true)) {
            render(view: "edit", model: [teamInstance: teamInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'team.label', default: 'Team'), teamInstance.id])
        redirect(action: "show", id: teamInstance.id)
    }

    def delete(Long id) {
        def teamInstance = Team.get(id)
        if (!teamInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "list")
            return
        }

        try {
            teamInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'team.label', default: 'Team'), id])
            redirect(action: "show", id: id)
        }
    }
}
