package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class WorkoutController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[workoutInstanceList: Workout.list(params), workoutInstanceTotal: Workout.count()]
        //workoutInstanceList = Workout.list(params)
        render Workout.list(params) as JSON
    }

//    def create() {
//        [workoutInstance: new Workout(params)]
 //   }

    def save() {
        def workoutInstance = new Workout(params)
        if (!workoutInstance.save(flush: true)) {
            render workoutInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render workoutInstance as JSON
    }

    def show(Long id) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
           response.status = 404
           render "Workout not found"
           return
        }

        render workoutInstance as JSON
    }

    def update(Long id, Long version) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
           response.status = 404
           render "Workout not found"
           return
        }

        if (version != null) {
            if (workoutInstance.version > version) {
                render message(error: "Another user has updated this Workout while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        workoutInstance.properties = params

        if (!workoutInstance.save(flush: true)) {
           render workoutInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render workoutInstance as JSON
    }

    def delete(Long id) {
        def workoutInstance = Workout.get(id)
        if (!workoutInstance) {
           response.status = 404
           render "Workout not found"
            return
        }

        try {
            workoutInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
