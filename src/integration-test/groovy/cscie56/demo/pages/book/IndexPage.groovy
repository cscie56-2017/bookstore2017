package cscie56.demo.pages.book

import cscie56.demo.pages.ScaffoldPage
import geb.Module

class IndexPage extends ScaffoldPage {

    static url = "book/index"

    static at = {
        title
    }

    static content = {
        newBookButton(to: CreatePage) { $("a", text: "New Book") }
        bookTable { $("#bookTable") }
        bookRow { module BookRow, bookRows[it] }
        bookRows(required: false) { bookTable.find("tbody").find("tr") }
    }
}

class BookRow extends Module {
    static content = {
        cell { $("td", it) }
        cellText { cell(it).text() }
        cellHref{ cell(it).find('a') }
        cellHrefText{ cellHref(it).text() }
        title { cellHrefText(0) }
        authors { cellText(1)}
        publisher { cellText(2) }
        dateOfPublication { cellText(3) }
        isbn {cellText(4) }
        price {cellText(5) }
        showLink(to: ShowPage) { cell(0).find("a") }
    }
}