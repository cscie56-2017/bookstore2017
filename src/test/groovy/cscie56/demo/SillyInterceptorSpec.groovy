package cscie56.demo


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SillyInterceptor)
class SillyInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test silly interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"book")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }

    void "Test whether joke is there" () {
        when: "A request matches the interceptor"
            withRequest(controller:"book")
            interceptor.before()
        then:
            if (interceptor?.flash?.message) {
                interceptor?.badJokes?.contains(interceptor.flash.message)
            }

        expect:
            interceptor.after()
    }
}
