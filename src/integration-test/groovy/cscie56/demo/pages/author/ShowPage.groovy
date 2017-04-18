package cscie56.demo.pages.author

import cscie56.demo.pages.ScaffoldPage

class ShowPage extends ScaffoldPage {

    static url = "author/show"

    static at = { id = 1 ->
        heading.text() ==~ /Show Author/
    }

    static content = {
        editButton(to: cscie56.demo.pages.author.EditPage) { $("a", text: "Edit") }
        deleteButton(to: cscie56.demo.pages.author.IndexPage) { $("input", value: "Delete") }

        row { $("li.fieldcontain span.property-label", text: it).parent() }
        value { row(it).find("span.property-value").text() }
        birthDate { value("Birth Date") }
        firstName { value("First Name") }
        lastName { value("Last Name") }
    }
}
