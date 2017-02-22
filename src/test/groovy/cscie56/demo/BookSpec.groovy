package cscie56.demo

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Book)
class BookSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test validation of nullable objects" () {
        when:
            Book b1 = new Book()

        then:
            !b1.validate()

        when:
            b1 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", author: new Author(), publisher: new Publisher())

        then:
            b1.validate()
    }

    void "test isbn is unique"() {
        when:
            Book b1 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", author: new Author(), publisher: new Publisher())
            b1.save(flush:true)
            Book b2 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", author: new Author(), publisher: new Publisher())
            b2.save()

        then:
            b1.validate()
            !b2.validate()
    }

    @Unroll
    void "test publication date not before author birthdate" () {
        when:
            Author a = new Author(firstName: 'Stephen', lastName: 'King', birthDate: Date.parse("MM/dd/yyyy","9/21/1947"))
            Book b = new Book(title:title,dateOfPublication: Date.parse("MM/dd/yyyy",dateString), author:a, isbn:isbn, publisher: new Publisher())

        then:
            b.validate() == result

        where:
        title           |   dateString  | isbn                  | result
        'The Shining'   | "1/27/1977"   | '978-0-385-12167-5'   | true
        'The Shining'   | "1/27/1937"   | '978-0-385-12167-5'   | false
    }
}
