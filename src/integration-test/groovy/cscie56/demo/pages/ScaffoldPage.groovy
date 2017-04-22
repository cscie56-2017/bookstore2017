package cscie56.demo.pages

import geb.Page

class ScaffoldPage extends Page {



    static content = {
        heading { $("h1") }
        message { $("div.message").text() }
        loginLink (to: LoginPage) { $("#loginLink") }
        logoutLink { $("#logoutLink") }
    }
}