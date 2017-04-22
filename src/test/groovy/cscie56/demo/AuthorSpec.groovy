package cscie56.demo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Author)
@Mock(Book)
class AuthorSpec extends Specification {

    void "test create Author with books"() {
        when:
            Author a = new Author()
        then:
            !a.validate()
        when:
            a = new Author(firstName: 'Stephen',lastName: 'King',birthDate: new Date())
            Book b = new Book(title: 'book',dateOfPublication: new Date(), isbn: '1239088',authors: [a],price:1999,publisher: new Publisher())
            a.books = [] << b
        then:
            a.validate()
    }

    void "test fullname" () {
        when:
            Author a = new Author(firstName: 'Bob',lastName: 'Smith')
        then:
            a.fullName == "Bob Smith"
        when:
            a.middleName = 'Tiberius'
        then:
            a.fullName == "Bob Tiberius Smith"

    }

    void "test toString()" () {
        when:
            Author a = new Author(firstName: 'Bob',lastName: 'Smith')
        then:
            a.toString() == "Bob Smith"
        when:
            a.middleName = 'Tiberius'
        then:
            a.toString() == "Bob Tiberius Smith"
    }
}
