package cscie56.demo

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured([Role.ROLE_ADMIN])
class BookController {

    BookService bookService
    def springSecurityService


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def index(Integer max) {
        respond Book.list()
    }

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def show(Book book) {
        respond book
    }

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def _show(Book book) {
        withFormat {
            html { respond book }
            json { render ( [authors:book?.authors*.fullName.join(', ')
                             ,price:book?.priceFormatted
                             ,publisher:book?.publisher?.name
                             ,isbn:book?.isbn
                             ,dateOfPublication:book?.dateOfPublication.format('MM/dd/yyyy')
                             ,genre:book?.genre]  as JSON ) }
            xml { render (book as XML) }
        }
    }

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
    def _book(Book book) {
        respond book
    }

    @Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
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

    @Transactional
    def saveEverythingCommand(CreateEverythingCommand cmd) {

        if (cmd.bookInstance == null || cmd.authorInstance == null || cmd.publisherInstance == null) {
            notFound()
            return
        }

        bookService.setupSaveEverythingAtOnce(cmd)

        if(cmd.hasErrors()) {
            transactionStatus.setRollbackOnly()
            render view:'createEverythingCommand',model:[cmd:cmd]
            return
        }

       // bookService.saveEverythingAtOnce(cmd)
        cmd.authorInstance.save (flush:true)
        cmd.publisherInstance.save (flush:true)
        cmd.bookInstance.save (flush:true)


        request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'everything.created.message', args: [cmd.bookInstance.title, cmd.authorInstance.fullName, cmd.publisherInstance.name, cmd.bookInstance?.dateOfPublication?.format('M/d/yyyy')])
                    redirect cmd.bookInstance
                }
                '*' { respond cmd.bookInstance, [status: CREATED] }
            }

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

        bookService.setPriceByGenre(book)

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
