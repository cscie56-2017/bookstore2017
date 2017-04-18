package cscie56.demo.pages.book

import cscie56.demo.pages.ScaffoldPage

class ShowPage extends ScaffoldPage {

    static url = "book/show"

    static at = { id = 1 ->
        heading.text() ==~ /Show Book/
    }

    static content = {
        editButton(to: cscie56.demo.pages.book.EditPage) { $("a", text: "Edit") }
        deleteButton(to: cscie56.demo.pages.book.IndexPage) { $("input", value: "Delete") }

        row { $("li.fieldcontain span.property-label", text: it).parent() }
        value { row(it).find("span.property-value").text() }
        datePublished { value("Date Published") }
        bookTitle { value("Title") }
        isbn { value("Isbn") }
        price { value("Price") }
    }
}