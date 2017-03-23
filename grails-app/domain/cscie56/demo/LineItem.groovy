package cscie56.demo

class LineItem {

    Book book
    Integer quantity

    static transients = ['extension']

    static constraints = {
        quantity min:0
    }

    Integer getExtension() {
        book.price * quantity
    }
}
