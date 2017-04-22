package cscie56.demo


import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class ShoppingCartIntSpec extends Specification {

    def shoppingCartService

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
            cart.lineItems.size() == allBooks.size()

        when:
            Book anotherBook = Book.get(1)
            shoppingCartService.addItemToCart(cart,  anotherBook,  1)

        then:
            cart.grandTotal == expectedSum + anotherBook.price
            cart.lineItems.size() == allBooks.size() + 1

    }
}
