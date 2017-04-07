package cscie56.demo

import java.text.DecimalFormat

class Book {

    String title
    Date dateOfPublication
    String isbn
    Integer price //stored as an integer, to make the math easier; will need to be formatted properly
    Publisher publisher
    String genre

    Date dateCreated
    Date lastUpdated

    static belongsTo = [Author, Publisher]
    static hasMany = [authors: Author]

    static constraints = {
        isbn unique: true
        dateOfPublication validator: {val, obj, errors ->
            obj?.authors?.each { author ->
                if (val < author?.birthDate) {
                    errors.rejectValue('dateOfPublication','pubDateBeforeBirthdate')
                }
            }
        }
        authors minSize: 1
        price min:1
        genre inList: ['Horror', 'Science Fiction', 'Mystery', 'Biography', 'Textbook', 'Satire', 'Drama', 'Romance', 'Poetry', 'Art']
    }

    static namedQueries = {
        findAllBooksByPublicationYear { Integer year ->
            Date start = Date.parse("MM/dd/yyyy","1/1/${year}")
            Date end = Date.parse("MM/dd/yyyy","1/1/${year+1}")
            ge 'dateOfPublication', start
            lt 'dateOfPublication', end
        }
    }

    static transients = ['priceFormatted']

    String getPriceFormatted(){
        "\$${(int)(price/100)}.${new DecimalFormat('00').format(price%100)}"
    }

    String toString(){
        "$title (${dateOfPublication?.format('yyyy')})"
    }
}
