package cscie56.demo

class ShoppingCart {

    Integer grandTotal = 0

    static hasMany = [lineItems:LineItem]

    static constraints = {
        grandTotal validator: { value, obj, errors ->
            value == obj.lineItems*.extension.sum()
        }
    }
}
