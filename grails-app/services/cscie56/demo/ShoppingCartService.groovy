package cscie56.demo

import grails.transaction.Transactional

@Transactional
class ShoppingCartService {

    def addItemToCart(ShoppingCart cart, Book book, Integer quantity) {

        LineItem alreadyInCart = cart.lineItems.find { it.book == book }
        if (alreadyInCart) {
            alreadyInCart.quantity += quantity
        } else {
            cart.addToLineItems( new LineItem(book: book, quantity:quantity) )
        }
        cart.grandTotal += book.price * quantity
    }
}
