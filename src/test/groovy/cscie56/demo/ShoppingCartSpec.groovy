package cscie56.demo

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ShoppingCart)
@Mock([Book,LineItem])
class ShoppingCartSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test grandTotal matches"() {
        when:
            Book b = new Book(price: 1000)
            LineItem item = new LineItem(book: b, quantity: 1)
            ShoppingCart cart = new ShoppingCart(lineItems: [item], grandTotal: 1000)
        then:
            cart.validate()

    }
}
