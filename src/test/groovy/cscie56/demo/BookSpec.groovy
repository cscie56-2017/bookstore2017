package cscie56.demo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Book)
@Mock([Author,Publisher])
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

    }

    void "test isbn is unique"() {
        when:
            Book b1 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0,genre: 'Horror')
            b1.addToAuthors(new Author())
            b1.save(flush:true)
            Book b2 = new Book(title:'title',dateOfPublication: new Date(), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0,genre: 'Horror')
            b2.addToAuthors(new Author())
            b2.save()
        then:
            b1.validate()
            !b2.validate()
    }

    @Unroll
    void "test publication date not before author birthdate" () {
        when:
            Author a = new Author(firstName: 'Stephen', lastName: 'King', birthDate: Date.parse("MM/dd/yyyy","9/21/1947"))
            Book b = new Book(title:title,dateOfPublication: Date.parse("MM/dd/yyyy",dateString), authors: [a], isbn:isbn, publisher: new Publisher(), price:0,genre: 'Horror')

        then:
            b.validate() == result

        where:
        title           |   dateString  | isbn                  | result
        'The Shining'   | "1/27/1977"   | '978-0-385-12167-5'   | true
        'The Shining'   | "1/27/1937"   | '978-0-385-12167-5'   | false
    }

    void "test findAllBooksByYear" () {
        when:
            Book b1 = new Book(title:'title',dateOfPublication: Date.parse('yyyy','2017'), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0,genre: 'Horror')
            Book b2 = new Book(title:'title',dateOfPublication: Date.parse('yyyy','2016'), isbn: "1234567891", authors: [new Author()], publisher: new Publisher(), price:0,genre: 'Horror')
            b1.save(flush:true)
            b2.save(flush:true)
        then:
            Book.findAllBooksByPublicationYear(2017).list().size() == 1
            Book.findAllBooksByPublicationYear(2016).list().size() == 1
            Book.findAllBooksByPublicationYear(2015).list().size() == 0

    }

    void "test toString()" () {
        when:
            Book b1 = new Book(title:'title',dateOfPublication: Date.parse('yyyy','2017'), isbn: "1234567890", authors: [new Author()], publisher: new Publisher(), price:0)
        then:
            b1.toString() == "title (2017)"
    }
}
