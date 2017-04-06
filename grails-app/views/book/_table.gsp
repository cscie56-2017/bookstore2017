<asset:javascript src="application.js"></asset:javascript>
<table id="bookTable">
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
                    <ul>
                    <g:each in="${book.authors}" var="author">
                        <li>
                            ${author.fullName}
                        </li>
                    </g:each>
                    </ul>
                </td>
                <td>${book.publisher}</td>
                <td><g:formatDate date="${book.dateOfPublication}" /></td>
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
<script>

$('#bookModal').on('show.bs.modal', function (event) {
    console.log('bookModal fired!');
  var button = $(event.relatedTarget), // Button that triggered the modal
      bookId = button.data('book-id'), // Extract info from data-* attributes
      bookTitle = button.html();
  console.log(bookTitle);
  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
  var modal = $(this);
    var request = $.ajax({
        url:'/book/_show/'+bookId, // add +'?format=json' to get JSON response
        method:'GET'
    });
    request.done(function(data){
        console.log(data);
        $('#bookModalContent').html(data);
        // the following would be used if instead you were calling for json and processing the individual data members
       // $('#book-isbn').html(data.isbn);
       // $('#book-dateOfPublication').html(data.dateOfPublication);
       // $('#book-authors').html(data.authors);
       // $('#book-publisher').html(data.publisher);
       // $('#book-price').html(data.price);
       // $('#book-genre').html(data.genre);
    });
    request.fail(function(jqXHR,textStatus) {
        $('#results-div').html('<p>Could not retrieve details for id = ' + bookId +'<p>');
    });
$('#bookModalLabel').html(bookTitle);

});
</script>
