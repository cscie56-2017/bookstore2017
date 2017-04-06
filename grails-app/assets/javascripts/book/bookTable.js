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
			url: '/book/_show/' + bookId + '?format=json', // for JSON response
			method: 'GET'
		});
		request.done(function (data) {
			console.log(data);
			//$('#bookModalContent').html(data); //used for handling HTML response
			// the following are used when calling for json and processing the individual data members
			$('#book-isbn').html(data.isbn);
			console.log(data.isbn);
			$('#book-dateOfPublication').html(data.dateOfPublication);
			$('#book-authors').html(data.authors);
			$('#book-publisher').html(data.publisher);
			$('#book-price').html(data.price);
			$('#book-genre').html(data.genre);
		});
		request.fail(function (jqXHR, textStatus) {
			$('#bookModalContent').html('<p>Could not retrieve details for ' + bookTitle + ' with id = ' + bookId + '<p>');
		});

	});
});