package cscie56.demo

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BookController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Book.listOrderByDateOfPublication(params), model:[bookCount: Book.count()]
    }

    def show(Book book) {
        respond book
    }

    def findBooksByYear(Integer year) {
        render view:'index', model:[bookList:Book.findAllBooksByPublicationYear(year).list()]
    }

    def create() {
        respond new Book(params)
    }

    @Transactional
    def save(Book book) {
        if (book == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (book.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond book.errors, view:'create'
            return
        }

        book.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*' { respond book, [status: CREATED] }
        }
    }

    def showEverything(CreateEverythingCommand cmd) {
        [ cmd: cmd ]
    }

    def createEverything() {
        CreateEverythingCommand cmd = new CreateEverythingCommand(bookInstance:new Book(), authorInstance: new Author(), publisherInstance: new Publisher())
        [ cmd: cmd ]
    }

    def createEverythingCommand() {
        CreateEverythingCommand cmd = new CreateEverythingCommand(
                bookInstance:new Book(),
                authorInstance: new Author(),
                publisherInstance: new Publisher())

        [ cmd: cmd ]
    }

    def saveEverything(Book bookInstance, Author authorInstance, Publisher publisherInstance) {
        if (bookInstance == null || authorInstance == null || publisherInstance == null) {
            notFound()
            return
        }

        if (!authorInstance.save (flush:true) ){
            authorInstance.errors.allErrors.each {println it }
        }

        if(!publisherInstance.save (flush:true) ){
            publisherInstance.errors.allErrors.each { println it }
        }

        bookInstance.author = authorInstance
        bookInstance.publisher = publisherInstance
        bookInstance.clearErrors() //book has already been validated bc it is used as a command object, so need to clear errors before re-validating
        bookInstance.validate()

        if (bookInstance.hasErrors() || authorInstance.hasErrors() || publisherInstance.hasErrors()) {
            def errors
            if (bookInstance.hasErrors()) {
                errors = bookInstance.errors
            }
            render view:'createEverything',model:[cmd:new CreateEverythingCommand(bookInstance:bookInstance, authorInstance:authorInstance,publisherInstance: publisherInstance, errors:errors)]
            return
        }

        authorInstance.save flush:true
        publisherInstance.save flush:true
        bookInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Create Book, Author and Publisher'), bookInstance.id])
                render  view: 'showEverything', model:[cmd:new CreateEverythingCommand(bookInstance:bookInstance, authorInstance:authorInstance,publisherInstance: publisherInstance)]
            }
            '*' { respond new CreateEverythingCommand(bookInstance:bookInstance, authorInstance: authorInstance,publisherInstance: publisherInstance), [view: 'showEverything', status: CREATED] }
        }
    }

    def saveEverythingCommand(CreateEverythingCommand cmd) {

        if (cmd.bookInstance == null || cmd.authorInstance == null || cmd.publisherInstance == null) {
            notFound()
            return
        }

        if (!cmd.authorInstance.save (flush:true) ){
            cmd.authorInstance.errors.allErrors.each {println it }
        }

        if(!cmd.publisherInstance.save (flush:true) ){
            cmd.publisherInstance.errors.allErrors.each { println it }
        }

        cmd.bookInstance.author = cmd.authorInstance
        cmd.bookInstance.publisher = cmd.publisherInstance
        cmd.bookInstance.clearErrors() //book has already been validated bc it is used as a command object, so need to clear errors before re-validating
        cmd.bookInstance.validate()

        if (cmd.bookInstance.hasErrors()) {
            def errors = cmd.bookInstance.errors
            render view:'createEverything',model:[cmd:new CreateEverythingCommand(bookInstance:cmd.bookInstance, authorInstance:cmd.authorInstance,publisherInstance: cmd.publisherInstance, errors:errors)]
            return
        }

        cmd.authorInstance.save flush:true
        cmd.publisherInstance.save flush:true
        cmd.bookInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'everything.created.message', args: [cmd.bookInstance.title, cmd.authorInstance.fullName, cmd.publisherInstance.name, cmd.bookInstance.dateOfPublication.format('MM/dd/yyyy')])
                redirect cmd.bookInstance
            }
            '*' { respond cmd.bookInstance, [status: CREATED] }        }
    }

    def edit(Book book) {
        respond book
    }

    @Transactional
    def update(Book book) {
        if (book == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (book.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond book.errors, view:'edit'
            return
        }

        book.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*'{ respond book, [status: OK] }
        }
    }

    @Transactional
    def delete(Book book) {

        if (book == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        book.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
