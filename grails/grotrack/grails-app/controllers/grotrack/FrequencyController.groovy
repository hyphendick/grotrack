package grotrack

import org.springframework.dao.DataIntegrityViolationException

class FrequencyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [frequencyInstanceList: Frequency.list(params), frequencyInstanceTotal: Frequency.count()]
    }

    def create() {
        [frequencyInstance: new Frequency(params)]
    }

    def save() {
        def frequencyInstance = new Frequency(params)
        if (!frequencyInstance.save(flush: true)) {
            render(view: "create", model: [frequencyInstance: frequencyInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'frequency.label', default: 'Frequency'), frequencyInstance.id])
        redirect(action: "show", id: frequencyInstance.id)
    }

    def show(Long id) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "list")
            return
        }

        [frequencyInstance: frequencyInstance]
    }

    def edit(Long id) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "list")
            return
        }

        [frequencyInstance: frequencyInstance]
    }

    def update(Long id, Long version) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (frequencyInstance.version > version) {
                frequencyInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'frequency.label', default: 'Frequency')] as Object[],
                          "Another user has updated this Frequency while you were editing")
                render(view: "edit", model: [frequencyInstance: frequencyInstance])
                return
            }
        }

        frequencyInstance.properties = params

        if (!frequencyInstance.save(flush: true)) {
            render(view: "edit", model: [frequencyInstance: frequencyInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'frequency.label', default: 'Frequency'), frequencyInstance.id])
        redirect(action: "show", id: frequencyInstance.id)
    }

    def delete(Long id) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "list")
            return
        }

        try {
            frequencyInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'frequency.label', default: 'Frequency'), id])
            redirect(action: "show", id: id)
        }
    }
}
