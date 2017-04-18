$(function() {
	$('#authorModal').on('show.bs.modal', function (event) {
		console.log('authorModal fired!');
		var button = $(event.relatedTarget), // Button that triggered the modal
			authorId = button.data('author-id'), // Extract info from data-* attributes
			authorFullName = button.html();
		console.log(authorFullName);
		$('#authorModalLabel').html(authorFullName); // we already have the author's name, from the page itself, so just use it

		var request = $.ajax({
			//url:'/book/_show/'+bookId, // for HTML response
			url: '/author/show/' + authorId + '.json', // for JSON response
			method: 'GET'
		});
		request.done(function (data) {
			console.log("authorModal success - data = "+data);
			//$('#bookModalContent').html(data); //used for handling HTML response
			// the following are used when calling for json and processing the individual data members
			$('#author-birthDate').html(data.birthDate);
			$('#author-books').html(data.books);
			if (data.books) {
				var bookList = $('<ul id="bookList" style="list-style-type: none;"/>');
				$.each(data.books, function(index, item) {
                    bookList.append('<li><a href="/book/show/'+item.bookId+'">'+item.bookTitle+' ('+item.bookPublicationYear+')</a></li>');
				});
                $('#author-books').append(bookList);
			}
			$('#author-firstName').html(data.firstName);
			$('#author-middleName').html(data.middleName);
			$('#author-lastName').html(data.lastName);
			$('#author-delete-form').attr('action','/author/delete/'+data.id);
			$('#author-edit-link').attr('href','/author/edit/'+data.id);
		});
		request.fail(function (jqXHR, textStatus) {
			console.log("authorModal fail - jqXHR="+jqXHR + ", textStatus="+textStatus);
			$('#authorModalContent').html('<p>Could not retrieve details for ' + authorFullName + ' with id = ' + authorId + '<p>');
		});

	});
});