<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>
<body>

    <g:render template="/layouts/navbar" />
    <div id="auth_div" class="pull-right" style="padding-right:3px;">
        <sec:ifLoggedIn>
            Logged in as <sec:username/><g:form controller="logout" type="POST"><input class="btn btn-link" type="submit" value="Logout" /></g:form>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <g:link controller="login" action="index">Login</g:link>
        </sec:ifNotLoggedIn>
    </div>

    <g:layoutBody/>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
