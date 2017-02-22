package cscie56.demo

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(FormatTagLib)
class FormatTagLibSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test tag" () {
        expect:
        applyTemplate('<bookstore:copyright by="President and Fellows of Harvard University" to="2016" from="2012" />') == '<p>Copyright &copy; 2012-2016 President and Fellows of Harvard University</p>'
        applyTemplate('<bookstore:copyright by="President and Fellows of Harvard University" to="2016"  />') == '<p>Copyright &copy; 2016 President and Fellows of Harvard University</p>'
    }

    void "test tag calls"() {
        expect:
        tagLib.copyright(by:"President and Fellows of Harvard University", to:"2016", from:"2012")         == '<p>Copyright &copy; 2012-2016 President and Fellows of Harvard University</p>'
        tagLib.copyright(by:"President and Fellows of Harvard University", to:"2016") == '<p>Copyright &copy; 2016 President and Fellows of Harvard University</p>'

    }
}
