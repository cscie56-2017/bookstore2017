package cscie56.demo

import grails.converters.JSON
import grails.converters.XML

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BookController {

    BookService bookService

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
        //render Book.findAllBooksByPublicationYear(year).list() as XML
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

        // set price if missing, before checking for errors
        bookService.setPriceByGenre(book)

        if (book.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond book.errors, view:'create'
            return
        }

        book.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message( code: 'book.created.message', args: [book.title, book.authors*.fullName, book.publisher.name, book.dateOfPublication])
                redirect book
            }
            '*' { respond book, [status: CREATED] }
        }
    }

    def createEverythingCommand() {
        def cmd = new CreateEverythingCommand(
                bookInstance:new Book(),
                authorInstance: new Author(),
                publisherInstance: new Publisher())
        respond cmd, model:[cmd:cmd]
    }

    def saveEverythingCommand(CreateEverythingCommand cmd) {

        if (cmd.bookInstance == null || cmd.authorInstance == null || cmd.publisherInstance == null) {
            notFound()
            return
        }

        if (!cmd.authorInstance.save (flush:true) ){
            cmd.authorInstance.errors.allErrors.each {println it }
            def errors = cmd.authorInstance.errors
            render view:'createEverythingCommand',model:[cmd:new CreateEverythingCommand(bookInstance:cmd.bookInstance, authorInstance:cmd.authorInstance,publisherInstance: cmd.publisherInstance, errors:errors)]
            return
        }

        if(!cmd.publisherInstance.save (flush:true) ){
            cmd.publisherInstance.errors.allErrors.each { println it }
            def errors = cmd.publisherInstance.errors
            render view:'createEverythingCommand',model:[cmd:new CreateEverythingCommand(bookInstance:cmd.bookInstance, authorInstance:cmd.authorInstance,publisherInstance: cmd.publisherInstance, errors:errors)]
            return
        }

        cmd.bookInstance.addToAuthors( cmd.authorInstance )
        cmd.authorInstance.addToBooks(cmd.bookInstance)
        cmd.bookInstance.publisher = cmd.publisherInstance
        // set price if missing, before checking for errors
        bookService.setPriceByGenre(cmd.bookInstance)

        if (cmd.bookInstance.hasErrors()) {
            def errors = cmd.bookInstance.errors
            render view:'createEverythingCommand',model:[cmd:new CreateEverythingCommand(bookInstance:cmd.bookInstance, authorInstance:cmd.authorInstance,publisherInstance: cmd.publisherInstance, errors:errors)]
            return
        }

        cmd.authorInstance.save flush:true
        cmd.publisherInstance.save flush:true
        cmd.bookInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'everything.created.message', args: [cmd.bookInstance.title, cmd.authorInstance.fullName, cmd.publisherInstance.name, cmd.bookInstance.dateOfPublication.format('M/d/yyyy')])
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
