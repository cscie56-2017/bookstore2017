package cscie56.demo


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class ShoppingCartIntSpec extends Specification {

    def shoppingCartService

    def setup() {
    }

    def cleanup() {
    }

    void "test shopping cart addition"() {
       when:
            ShoppingCart cart = new ShoppingCart()

            def allBooks = Book.list()

            allBooks.each {
                shoppingCartService.addItemToCart(cart, it, 1)
            }
            def expectedSum = allBooks*.price.sum()

        then:
            cart.grandTotal == expectedSum
            cart.validate()

        when:
            Book anotherBook = Book.get(1)
            shoppingCartService.addItemToCart(cart,  anotherBook,  1)

        then:
            cart.grandTotal == expectedSum + anotherBook.price
            cart.validate()
    }
}
