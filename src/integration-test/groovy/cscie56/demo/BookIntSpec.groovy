package cscie56.demo


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class BookIntSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test that Book is valid and has at least one author"() {
        when:""
            Book b = new Book()
        then:
            !b.validate()
    }

    void "test that there are 8 books in the db" () {
        expect:
            Book.count() == 8
    }
}
