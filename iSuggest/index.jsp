<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:useBean id="generalMessage" class="java.util.ArrayList" scope="request"/>
	<jsp:useBean id="errorMessage" class="java.util.ArrayList" scope="request"/>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>iSuggest</title>
	<link href="css/index.css" rel="stylesheet" type="text/css" />
	<link href="css/redmond/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.9.1.custom.js"></script>
	<script>
	
	<!--
		General Messages and Error Messages are responses sent from the system to the user.
		The dialog boxes are hidden by default.
		When the page is loaded it checks the request to see if there is a generalMessage or an errorMessage.
		If they exist then we use jQuery to unhide the dialog and show it to the user.
	-->
	<% if (generalMessage.size() > 0) { %>
			 $(function() {
			        $( "#generalMessageDialog" ).dialog();
			 });
	 <% } 
	   else if (errorMessage.size() > 0) { %>
		  	 $(function() {
		        $( "#errorMessageDialog" ).dialog();
			 });
	 <% } %>
    </script>
</head>

<body>
<div class="content">
    <div class="header">
        <table>
            <tr>
                <td class="logoCell">
                    <img src="images/iSuggest_logo.jpg" alt="iSuggest logo this website can make suggestions for university" width="112" height="66" />
                </td>
                <td class="titleCell">
                    <h1>iSuggest</h1>
              </td>
                <td class="loginCell">
                    <form name="loginForm" id="loginForm" method="post" action="login">
                        UserName: <input type="text" name="username" id="username" size="15" maxlength="40" />
                        Password: <input type="password" name="password" id="password" size="10" maxlength="40" /><br />
                        <button type="button" onclick="showRegistrationBox();">Register</button>
                        <button type="submit">Login</button>                   
                    </form>
                </td>
            </tr>
        </table>
    </div>

    <div class="suggestionsByCategory"> 
		<button class="createSuggestionButton" type="button">Create A Suggestion</button>       
        <table>
            <tr>
                <td class="searchCell">
                    <form name="searchForm" id="searchForm" method="post" action="">
                      <input style="font-size:20px;" type="text" value="Search" size="40" maxlength="80" />
                    </form>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td class="sortByCategoriesCell">
                    <button type="button">Facilities</button>
                </td>
                <td class="sortByCategoriesCell">
                    <button type="button">Activities</button>
                </td>
                <td class="sortByCategoriesCell">
                    <button type="button">Entertainment</button>
                </td>
                <td class="sortByCategoriesCell">
                    <button type="button">Commuting</button>
                </td>
                <td class="sortByCategoriesCell">
                    <button type="button">Campus Life</button>
                </td>
            </tr>
        </table>
    </div>
    
    <div class="suggestionsByRoles">
        <h3 style="font-size:15px; text-align:center;">Sort by Group</h3>
        <table>
            <tr>
                <td class="sortByGroupCell">
                    <button type="button">All</button>
                </td>
            </tr>
            <tr>
                <td class="sortByGroupCell">
                    <button type="button">Undergrads</button>
                </td>
            </tr>
            <tr>
                <td class="sortByGroupCell">
                    <button type="button">Grad</button>
                </td>
            </tr>
            <tr>
                <td class="sortByGroupCell">
                    <button type="button">Alumni</button>
                </td>
            </tr>
            <tr>
                <td class="sortByGroupCell">
                    <button type="button">Faculty</button>
                </td>
            </tr>
        </table>
    </div>
    
    <div class="suggestionPosts">
    	<h1>Newest Suggestions</h1>
        <table>
        	<tr>
            	<td>Designated quiet study rooms</td>
            </tr>
            <tr>
            	<td>Weekend Activities</td>
            </tr>
        </table>
    </div>
    
    <div class="footer">©NJIT iSuggest System - 2012</div>
</div>

<div style="display:none;" id="registrationDialog" title="Register for iSuggest">
	<form name="registrationForm" id="registrationForm" method="post" action="register">
		<table id="registrationTable">
			<tr>
				<td>E-mail</td>
				<td><input type="text" name="emailAddr" id="emailAddr" size="30" maxlength="60" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="text" name="password" id="password" size="30" maxlength="20" /></td>
			</tr>
			<tr>
				<td>First Name</td>
				<td><input type="text" name="firstName" id="firstName" size="30" maxlength="20" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><input type="text" name="lastName" id="lastName" size="30" maxlength="20" /></td>
			</tr>
			<tr>
				<td><button onclick="register();">Register</button></td>
			</tr>
		</table>
	</form>
</div>
<div style="display:none;" id="generalMessageDialog" title="SUCCESS">
	<table>
		<% for (int i = 0; i < generalMessage.size(); i++) { %>
			<tr>
				<td><%= (String)generalMessage.get(i) %></td>
			</tr>
		<% } %>
	</table>
</div>
<div style="display:none;" id="errorMessageDialog" title="ERROR">
	<table>
		<% for (int i = 0; i < errorMessage.size(); i++) { %>
			<tr>
				<td><font color="red"><%= (String)errorMessage.get(i) %></font></td>
			</tr>
		<% } %>
	</table>
</div>
</body>
</html>
