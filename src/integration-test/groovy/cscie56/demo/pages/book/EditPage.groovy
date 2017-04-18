package cscie56.demo.pages.book

import cscie56.demo.pages.ScaffoldPage

class EditPage extends ScaffoldPage {

    static url = "book/edit"

    static at = {
        heading.text() ==~ /Edit.+/
    }

    static content = {
        updateButton(to: ShowPage) { $("input", value: "Update") }
        deleteButton(to: IndexPage) { $("input", value: "Delete") }
    }

}