package cscie56.demo.pages

import geb.Page

/**
 * Created by michael on 4/17/16.
 */
class RootPage extends Page{
    static url = "/"

    static at = {
        title ==~ /Bookstore/
    }
}
