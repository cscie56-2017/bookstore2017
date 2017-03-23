package cscie56.demo.lookup


import grails.transaction.Transactional

@Transactional
class LookupService {

    def lookupBookByIsbn() {
        def isbn = "1234567890"
        Book.findByIsbn(isbn)
    }
}
