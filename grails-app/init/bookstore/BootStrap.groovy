package bookstore

import cscie56.demo.Author
import cscie56.demo.Book

class BootStrap {

    def init = { servletContext ->
        Author a1 = new Author(firstName: 'Stephen', lastName: 'King', birthDate: Date.parse("MM/dd/yyyy","9/21/1947"))
        saveObject(a1)

        saveBook('The Shining', "1/27/1977" , a1, '978-0-385-12167-5')
        saveBook("Salem's Lot", "10/17/1975", a1, '978-0-385-00751-1')
        saveBook("Carrie",        "4/5/1974", a1, '978-0-385-08695-0')
        saveBook("The Dead Zone", "8/1/1979", a1, '978-0-670-26077-5')

        Author a2 = new Author(firstName: 'Patrick', lastName: "O'Brian", birthDate: Date.parse("MM/dd/yyyy","12/12/1914"))
        saveObject(a2)

        saveBook('Master and Commander', "1/1/1969" , a2, '0-00-221526-8 ')
        saveBook("Post Captain", "1/1/1972", a2, '0-00-221657-4')
        saveBook("HMS Surprise",        "1/1/1973", a2, '0-397-00998-4')
        saveBook("The Mauritius Command ", "1/1/1977", a2, '0-00-222383-X')

    }
    def destroy = {
    }

    def saveBook(title,dateString,author,isbn) {
        Book b = new Book(title:title,dateOfPublication: Date.parse("MM/dd/yyyy",dateString), author:author, isbn:isbn)
        saveObject(b)
    }

    def saveObject(object) {
        if (!object.save(flush:true)) {
            object.errors.allErrors.each { println it }
        }
    }
}
