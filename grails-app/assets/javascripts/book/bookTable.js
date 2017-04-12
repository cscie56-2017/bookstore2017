$(function() {
	$('#bookModal').on('show.bs.modal', function (event) {
		console.log('bookModal fired!');
		var button = $(event.relatedTarget), // Button that triggered the modal
			bookId = button.data('book-id'), // Extract info from data-* attributes
			bookTitle = button.html();
		console.log(bookTitle);
		$('#bookModalLabel').html(bookTitle); // we already have the title, from the page itself, so just use it

		var request = $.ajax({
			//url:'/book/_show/'+bookId, // for HTML response
			url: '/book/show/' + bookId + '.json', // for JSON response
			method: 'GET'
		});
		request.done(function (data) {
			console.log("bookModal success - data = "+data);
			//$('#bookModalContent').html(data); //used for handling HTML response
			// the following are used when calling for json and processing the individual data members
			$('#book-isbn').html(data.isbn);
			console.log(data.isbn);
			$('#book-dateOfPublication').html(data.dateOfPublication);
			$('#book-authors').html(data.authors);
			if (data.authors) {
				var authorList = $('<ul id="authorList" style="list-style-type: none;"/>');
				$.each(data.authors, function(index, item) {
					authorList.append('<li><a href="/author/show/'+item.authorId+'">'+item.authorName+'</a></li>');
				});
                $('#book-authors').append(authorList);
			}
			$('#book-publisher').html('<a href="/publisher/show/'+data.publisherId+'">'+data.publisher+'</a>');
			$('#book-price').html(data.price);
			$('#book-genre').html(data.genre);
			$('#book-description').html(data.description);
			$('#book-delete-form').attr('action','/book/delete/'+data.id);
			$('#book-edit-link').attr('href','/book/edit/'+data.id);
		});
		request.fail(function (jqXHR, textStatus) {
			console.log("bookModal fail - jqXHR="+jqXHR + ", textStatus="+textStatus);
			$('#bookModalContent').html('<p>Could not retrieve details for ' + bookTitle + ' with id = ' + bookId + '<p>');
		});

	});
});