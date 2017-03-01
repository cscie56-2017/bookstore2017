package cscie56.demo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BookService)
@Mock(Book)
class BookServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    def "testing setPriceByGenre: genre = #genre, expected price = #price" () {
        when:
            Book b = new Book(genre: genre)
            service.setPriceByGenre(b)
        then:
            b.price == price

        where:
        genre | price
        'Horror'|795
        'Science Fiction'|595
        'Mystery'|895
        'Biography'|995
        'Textbook'|19999
        'Satire'|2499
        'Drama'|499
        'Romance'|299
        'Poetry'|1999
        'Art'|2999
    }
}
