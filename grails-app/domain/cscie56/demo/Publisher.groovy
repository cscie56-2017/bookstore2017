package cscie56.demo

class Publisher {

    String name
    Date dateEstablished
    String type

    static constraints = {
        dateEstablished max: new Date()
        type inList: ['Trade','Textbook','Educational','Academic','Bargain','Self']
    }

    static transients = ['books']

    List<Book> getBooks(){
        Book.findAllByPublisher(this) as List
    }

    String toString(){
        "$name"
    }

    String toStringLong(){
        "$name, established ${dateEstablished.format('yyyy')}"
    }
}
