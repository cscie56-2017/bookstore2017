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
                    <asset:image src="grails-cupsonly-logo-white.svg" alt="Grails cup logo"/>
                </i> Grails
            </a>
        </div>
        <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
            <ul class="nav navbar-nav">
                <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                    <g:if test="${!c?.logicalPropertyName?.contains("log")}">
                    <li class="controller">
                        <g:link controller="${c.logicalPropertyName}">${c.logicalPropertyName}s</g:link>
                    </li>
                    </g:if>
                </g:each>
            </ul>
        </div>
    </div>
</nav>