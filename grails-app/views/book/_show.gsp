
<ol class="property-list book">
    <li class="fieldcontain">
        <span id="authors-label" class="property-label">Author${book?.authors?.size()==1?'':'s'}</span>
        <div class="property-value" aria-labelledby="authors-label" id="book-authors">
            <g:if test="${book?.authors?.size()}">
                <ul style="list-style-type: none;">
                    <g:each in="${book?.authors}" var="author">
                        <li><g:link controller="author" action="show" id="${author.id}">${author.fullName}</g:link> </li>
                    </g:each>
                </ul>
            </g:if>
        </div>
    </li>

    <li class="fieldcontain">
        <span id="description-label" class="property-label">Description</span>
        <div class="property-value" aria-labelledby="description-label" id="book-description">${book?.description}</div>
    </li>

    <li class="fieldcontain">
        <span id="dateOfPublication-label" class="property-label">Date Of Publication</span>
        <div class="property-value" aria-labelledby="dateOfPublication-label" id="book-dateOfPublication">
            <g:formatDate date="${book?.dateOfPublication}" />
        </div>
    </li>

    <li class="fieldcontain">
        <span id="price-label" class="property-label">Price</span>
        <div class="property-value" aria-labelledby="price-label" id="book-price">${book?.priceFormatted}</div>
    </li>

    <li class="fieldcontain">
        <span id="genre-label" class="property-label">Genre</span>
        <div class="property-value" aria-labelledby="genre-label" id="book-genre">${book?.genre}</div>
    </li>

    <li class="fieldcontain">
        <span id="publisher-label" class="property-label">Publisher</span>
        <div class="property-value" aria-labelledby="publisher-label" id="book-publisher">
            <g:link controller="publisher" action="show" id="${book?.publisher?.id}">${book?.publisher}</g:link>
        </div>
    </li>

    <li class="fieldcontain">
        <span id="isbn-label" class="property-label">Isbn</span>
        <div class="property-value" aria-labelledby="isbn-label" id="book-isbn">${book?.isbn}</div>
    </li>

</ol>

<sec:ifAnyGranted roles="ROLE_ADMIN">
    <form id="book-delete-form" action="/book/delete/${book?.id}" method="post" >
        <input type="hidden" name="_method" value="DELETE" id="_method" />
        <fieldset class="buttons">
            <a id="book-edit-link" href="/book/edit/${book?.id}" class="edit">Edit</a>
            <input class="delete" type="submit" value="Delete" onclick="return confirm('Are you sure?');" />
        </fieldset>
    </form>
</sec:ifAnyGranted>
