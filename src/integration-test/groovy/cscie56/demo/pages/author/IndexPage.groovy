package cscie56.demo.pages.author

import cscie56.demo.pages.ScaffoldPage
import geb.Module

class IndexPage extends ScaffoldPage{

    static url = "author/index"

    static at = {
        title ==~ /AUTHOR List/
    }

    static content = {
        newAuthorButton(to: CreatePage) { $("a", text: "New Author") }
        authorTable { $("#authorTable") }
        authorRow { module AuthorRow, authorRows[it] }
        authorRows(required: false) { authorTable.find("tbody").find("tr") }
    }

}

class AuthorRow extends Module {
    static content = {
        cell { $("td", it) }
        cellText { cell(it).text() }
        cellHref { cell(it).find('a') }
        cellHrefText { cellHref(0).text() }
        fullName {cellHrefText(0) }
        birthDate { cellText(1) }
        showLink(to: ShowPage) { cell(0).find("a") }

    }
}

