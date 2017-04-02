package cscie56.demo

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured([Role.ROLE_USER,Role.ROLE_ADMIN,Role.ROLE_ANONYMOUS])
class SpringBeanDemoController {

    def springBeanDemo

    def index() {
        render springBeanDemo as JSON
    }
}
