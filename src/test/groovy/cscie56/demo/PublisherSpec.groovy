package cscie56.demo

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Publisher)
class PublisherSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll
    void "test constraints name:#name, dateEstablished:#dateEstablished, type:#type - should validate? #result"() {

        when: "I create a Publisher with the following values: #name, #dateEstablished, #type"
            Publisher p = new Publisher(name:name,dateEstablished:dateEstablished,type:type)

        then: "then the test should pass? #result"
            p.validate() == result

        where:
        name    | dateEstablished   | type      | result
        null    | null              | null      | false
        'a'     | new Date()        | 'Trade'   | true
        'a'     | new Date() +1     | 'Trade'   | false
        null    | new Date() -1     | 'Trade'   | false
        'a '    | new Date() -1     | 'Ha'      | false

    }
}
