package grotrack

import org.springframework.dao.DataIntegrityViolationException

class ExerciseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [exerciseInstanceList: Exercise.list(params), exerciseInstanceTotal: Exercise.count()]
    }

    def create() {
        [exerciseInstance: new Exercise(params)]
    }

    def save() {
        def exerciseInstance = new Exercise(params)
        if (!exerciseInstance.save(flush: true)) {
            render(view: "create", model: [exerciseInstance: exerciseInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'exercise.label', default: 'Exercise'), exerciseInstance.id])
        redirect(action: "show", id: exerciseInstance.id)
    }

    def show(Long id) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "list")
            return
        }

        [exerciseInstance: exerciseInstance]
    }

    def edit(Long id) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "list")
            return
        }

        [exerciseInstance: exerciseInstance]
    }

    def update(Long id, Long version) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (exerciseInstance.version > version) {
                exerciseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'exercise.label', default: 'Exercise')] as Object[],
                          "Another user has updated this Exercise while you were editing")
                render(view: "edit", model: [exerciseInstance: exerciseInstance])
                return
            }
        }

        exerciseInstance.properties = params

        if (!exerciseInstance.save(flush: true)) {
            render(view: "edit", model: [exerciseInstance: exerciseInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'exercise.label', default: 'Exercise'), exerciseInstance.id])
        redirect(action: "show", id: exerciseInstance.id)
    }

    def delete(Long id) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "list")
            return
        }

        try {
            exerciseInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'exercise.label', default: 'Exercise'), id])
            redirect(action: "show", id: id)
        }
    }
}
