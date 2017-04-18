<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css" />
    </head>
    <body>
        <a href="#list-book" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                </sec:ifAnyGranted>
            </ul>
        </div>
        <div id="list-book" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <%--<f:table collection="${bookList}" properties="['title','authors','publisher','dateOfPublication','isbn','priceFormatted']"/>
            --%>
            <g:render template="table" model="[bookList:bookList]" />

        </div>
        <script src="//cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
        <script>
            $('#bookTable').DataTable();
        </script>
    </body>
</html>