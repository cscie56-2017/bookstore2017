<asset:javascript src="application.js"></asset:javascript>
<table id="authorTable" class="table  table-striped table-hover">
    <thead>
        <tr>
            <th>Name</th>
            <th>Date of Birth</th>
            <th>Books</th>
        </tr>
    </thead>
    <tbody>
        <g:each in="${authorList}" var="author">
            <tr>
                <td>
                    <a href="#" data-author-id="${author.id}" data-toggle="modal" data-target="#authorModal">${author.fullName}</a>
                </td>
                <td><span style="display: none;"><g:formatDate date="${author.birthDate}" format="yyyyMMdd"/></span><g:formatDate date="${author.birthDate}" /></td>
                <td>
                    <ul style="list-style-type: none;">
                    <g:each in="${author.books}" var="book">
                        <li>
                            ${book.title} (<g:formatDate date="${book.dateOfPublication}" format="yyyy" />)
                        </li>
                    </g:each>
                    </ul>
                </td>
            </tr>
        </g:each>
    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="authorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="authorModalLabel">Author Name</h4>
            </div>
            <div class="modal-body">
                <div id="authorModalContent">
                    <g:render template="show" />
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="author/authorTable.js"></asset:javascript>
