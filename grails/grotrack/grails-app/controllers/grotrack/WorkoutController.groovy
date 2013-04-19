package grotrack

import org.springframework.dao.DataIntegrityViolationException

class WorkoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [workoutInstanceList: Workout.list(params), workoutInstanceTotal: Workout.count()]
    }

    def create() {
        [workoutInstance: new Workout(params)]
    }

    def save() {
        def workoutInstance = new Workout(params)
        if (!workoutInstance.save(flush: true)) {
            render(view: "create", model: [workoutInstance: workoutInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'workout.label', default: 'Workout'), workoutInstance.id])
        redirect(action: "show", id: workoutInstance.id)
    }

    def show(Long id) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "list")
            return
        }

        [workoutInstance: workoutInstance]
    }

    def edit(Long id) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "list")
            return
        }

        [workoutInstance: workoutInstance]
    }

    def update(Long id, Long version) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (workoutInstance.version > version) {
                workoutInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'workout.label', default: 'Workout')] as Object[],
                          "Another user has updated this Workout while you were editing")
                render(view: "edit", model: [workoutInstance: workoutInstance])
                return
            }
        }

        workoutInstance.properties = params

        if (!workoutInstance.save(flush: true)) {
            render(view: "edit", model: [workoutInstance: workoutInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'workout.label', default: 'Workout'), workoutInstance.id])
        redirect(action: "show", id: workoutInstance.id)
    }

    def delete(Long id) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "list")
            return
        }

        try {
            workoutInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'workout.label', default: 'Workout'), id])
            redirect(action: "show", id: id)
        }
    }
}
