<asset:javascript src="application.js"></asset:javascript>
<table id="bookTable" class="table  table-striped table-hover">
    <thead>
        <tr>
            <th>Title</th>
            <th>Authors</th>
            <th>Publisher</th>
            <th>Date of Publication</th>
            <th>ISBN</th>
            <th>Price</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${bookList}" var="book">
            <tr>
                <td>
                    <a href="#" data-book-id="${book.id}" data-toggle="modal" data-target="#bookModal">${book.title}</a>
                </td>
                <td>
                    <ul style="list-style-type: none;">
                    <g:each in="${book.authors}" var="author">
                        <li>
                            ${author.fullName}
                        </li>
                    </g:each>
                    </ul>
                </td>
                <td>${book.publisher}</td>
                <td><span style="display: none;"><g:formatDate date="${book.dateOfPublication}" format="yyyyMMdd"/></span><g:formatDate date="${book.dateOfPublication}" /></td>
                <td>${book.isbn}</td>
                <td>${book.priceFormatted}</td>
            </tr>
        </g:each>
    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="bookModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="bookModalLabel">Book title</h4>
            </div>
            <div class="modal-body">
                <div id="bookModalContent">
                    <g:render template="show" />
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="book/bookTable.js"></asset:javascript>
