package cscie56.demo


import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class BookIntSpec extends Specification {

    void "test that Book is valid and has at least one author"() {
        when:""
            Book b = new Book()
        then:
            !b.validate()
    }

    void "test that there are 19 books in the db" () {
        expect:
            Book.count() == 19
    }
}
