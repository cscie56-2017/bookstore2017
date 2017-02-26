package cscie56.demo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PublisherController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Publisher.list(params), model:[publisherCount: Publisher.count()]
    }

    def show(Publisher publisher) {
        respond publisher
    }

    def create() {
        respond new Publisher(params)
    }

    @Transactional
    def save(Publisher publisher) {
        if (publisher == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (publisher.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond publisher.errors, view:'create'
            return
        }

        publisher.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'publisher.created.message', args: [publisher.name, publisher.dateEstablished.format('yyyy')])
                redirect publisher
            }
            '*' { respond publisher, [status: CREATED] }
        }
    }

    def edit(Publisher publisher) {
        respond publisher
    }

    @Transactional
    def update(Publisher publisher) {
        if (publisher == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (publisher.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond publisher.errors, view:'edit'
            return
        }

        publisher.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'publisher.label', default: 'Publisher'), publisher.id])
                redirect publisher
            }
            '*'{ respond publisher, [status: OK] }
        }
    }

    @Transactional
    def delete(Publisher publisher) {

        if (publisher == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        publisher.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'publisher.label', default: 'Publisher'), publisher.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'publisher.label', default: 'Publisher'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
