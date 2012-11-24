function showRegistrationBox() {
	$( "#registrationDialog" ).dialog({ height: 300, width: 500, modal: true });
}

function showCreateSuggestionDialog() {
	$( "#createSuggestionDialog" ).dialog({ height: 350, width: 640, modal: true });
}

function verifyRole() {
	var roleSelect = document.getElementById("userType");
	if (roleSelect.options[roleSelect.selectedIndex].value == "4" || roleSelect.options[roleSelect.selectedIndex].value == "5") {
		document.getElementById("hiddenRow").style.display="";
	}
	else {
		document.getElementById("hiddenRow").style.display="none";
	}
}

function register() {
	document.registrationForm.submit;
}

function createSuggestion() {
	document.createSuggestionForm.submit;
}

function showLeaveCommentBox() {
	$( "#leaveCommentDialog" ).dialog({ height: 350, width: 750, modal: true });
}

function leaveComment() {
	document.leaveCommentForm.submit;
}

function confirmAccept(postId) {
	var conf = confirm("Are you sure you want to accept this post? It will be moved to the main page where all users can see it.");
	if(conf == true){
		document.getElementById("acceptPostId").value = postId;
		document.acceptPostForm.submit();
	}
}

function confirmReject(postId) {
	var conf = confirm("Are you sure you want to reject this post? It will be moved to the archive and will not be displayed.");
	if(conf == true){
		document.getElementById("rejectPostId").value = postId;
		document.rejectPostForm.submit();
	}
}

function sortByCategory(category) {
	document.getElementById("sortCategory").value = category;
	document.sortByCategoriesForm.submit();
}