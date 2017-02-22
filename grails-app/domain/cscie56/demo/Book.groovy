package cscie56.demo

class Book {

    String title
    Date dateOfPublication
    String isbn

    static belongsTo = [author:Author, publisher:Publisher]

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
            if (val < obj?.author?.birthDate) {
                errors.rejectValue('dateOfPublication','pubDateBeforeBirthdate')
            }
        }
    }

    String toString(){
        "$title (${dateOfPublication?.format('yyyy')})"
    }
}
