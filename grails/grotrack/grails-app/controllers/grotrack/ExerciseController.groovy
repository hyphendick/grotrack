package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ExerciseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[exerciseInstanceList: Exercise.list(params), exerciseInstanceTotal: Exercise.count()]
        //exerciseInstanceList = Exercise.list(params)
        render Exercise.list(params) as JSON
    }

//    def create() {
//        [exerciseInstance: new Exercise(params)]
 //   }

    def save() {
        def exerciseInstance = new Exercise(params)
        if (!exerciseInstance.save(flush: true)) {
            render exerciseInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render exerciseInstance as JSON
    }

    def show(Long id) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
           response.status = 404
           render "Exercise not found"
           return
        }

        render exerciseInstance as JSON
    }

    def update(Long id, Long version) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
           response.status = 404
           render "Exercise not found"
           return
        }

        if (version != null) {
            if (exerciseInstance.version > version) {
                render message(error: "Another user has updated this Exercise while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        exerciseInstance.properties = params

        if (!exerciseInstance.save(flush: true)) {
           render exerciseInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render exerciseInstance as JSON
    }

    def delete(Long id) {
        def exerciseInstance = Exercise.get(id)
        if (!exerciseInstance) {
           response.status = 404
           render "Exercise not found"
            return
        }

        try {
            exerciseInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
