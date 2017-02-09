package cscie56.demo

class Author {

    String firstName
    String lastName
    Date birthDate

    static hasMany = [books:Book]

    static constraints = {
    }
}
