package cscie56.demo

import cscie56.demo.pages.LoginPage
import cscie56.demo.pages.book.IndexPage
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class BookCRUDSpec extends GebSpec {

    /**
     * Because CRUD operations require admin role, logging in as admin
     * @return
     */
    def setupSpec(){
        browser.config.reportsDir = new File('/build/reports/geb')
        this.baseUrl = "http://localhost:${serverPort}/"

    }


    def "warmup"(){
        when:
        go "/"
        then:
        title
    }

    def "there are books created in Bootstrap.groovy"() {
        when: "navigating to the Book index/list page"
        to cscie56.demo.pages.book.IndexPage

        then: "The user will see 10 books created by Bootstrap.groovy"
        bookRows.size() == 10 //Book.count()
    }

    def "add a book"() {
        given:
            to LoginPage
            $('#loginForm').username = "admin"
            $('#loginForm').password = "supersecret"
            $('#submit').click()
            waitFor {at cscie56.demo.pages.RootPage }
        when: "The user logs in and The Create Book button is clicked"
            to IndexPage
            newBookButton.click()
        then: "User will be directed to the Create Book page"
            at cscie56.demo.pages.book.CreatePage
    }

    def "enter the details and save a valid Book"() {
        given:"The user logs in and The Create Book button is clicked"
        to LoginPage
        $('#loginForm').username = "admin"
        $('#loginForm').password = "supersecret"
        $('#submit').click()
        waitFor {at cscie56.demo.pages.RootPage }

        to IndexPage
        newBookButton.click()

        when:"valid parameters for a Book are entered"
        //$("#visible").click()
        $('form').title = "Carrie"
        //$('form').
                isbn = "0987654321"

        authors = [1]
        $('form').dateOfPublication_day = "5"
        $('form').dateOfPublication_month = "4"
        $('form').dateOfPublication_year = "1974"
        $('form').genre = "Horror"
        $('#publisher').value(1)

        $('form').price = "2095"
        $("#create").click()
        then: "A successful save will redirect to Show Page"
        at cscie56.demo.pages.book.ShowPage
    }

    def "deleting the current instance"() {
        given:"navigate to a show instance page"
            go "/book/show/1"

        when:"I click delete"
            $("#delete").click()
            $("input[value*='Yes']").click()

        then:
            at cscie56.demo.pages.book.IndexPage
    }

    def "logging out" () {
        $("#logoutLink").click()
    }
}