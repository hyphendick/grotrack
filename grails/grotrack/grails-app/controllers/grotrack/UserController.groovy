package grotrack

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[userInstanceList: User.list(params), userInstanceTotal: User.count()]
        //userInstanceList = User.list(params)
        render User.list(params) as JSON
    }

//    def create() {
//        [userInstance: new User(params)]
 //   }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render userInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render userInstance as JSON
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
           response.status = 404
           render "User not found"
           return
        }

        render userInstance as JSON
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
           response.status = 404
           render "User not found"
           return
        }

        if (version != null) {
            if (userInstance.version > version) {
                render message(error: "Another user has updated this User while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
           render userInstance.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render userInstance as JSON
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
           response.status = 404
           render "User not found"
            return
        }

        try {
            userInstance.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
