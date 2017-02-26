package cscie56.demo

class Book {

    String title
    Date dateOfPublication
    String isbn
    Integer price //stored as an integer, to make the math easier; will need to be formatted properly
    Publisher publisher

    static belongsTo = [Author, Publisher]
    static hasMany = [authors: Author]

    static namedQueries = {
        findAllBooksByPublicationYear { Integer year ->
            Date start = Date.parse("MM/dd/yyyy","1/1/${year}")
            Date end = Date.parse("MM/dd/yyyy","1/1/${year+1}")
            ge 'dateOfPublication', start
            lt 'dateOfPublication', end
        }
    }

    static constraints = {
        isbn unique: true
        dateOfPublication validator: {val, obj, errors ->
            obj?.authors?.each { author ->
                if (val < author?.birthDate) {
                    errors.rejectValue('dateOfPublication','pubDateBeforeBirthdate')
                }
            }
        }
        price min:0
    }

    String toString(){
        "$title (${dateOfPublication?.format('yyyy')})"
    }
}
