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

    void "test copyright tag" () {
        expect:
        applyTemplate('<bookstore:copyright by="President and Fellows of Harvard University" to="2016" from="2012" />') == '<p>Copyright &copy; 2012-2016 President and Fellows of Harvard University</p>'
        applyTemplate('<bookstore:copyright by="President and Fellows of Harvard University" to="2016"  />') == '<p>Copyright &copy; 2016 President and Fellows of Harvard University</p>'
    }

    void "test copyright tag calls"() {
        expect:
        tagLib.copyright(by:"President and Fellows of Harvard University", to:"2016", from:"2012")         == '<p>Copyright &copy; 2012-2016 President and Fellows of Harvard University</p>'
        tagLib.copyright(by:"President and Fellows of Harvard University", to:"2016") == '<p>Copyright &copy; 2016 President and Fellows of Harvard University</p>'

    }

    void "test price tag" () {
        expect:
        applyTemplate('<bookstore:showPrice val="1999"  />') == '$19.99'
        applyTemplate('<bookstore:showPrice val="0" />') == '$0.00'
    }

    void "test price tag calls"() {
        expect:
        tagLib.showPrice(val:"1999")== '$19.99'
        tagLib.showPrice(val:"0") == '$0.00'

    }
}
