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