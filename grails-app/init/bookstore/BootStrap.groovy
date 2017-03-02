package bookstore

import cscie56.demo.Author
import cscie56.demo.Book
import cscie56.demo.Publisher

class BootStrap {

    def init = { servletContext ->
        Publisher p1 = new Publisher(name: 'Doubleday',dateEstablished: Date.parse("yyyy",'1897'),type: 'Trade')
        saveObject(p1)

        Publisher p2 = new Publisher(name: 'Collins',dateEstablished: Date.parse("yyyy",'1819'),type: 'Trade')
        saveObject(p2)

        Author a1 = new Author(firstName: 'Stephen', lastName: 'King', birthDate: Date.parse("MM/dd/yyyy","9/21/1947"))
        saveObject(a1)

        saveBook('The Shining', "1/27/1977" , [a1], '978-0-385-12167-5',p1,1999,'Horror')
        saveBook("Salem's Lot", "10/17/1975", [a1], '978-0-385-00751-1',p1,2499,'Horror')
        saveBook("Carrie",        "4/5/1974", [a1], '978-0-385-08695-0',p1,2999,'Horror')
        saveBook("The Dead Zone", "8/1/1979", [a1], '978-0-670-26077-5',p1,2999,'Horror')

        Author a2 = new Author(firstName: 'Patrick', lastName: "O'Brian", birthDate: Date.parse("MM/dd/yyyy","12/12/1914"))
        saveObject(a2)

        saveBook('Master and Commander',    "1/1/1969", [a2], '0-00-221526-8',p2, 999, 'Drama')
        saveBook("Post Captain",            "1/1/1972", [a2], '0-00-221657-4',p2,1299, 'Drama')
        saveBook("HMS Surprise",            "1/1/1973", [a2], '0-397-00998-4',p2,1999, 'Drama')
        saveBook("The Mauritius Command ",  "1/1/1977", [a2], '0-00-222383-X',p2,1999, 'Drama')

    }
    def destroy = {
    }

    def saveBook(title,dateString,authors,isbn,publisher,price,genre) {
        Book b = new Book(title:title,
                          dateOfPublication: Date.parse("MM/dd/yyyy",dateString),
                          authors:authors,
                          isbn:isbn,
                          publisher: publisher,
                          price:price,
                          genre: genre)
        saveObject(b)
        authors.each { author ->
            author.addToBooks(b)
            author.save()
        }
    }

    def saveObject(object) {
        if (!object.save(flush:true)) {
            object.errors.allErrors.each { println it }
        }
    }
}
