package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class TrainerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[trainerInstanceList: Trainer.list(params), trainerInstanceTotal: Trainer.count()]
        //trainerInstanceList = Trainer.list(params)
        render Trainer.list(params) as JSON
    }

//    def create() {
//        [trainerInstance: new Trainer(params)]
 //   }

    def save() {
        def trainerInstance = new Trainer(params)
        if (!trainerInstance.save(flush: true)) {
            render trainerInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render trainerInstance as JSON
    }

    def show(Long id) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
           response.status = 404
           render "Trainer not found"
           return
        }

        render trainerInstance as JSON
    }

    def update(Long id, Long version) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
           response.status = 404
           render "Trainer not found"
           return
        }

        if (version != null) {
            if (trainerInstance.version > version) {
                render message(error: "Another user has updated this Trainer while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        trainerInstance.properties = params

        if (!trainerInstance.save(flush: true)) {
           render trainerInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render trainerInstance as JSON
    }

    def delete(Long id) {
        def trainerInstance = Trainer.get(id)
        if (!trainerInstance) {
           response.status = 404
           render "Trainer not found"
            return
        }

        try {
            trainerInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
