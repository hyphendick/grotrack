package grotrack

import org.springframework.dao.DataIntegrityViolationException

class TrainerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [trainerInstanceList: Trainer.list(params), trainerInstanceTotal: Trainer.count()]
    }

    def create() {
        [trainerInstance: new Trainer(params)]
    }

    def save() {
        def trainerInstance = new Trainer(params)
        if (!trainerInstance.save(flush: true)) {
            render(view: "create", model: [trainerInstance: trainerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'trainer.label', default: 'Trainer'), trainerInstance.id])
        redirect(action: "show", id: trainerInstance.id)
    }

    def show(Long id) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "list")
            return
        }

        [trainerInstance: trainerInstance]
    }

    def edit(Long id) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "list")
            return
        }

        [trainerInstance: trainerInstance]
    }

    def update(Long id, Long version) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (trainerInstance.version > version) {
                trainerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'trainer.label', default: 'Trainer')] as Object[],
                          "Another user has updated this Trainer while you were editing")
                render(view: "edit", model: [trainerInstance: trainerInstance])
                return
            }
        }

        trainerInstance.properties = params

        if (!trainerInstance.save(flush: true)) {
            render(view: "edit", model: [trainerInstance: trainerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'trainer.label', default: 'Trainer'), trainerInstance.id])
        redirect(action: "show", id: trainerInstance.id)
    }

    def delete(Long id) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "list")
            return
        }

        try {
            trainerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'trainer.label', default: 'Trainer'), id])
            redirect(action: "show", id: id)
        }
    }
}
