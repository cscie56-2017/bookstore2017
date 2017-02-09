package cscie56.demo

class Book {

    String title
    Date dateOfPublication
    String isbn

    static belongsTo = [author:Author]

    static constraints = {
        isbn unique: true
    }
}
