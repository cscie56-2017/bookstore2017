package cscie56.demo

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured([Role.ROLE_ADMIN])
class AuthorController {

    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def index(Integer max) {
        respond Author.list()
    }

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def show(Author author) {
        respond author
    }

    def create() {
        respond new Author(params)
    }

    @Transactional
    def save(Author author) {
        if (author == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (author.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond author.errors, view:'create'
            return
        }

        author.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'author.created.message', args: [author.fullName, author.birthDate])
                redirect author
            }
            '*' { respond author, [status: CREATED] }
        }
    }

    def edit(Author author) {
        respond author
    }

    @Transactional
    def update(Author author) {
        if (author == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (author.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond author.errors, view:'edit'
            return
        }

        author.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect author
            }
            '*'{ respond author, [status: OK] }
        }
    }

    @Transactional
    def delete(Author author) {

        if (author == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        author.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
