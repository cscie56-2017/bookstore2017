package cscie56.demo

import grails.test.mixin.*
import org.apache.http.HttpStatus
import spock.lang.*

import static org.springframework.http.HttpStatus.NOT_FOUND

@TestFor(BookController)
@Mock([Book,Author,Publisher])
class BookControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params << [title:'title',dateOfPublication: new Date(), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0]
    }

    def populateValidCommandParams(params) {
        assert params != null
        params << [bookInstance:[title:'title',dateOfPublication: new Date(), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0],
                authorInstance:[firstName:'firstName',lastName:'lastName',birthDate:new Date()-1],
                publisherInstance:[name:"name",dateEstablished:new Date()-2, type:"Trade"]]

    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.bookList
            model.bookCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.book!= null
    }


    void "Test the createEverythingCommand action returns the correct model"() {
        when:"The create action is executed"
        controller.createEverythingCommand()

        then:"The model is correctly created"
        model.cmd!= null
        model.cmd instanceof CreateEverythingCommand
    }

    void "Test that saving null fails" () {
        when:"The save action is executed with null"
            request.method = 'POST'
            controller.save(null)
        then:
            response.status == 404
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def book = new Book()
            book.validate()
            controller.save(book)

        then:"The create view is rendered again with the correct model"
            model.book!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            book = new Book(params)

            controller.save(book)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/book/show/1'
            controller.flash.message != null
            Book.count() == 1
    }

    void "Test that saveEverythingCommand null fails" () {
        when:"The save action is executed with null"
            request.method = 'POST'
            controller.saveEverythingCommand(new CreateEverythingCommand())
        then:
            response.status == 404
    }


    void "test the saveEverythingCommand action correctly persists an instance" () {
        when:"The saveEverythingCommand action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            CreateEverythingCommand cmd = new CreateEverythingCommand( bookInstance:new Book(),
                    authorInstance : new Author(),
                    publisherInstance :new Publisher())
            cmd.validate()
            controller.saveEverythingCommand(cmd)

        then:"The create view is rendered again with the correct model"
            model.cmd!= null
            view == '/book/createEverythingCommand'

        when: "Only a valid author is supplied"
            response.reset()
            cmd = new CreateEverythingCommand( bookInstance:new Book(),
                    authorInstance : new Author(firstName:'firstName',lastName:'lastName',birthDate:new Date()-1),
                    publisherInstance :new Publisher())
            cmd.validate()
            controller.saveEverythingCommand(cmd)
        then:"The createEverythingCommand view is rendered again with the correct model"
            model.cmd!= null
            view == '/book/createEverythingCommand'

        when: "Only a valid author and a valid publisher is supplied"
            response.reset()
            cmd = new CreateEverythingCommand( bookInstance:new Book(),
                    authorInstance : new Author(firstName:'firstName',lastName:'lastName',birthDate:new Date()-1),
                    publisherInstance :new Publisher(name:"name",dateEstablished:new Date()-2, type:"Trade"))
            cmd.validate()
            controller.saveEverythingCommand(cmd)
        then:"The createEverythingCommand view is rendered again with the correct model"
            model.cmd!= null
            view == '/book/createEverythingCommand'


        when:"The saveEverythingCommand action is executed with a valid instance"
            response.reset()
            populateValidCommandParams(params)
            cmd = new CreateEverythingCommand(params)
            controller.saveEverythingCommand(cmd)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/book/show/1'
            controller.flash.message != null
            Book.count() == 1

    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def book = new Book(params)
            controller.show(book)

        then:"A model is populated containing the domain instance"
            model.book == book
    }

    void "Test that the findBooksByYear returns only the anticipated results" () {
        when:
            Book b1 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(),price:0)
            b1.save(flush:true)
            Book b2 = new Book(title:'title',dateOfPublication: new Date() -365, isbn: "1234567891", authors: [new Author()], publisher: new Publisher(),price:0)
            b2.save()
            controller.findBooksByYear(2017)
        then:
            model.bookList != null
            model.bookList.size() == 1

    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def book = new Book(params)
            controller.edit(book)

        then:"A model is populated containing the domain instance"
            model.book == book
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/book/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def book = new Book()
            book.validate()
            controller.update(book)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.book == book

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            book = new Book(params).save(flush: true)
            controller.update(book)

        then:"A redirect is issued to the show action"
            book != null
            response.redirectedUrl == "/book/show/$book.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/book/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def book = new Book(params).save(flush: true)

        then:"It exists"
            Book.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(book)

        then:"The instance is deleted"
            Book.count() == 0
            response.redirectedUrl == '/book/index'
            flash.message != null
    }
}
