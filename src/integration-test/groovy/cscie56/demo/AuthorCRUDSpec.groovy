package cscie56.demo

import cscie56.demo.pages.author.IndexPage
import geb.spock.GebReportingSpec
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import spock.lang.*

@Integration
class AuthorCRUDSpec extends GebSpec {

    def setupSpec() {
        browser.config.reportsDir = new File('/build/reports/geb')
        this.baseUrl = "http://localhost:${serverPort}/"
    }

    def "warmup"(){
        when:
        go "/"
        then:
        title
    }

    def "There are 4 authors created"(){
        when:"visiting the author index"
            to IndexPage
        then:"there are 3 authors created in Boostrap"
            authorRows.size() ==4
    }
}
