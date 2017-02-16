package cscie56.demo

class Author {

    String firstName
    String middleName
    String lastName
    Date birthDate

    static hasMany = [books:Book]

    static transients = ['fullName']

    String getFullName() {
        "$firstName ${middleName?middleName+' ':''}$lastName"
    }

    static constraints = {
        firstName()
        middleName nullable: true
        lastName()
        birthDate()
    }

    String toString(){
        fullName
    }
}
