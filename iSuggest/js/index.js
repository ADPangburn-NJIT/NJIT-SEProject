function showRegistrationBox() {
	$( "#registrationDialog" ).dialog({ height: 300, width: 500, modal: true });
}

function showCreateSuggestionDialog() {
	$( "#createSuggestionDialog" ).dialog({ height: 350, width: 640, modal: true });
}

function showMySuggestionsDialog() {
	$( "#mySuggestionsDialog" ).dialog({ height: 400, width: 850, modal: true });
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

function sortByCategory() {
	var e = document.getElementById("sortByCategories");
	var category = e.options[e.selectedIndex].value;
	document.getElementById("sortCategory").value = category;
	document.sortByCategoriesForm.submit();
}

function sortByGroup(group) {
	document.sortByGroupForm.sortGroup.value = group;
	document.sortByGroupForm.submit();
}

function voteSuggestion(postId, vote, currentPage, category, group, popularity) {
	document.voteSuggestionForm.voteSuggestionPostId.value = postId;
	document.voteSuggestionForm.vote.value = vote;
	document.voteSuggestionForm.currentPage.value = currentPage;
	document.voteSuggestionForm.sortCategory.value = category;
	document.voteSuggestionForm.sortRole.value = group;
	document.voteSuggestionForm.sortPopularity.value = popularity;
	document.voteSuggestionForm.submit();
}

function pleaseLogin() {
	$( "#pleaseLoginDialog" ).dialog({ height: 200, width: 450, modal:true, 
    	buttons: [
    	          {
    	        	  text: "Close",
    	              click: function() { $(this).dialog("close"); }
    	          }
    	      ]		
    });
}

function alreadyVoted(title) {
	alert("You have already voted on suggestion: '" + title + "'");
}

function suggestionIndex() {
	window.location = "index.jsp";
}
function adminIndex() {
	window.location = "adminIndex.jsp";
}
function home() {
	window.location = "index.jsp";
}
function showCategoriesMenu() {
	document.getElementById("categoriesMenu").style.display="";
}
function hideCategoriesMenu() {
	document.getElementById("categoriesMenu").style.display="none";
}

function deleteComment(postId, commentId) {
	document.deleteCommentForm.commentId.value = commentId;
	document.deleteCommentForm.postId.value = postId;
	document.deleteCommentForm.submit();
}