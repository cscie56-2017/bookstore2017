package cscie56.demo

class Publisher {

    String name
    Date dateEstablished
    String type

    static hasMany = [book:Book]

    static constraints = {
        dateEstablished max: new Date()
        type inList: ['Trade','Textbook','Educational','Academic','Bargain','Self']
    }

    String toString(){
        "$name"
    }

    String toStringLong(){
        "$name, established ${dateEstablished.format('yyyy')}"
    }
}
