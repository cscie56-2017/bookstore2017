package cscie56.demo

class CreateEverythingCommand implements grails.validation.Validateable {

    Book bookInstance
    Author authorInstance
    Publisher publisherInstance

    static constraints = {
        bookInstance nullable: false, validator: { value, obj -> value != null && value.validate()}
        authorInstance nullable: false, validator: { value, obj -> value != null && value.validate()}
        publisherInstance nullable: false, validator: { value, obj -> value != null && value.validate()}
    }

}
