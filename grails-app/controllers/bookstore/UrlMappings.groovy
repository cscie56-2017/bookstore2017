package bookstore

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/books"(resources:"book") {
            "/publishers"(resources:"publisher")
            "/authors"(resources:"author")
        }
        "/authors"(resources:"author")
    	"/publishers"(resources:"publisher")

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
