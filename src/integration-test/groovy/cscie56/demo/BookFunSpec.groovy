package cscie56.demo

import grails.test.mixin.integration.Integration
import grails.transaction.*

import spock.lang.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class BookFunSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test index"(){
        when:"index is called"
            go "/book/index"
        then:"book list is shown"
            title == "Book List"
    }

    void "test book 1"() {
        when:"The 1st book is queried"
            go '/book/show/1'
        then:
            title.contains("Show Book")
    }

    /*
	* data is injected into the test environment by init/BootStrap.groovy, so there are books already there
	*/

    @Unroll
    void "test book #bookId : #bookTitle"() {

        when:"The show book page is queried"
            go "/book/show/${bookId}"
            Book book = Book.get(bookId)

        then: "Page title should include book title"
            title == "Show Book : ${bookTitle}"
            $('span', id:'isbn-label').text() == "Isbn"
            $('span', id:'isbn-label').next().text() == book.isbn
            $('span', id:'dateOfPublication-label').text() == "Date Of Publication" // not looking up this value bc of formatting
            $('span', id:'price-label').text() == "Price" // not looking up this value bc of formatting
            $('span', id:'title-label').text() == "Title"
            $('span', id:'title-label').next().text() == book.title

        where:
            bookId  | bookTitle
            1       | "The Shining"
            2       | "Salem's Lot"
            3       | "Carrie"
            4       | "The Dead Zone"
    }

    void "test book #bookId : #bookTitle in French"() {

        when:"The show book page is queried"
            go "/book/show/${bookId}?lang=fr"
            def book = Book.get(bookId)

        then: "Page title should include book title"
            title == "Montrer Livre : ${bookTitle}"
            $('span', id:'isbn-label').text() == "ISBN"
            $('span', id:'isbn-label').next().text() == book.isbn
            $('span', id:'dateOfPublication-label').text() == "Date Sorti" // not looking up this value bc of formatting
            $('span', id:'price-label').text() == "Prix" // not looking up this value bc of formatting
            $('span', id:'title-label').text() == "Titre"
            $('span', id:'title-label').next().text() == book.title
            //etc.

        where:
            bookId  | bookTitle
            1       | "The Shining"
            2       | "Salem's Lot"
            3       | "Carrie"
            4       | "The Dead Zone"
    }

}