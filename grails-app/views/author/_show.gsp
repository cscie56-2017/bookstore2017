
<ol class="property-list book">

<li class="fieldcontain">
    <span id="firstName-label" class="property-label">First Name</span>
    <div class="property-value" aria-labelledby="firstName-label" id="author-firstName">${author?.firstName}</div>
</li>

<li class="fieldcontain">
    <span id="middleName-label" class="property-label">Middle Name</span>
    <div class="property-value" aria-labelledby="middleName-label" id="author-middleName">${author?.middleName}</div>
</li>

<li class="fieldcontain">
    <span id="lastName-label" class="property-label">Last Name</span>
    <div class="property-value" aria-labelledby="lastName-label" id="author-lastName">${author?.lastName}</div>
</li>


<li class="fieldcontain">
    <span id="birthDate-label" class="property-label">Date of Birth</span>
    <div class="property-value" aria-labelledby="birthDate-label" id="author-birthDate">
        <g:formatDate date="${author?.birthDate}" />
    </div>
</li>

    <li class="fieldcontain">
        <span id="authors-label" class="property-label">Book${author?.books?.size()==1?'':'s'}</span>
        <div class="property-value" aria-labelledby="authors-label" id="author-books">
            <g:if test="${author?.books?.size()}">
                <ul style="list-style-type: none;">
                    <g:each in="${author?.books}" var="book">
                        <li><g:link controller="book" action="show" id="${book.id}">${book.title} (<g:formatDate date="${book.dateOfPublication}" format="yyyy"/>)</g:link> </li>
                    </g:each>
                </ul>
            </g:if>
        </div>
    </li>


</ol>

<sec:ifAnyGranted roles="ROLE_ADMIN">
    <form id="author-delete-form" action="/author/delete/${author?.id}" method="post" >
        <input type="hidden" name="_method" value="DELETE" id="_method" />
        <fieldset class="buttons">
            <a id="author-edit-link" href="/author/edit/${author?.id}" class="edit">Edit</a>
            <input class="delete" type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
        </fieldset>
    </form>
</sec:ifAnyGranted>
