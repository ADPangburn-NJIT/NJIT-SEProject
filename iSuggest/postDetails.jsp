<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ page import="Utils.TextUtils" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="DataStructures.Post" %>
	<%@ page import="DataStructures.User" %>
	<%@ page import="DataStructures.Comments" %>
	
	<jsp:useBean id="generalMessage" class="java.util.ArrayList" scope="request"/>
	<jsp:useBean id="errorMessage" class="java.util.ArrayList" scope="request"/>
	<jsp:useBean id="user" class="DataStructures.User" scope="session"/>
	<jsp:useBean id="comments" class="DataStructures.Comments" scope="page"/>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>iSuggest</title>
	<link href="css/postDetails.css" rel="stylesheet" type="text/css" />
	<link href="css/redmond/jquery-ui-1.9.1.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.9.1.custom.js"></script>
	<script>
	
	<% if (generalMessage.size() > 0) { %>
			 $(function() {
			        $( "#generalMessageDialog" ).dialog({ height: 200, width: 450, modal:true });
			 });
	 <% } 
	   else if (errorMessage.size() > 0) { %>
		  	 $(function() {
		        $( "#errorMessageDialog" ).dialog({ height: 200, width: 350, modal:true });
			 });
	 <% } %>
    </script>
</head>

<% 
	ArrayList activeComments = new ArrayList();
	if (request.getParameter("postId") != null) { 
		activeComments = comments.getActiveComments(Integer.parseInt(request.getParameter("postId")));
	}
%>
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
                <% if (user.getUserId() == null) { %>
                    <form name="loginForm" id="loginForm" method="post" action="login">
                        Email: <input type="text" name="emailAddr" id="emailAddr" size="15" maxlength="60" />
                        Password: <input type="password" name="password" id="password" size="10" maxlength="32" /><br />
                        <button type="button" onclick="showRegistrationBox();">Register</button>
                        <button type="submit">Login</button>                   
                    </form>
                <% } 
                else { %>
                	Logged in as <%= user.getEmailAddr() %>.
                	 <form name="logoutForm" id="logoutForm" method="post" action="logout">
                        <button type="submit">Logout</button>                   
                    </form>
                <% } %>
                </td>
            </tr>
        </table>
    </div>

    <div class="suggestionsByCategory"> 
		<button class="createSuggestionButton" type="button" <% if (user.getUserId() == null) { %> disabled="disabled" <% } %>onclick="showCreateSuggestionDialog();">Create A Suggestion</button>       
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

   <div class="suggestionPosts">
   		<table class="suggestionPostsTable">
        	<tr>
            	<td>
                	<h2 class="suggestionPostsHeader"><%= comments.getPost().getTitle() %></h2>
                </td>
            </tr>
            <tr>
            	<td>
                	 <h2 class="commentDescription"><%= comments.getPost().getDescription() %></h2>
                </td>
                
           	</tr>
        </table>
    </div>
    <div class="leaveComment">
    	<button type="button"  <% if (user.getUserId() == null) { %> disabled="disabled" <% } %> onclick="showLeaveCommentBox();">Leave Comment</button>
    </div>
    <div class="commentsSection">
   		<table>
        	<% for (int i = 0; i < activeComments.size(); i++) {
        		Comments comment = (Comments)activeComments.get(i);
        	%>
        		<tr>
        			<td><%= comment.getCommentText() %></td>
        			<td><%= comment.getUser().getFirstName() + " " + comment.getUser().getLastName() %></td>
        		</tr>
        	<% } %>
        </table>
    </div>
    <div class="footer">iSuggest System - 2012</div>
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
				<td>Role</td>
				<td>
					<select id="userType" name="userType" onchange="verifyRole();">
						<option value="1" selected="selected">Undergrad</option>
						<option value="2">Graduate</option>
						<option value="3">Alumni</option>
						<option value="4">Staff</option>
						<option value="5">Faculty</option>
					</select>
				</td>
			</tr>
			<tr id="hiddenRow" style="display:none;">
				<td>Verification</td>
				<td><input type="text" name="verification" id="verification" size="30" maxlength="20" value="Enter Empoyee ID" onclick="this.select()" /></td>
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
<div style="display:none;" id="createSuggestionDialog" title="Create Suggestion">
	<form name="createSuggestionForm" id="createSuggestionForm" method="post" action="createSuggestion">
		<table>
			<tr>
				<td>Title</td>
				<td><input type="text" id="title" name="title" size="53" maxlength="60" /></td>
			</tr>
			<tr>
				<td>Category</td>
				<td><input type="text" id="category" name="category" size="53" maxlength="60" /></td>
			</tr>
			<tr>
				<td>Description</td>
				<td><textarea name="description" id="description" rows="5" cols="51"></textarea></td>
			</tr>
			<tr align="center">
				<td colspan="2"><button onclick="createSuggestion();">Create Suggestion</button></td>
			</tr>
		</table>
	</form>
</div>
<div style="display:none;" id="leaveCommentDialog" title="Leave Comment">
	<form name="leaveCommentForm" id="leaveCommentForm" method="post" action="leaveComment">
		<input type="hidden" name="postId" id="postId" value="<%= request.getParameter("postId") %>" />
		<table>
			<tr>
				<td>
					Comment: <br />
					150 Character Max
				</td>
				<td><textarea name="commentText" id="commentText" rows="5" cols="51"></textarea></td>
			</tr>
			<tr align="center">
				<td colspan="2"><button onclick="leaveComment();">Leave Comment</button></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
