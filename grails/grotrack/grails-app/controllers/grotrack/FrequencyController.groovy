package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class FrequencyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[frequencyInstanceList: Frequency.list(params), frequencyInstanceTotal: Frequency.count()]
        //frequencyInstanceList = Frequency.list(params)
        render Frequency.list(params) as JSON
    }

//    def create() {
//        [frequencyInstance: new Frequency(params)]
 //   }

    def save() {
        def frequencyInstance = new Frequency(params)
        if (!frequencyInstance.save(flush: true)) {
            render frequencyInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render frequencyInstance as JSON
    }

    def show(Long id) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
           response.status = 404
           render "Frequency not found"
           return
        }

        render frequencyInstance as JSON
    }

    def update(Long id, Long version) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
           response.status = 404
           render "Frequency not found"
           return
        }

        if (version != null) {
            if (frequencyInstance.version > version) {
                render message(error: "Another user has updated this Frequency while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        frequencyInstance.properties = params

        if (!frequencyInstance.save(flush: true)) {
           render frequencyInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render frequencyInstance as JSON
    }

    def delete(Long id) {
        def frequencyInstance = Frequency.get(id)
        if (!frequencyInstance) {
           response.status = 404
           render "Frequency not found"
            return
        }

        try {
            frequencyInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
