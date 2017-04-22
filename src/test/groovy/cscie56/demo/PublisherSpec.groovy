package cscie56.demo

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Publisher)
class PublisherSpec extends Specification {

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

    @Unroll
    void "test String representations: for #name: #result; #resultLong" () {
        when:
            Publisher p = new Publisher(name:name,dateEstablished:date,type:"Trade")

        then:
            result == p.toString()
            resultLong == p.toStringLong()

        where:
        name    | date          | result | resultLong
        "name"  | new Date()    | "name" | "name, established ${new Date().format("yyyy")}"
    }
}
