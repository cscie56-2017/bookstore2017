package cscie56.demo

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class ZZSecuritySpec extends GebSpec {

    def setupSpec() {
        this.baseUrl = "http://localhost:${serverPort}/"
    }

    def "warmup"(){
        when:
            go "/"
        then:
            title
    }

    def "Testing pages accessible without logging in"(){
        when:
        via cscie56.demo.pages.author.IndexPage
        then:
        at cscie56.demo.pages.author.IndexPage

        /*
        when:
        via ([id:1],cscie56.demo.pages.author.ShowPage)
        then:
        at cscie56.demo.pages.author.ShowPage
        */

        when:
        via cscie56.demo.pages.book.IndexPage
        then:
        at cscie56.demo.pages.book.IndexPage

    }

    def "testing pages inaccessible when not logged in"() {
        when:
        via cscie56.demo.pages.author.CreatePage
        then:
        at cscie56.demo.pages.LoginPage

        when:
        via cscie56.demo.pages.author.EditPage
        then:
        at cscie56.demo.pages.LoginPage

        when:
        via cscie56.demo.pages.book.CreatePage
        then:
        at cscie56.demo.pages.LoginPage

        when:
        via cscie56.demo.pages.book.EditPage
        then:
        at cscie56.demo.pages.LoginPage


    }
}
