package cscie56.demo.pages.author

import cscie56.demo.pages.ScaffoldPage

class CreatePage  extends ScaffoldPage {

    static url = "author/create"

    static at = {
        title ==~ /Create.+/
    }

    static content = {
        createButton(to: ShowPage) { $("#create") }
    }
}
