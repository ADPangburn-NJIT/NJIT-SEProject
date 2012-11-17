function showRegistrationBox() {
	$( "#registrationDialog" ).dialog({ height: 300, width: 500, modal: true });
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