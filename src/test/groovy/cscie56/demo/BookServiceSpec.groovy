package cscie56.demo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BookService)
@Mock(Book)
class BookServiceSpec extends Specification {

    PriceGeneratorService priceGeneratorService


    def setup() {
        priceGeneratorService = new PriceGeneratorService()
        service.priceGeneratorService = priceGeneratorService
    }

    def cleanup() {
    }

    @Unroll
    def "testing setPriceByGenre: genre = #genre, expected price = #price" () {
        when:
            Book b = new Book(genre: genre)
            service.setPriceByGenre(b)
        then:
            b.price == price

        where:
        genre               | price
        'Horror'            | 795
        'Science Fiction'   | 595
        'Mystery'           | 895
        'Biography'         | 995
        'Textbook'          | 19999
        'Satire'            | 2499
        'Drama'             | 499
        'Romance'           | 299
        'Poetry'            | 1999
        'Art'               | 2999
    }

    @Unroll
    void "verify that price is set only if absent: genre = #genre, price = #price, expected price = #expectedPrice" () {
        when:
            Book b = new Book(genre: genre, price:price)
            service.setPriceByGenre(b)
        then:
            b.price == expectedPrice

        where:
        genre               | price  | expectedPrice
        'Horror'            | null   | 795
        'Science Fiction'   | 995    | 995
        'Mystery'           | null   | 895
        'Biography'         | null   | 995
        'Textbook'          | 10999  | 10999
        'Satire'            | null   | 2499
        'Drama'             | 1299   | 1299
        'Romance'           | null   | 299
        'Poetry'            | null   | 1999
        'Art'               | null   | 2999
    }
}
