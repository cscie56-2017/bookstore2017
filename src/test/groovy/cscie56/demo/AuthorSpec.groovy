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

    def setup() {
    }

    def cleanup() {
    }

    void "test create Author with books"() {
        when:
            Author a = new Author(firstName: 'Stephen',lastName: 'King',birthDate: new Date())
            Book b = new Book(title: 'book',dateOfPublication: new Date(), isbn: '1239088',authors: [a],price:1999,publisher: new Publisher())
            a.books = [] << b
        then:
            a.validate()
    }
}
