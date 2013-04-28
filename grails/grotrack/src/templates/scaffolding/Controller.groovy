<%=packageName ? "package ${packageName}\n\n" : ''%>import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ${className}Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        //[${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]
        //${propertyName}List = ${className}.list(params)
        render ${className}.list(params) as JSON
    }

//    def create() {
//        [${propertyName}: new ${className}(params)]
 //   }

    def save() {
        def ${propertyName} = new ${className}(params)
        if (!${propertyName}.save(flush: true)) {
            render ${propertyName}.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render ${propertyName} as JSON
    }

    def show(Long id) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
           response.status = 404
           render "${className} not found"
           return
        }

        render ${propertyName} as JSON
    }

    def update(Long id, Long version) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
           response.status = 404
           render "${className} not found"
           return
        }

        if (version != null) {
            if (${propertyName}.version > version) {<% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
                render message(error: "Another user has updated this ${className} while you were editing", encodeAs: 'HTML') as JSON
                return
            }
        }

        ${propertyName}.properties = params

        if (!${propertyName}.save(flush: true)) {
           render ${propertyName}.errors.allErrors.collect {
    message(error: it,encodeAs:'HTML')
} as JSON
            return
        }

        render ${propertyName} as JSON
    }

    def delete(Long id) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
           response.status = 404
           render "${className} not found"
            return
        }

        try {
            ${propertyName}.delete(flush: true)
            response.status = 200
            render ''
        }
        catch (DataIntegrityViolationException e) {
            response.status = 500
            render 'Error deleting'
        }
    }
}
