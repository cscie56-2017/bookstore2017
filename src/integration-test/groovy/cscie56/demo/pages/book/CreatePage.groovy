package cscie56.demo.pages.book

import cscie56.demo.pages.ScaffoldPage

class CreatePage extends ScaffoldPage {

    static url = "book/create"

    static at = {
        title ==~ /Create.+/
    }

    static content = {
        createButton(to: ShowPage) { $("#create") }
    }

}