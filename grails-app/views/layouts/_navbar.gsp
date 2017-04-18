<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/#">
                <i class="fa grails-icon">
                    <asset:image src="book_PNG2118.png" alt="Book logo"/>
                </i> Bookstore
            </a>
        </div>
        <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
            <ul class="nav navbar-nav">
                <li>
                    <g:link controller="book">books</g:link>
                </li>
                <li>
                    <g:link controller="author">authors</g:link>
                </li>
                <li>
                    <g:link controller="publisher">publishers</g:link>
                </li>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                <li>
                    <g:link controller="springBeanDemo">springBeanDemo</g:link>
                </li>
                </sec:ifAnyGranted>
                <sec:ifLoggedIn>
                    <li>
                        <a>Logged in as <sec:username/></a>
                    </li>
                    <li><a>
                        <g:form controller="logout" type="POST"><input id="logoutLink" class="btn btn-link" style="color: white !important;" type="submit" value="logout" /></g:form>
                        </a>
                    </li>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li>
                    <g:link id="loginLink" controller="login" action="index">login</g:link>
                    </li>
                </sec:ifNotLoggedIn>

            <%--
            <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                <g:if test="${!c?.logicalPropertyName?.contains("log")}">
                <li class="controller">
                    <g:link controller="${c.logicalPropertyName}">${c.logicalPropertyName}s</g:link>
                </li>
                </g:if>
            </g:each>
            --%>
            </ul>
        </div>
    </div>
</nav>