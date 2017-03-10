package cscie56.demo

import grails.test.mixin.*
import spock.lang.*

@TestFor(PublisherController)
@Mock(Publisher)
class PublisherControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        params << [name:"name",dateEstablished:new Date()-1, type:"Trade"]
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.publisherList
            model.publisherCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.publisher!= null
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
            def publisher = new Publisher()
            publisher.validate()
            controller.save(publisher)

        then:"The create view is rendered again with the correct model"
            model.publisher!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            publisher = new Publisher(params)

            controller.save(publisher)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/publisher/show/1'
            controller.flash.message != null
            Publisher.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def publisher = new Publisher(params)
            controller.show(publisher)

        then:"A model is populated containing the domain instance"
            model.publisher == publisher
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def publisher = new Publisher(params)
            controller.edit(publisher)

        then:"A model is populated containing the domain instance"
            model.publisher == publisher
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/publisher/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def publisher = new Publisher()
            publisher.validate()
            controller.update(publisher)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.publisher == publisher

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            publisher = new Publisher(params).save(flush: true)
            controller.update(publisher)

        then:"A redirect is issued to the show action"
            publisher != null
            response.redirectedUrl == "/publisher/show/$publisher.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/publisher/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def publisher = new Publisher(params).save(flush: true)

        then:"It exists"
            Publisher.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(publisher)

        then:"The instance is deleted"
            Publisher.count() == 0
            response.redirectedUrl == '/publisher/index'
            flash.message != null
    }
}
