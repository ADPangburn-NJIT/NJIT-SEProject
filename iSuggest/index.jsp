<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<jsp:useBean id="generalMessage" class="java.util.ArrayList" scope="request"/>
	<jsp:useBean id="errorMessage" class="java.util.ArrayList" scope="request"/>
	<jsp:useBean id="user" class="DataStructures.User" scope="session"/>
	<jsp:useBean id="post" class="DataStructures.Post" scope="page"/>
	
	<%@ page import="Utils.TextUtils" %>
	<%@ page import="Utils.VerificationUtils" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="DataStructures.Post" %>
	<%@ page import="DataStructures.Category" %>
	<%@ page import="DataStructures.Role" %>
	
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>iSuggest</title>
	<link href="css/index.css" rel="stylesheet" type="text/css" />
	<link href="css/custom-theme/jquery-ui-1.9.2.custom.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/index.js"></script>
	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.9.2.custom.js"></script>
	<script>
	<% if (generalMessage.size() > 0) { %>
			 $(function() {
			        $( "#generalMessageDialog" ).dialog({ height: 200, width: 450, modal:true, 
			        	buttons: [
			        	          {
			        	        	  text: "Close",
			        	              click: function() { $(this).dialog("close"); }
			        	          }
			        	      ]		
			        });
			 });
	 <% } 
	   else if (errorMessage.size() > 0) { %>
		  	 $(function() {
		        $( "#errorMessageDialog" ).dialog({ height: 200, width: 350, modal:true, 
		        	buttons: [
		        	          {
		        	              text: "Back",
		        	              click: function() { parent.history.back(); }
		        	          },
		        	          {
		        	        	  text: "Close",
		        	              click: function() { $(this).dialog("close"); }
		        	          }
		        	      ]	
		        });
			 });
	 <% } %>
    </script>
   <script>
	  $(document).ready(function() {
	    $("button").button();
	  });
  </script>
 </head>
<% 
	int currentPage = 1;
	String category = request.getParameter("sortCategory");
	String search = request.getParameter("search");
	String popularity = request.getParameter("sortPopularity");
	String group = request.getParameter("sortRole");
	ArrayList activePosts = new ArrayList();
	activePosts = post.getActiveSuggestions(currentPage, category, group, popularity, search);
	if (request.getParameter("page") != null) { 
		currentPage = Integer.parseInt(request.getParameter("page"));
		activePosts = post.getActiveSuggestions(currentPage, category, group, popularity, search);
	}
%>

<body>
<div class="content">
    <div class="header">
        <table>
            <tr>
            	<td><img src="images/isuggest_banner.png"></img></td>
                <td class="loginCell">
                <% if (user.getUserId() == null) { %>
                    <form name="loginForm" id="loginForm" method="post" action="login">
                        Email: <input type="text" name="emailAddr" id="emailAddr" size="13" maxlength="60" onclick="this.select()" />
                        Password: <input type="password" name="password" id="password" size="10" maxlength="32" /><br />
                        <button type="button" onclick="showRegistrationBox();">Register</button>
                        <button type="submit">Login</button>                   
                    </form>
                <% } 
                else { %>
                	Logged in as <%= user.getEmailAddr() %>.
                	 <form name="logoutForm" id="logoutForm" method="post" action="logout">
                	 <% if ("99".equals(user.getUserType())) { %>
                	 	<button type="button" onclick="adminIndex();">Admin Home</button>
               	 	<% } 
               	 		else { %>
             	 		<button type="button" onclick="showMySuggestionsDialog();">My Suggestions</button>
             	 	<% } %>
                        <button type="submit">Logout</button>                   
                    </form>
                <% } %>
                </td>
            </tr>
        </table>
    </div>

    <div class="subHeaderContent"> 
		<button class="createSuggestionButton" type="button" <% if (user.getUserId() == null) { %> onclick="pleaseLogin();" <% } %>onclick="showCreateSuggestionDialog();">Create A Suggestion</button>       
        <form name="sortForm" id="sortForm" method="post" action="index.jsp">
        	<table class="categorySortTable">
		       	<tr>
		       		<th style="background-color:#CCCCFF" colspan="3">Filtering</th>
		       	</tr>
		       	<tr>
		       		<th style="background-color:#CCCCFF">Categories</th>
		       		<th style="background-color:#CCCCFF">Groups</th>
		       		<th style="background-color:#CCCCFF">Arrange By</th>
		       	</tr>
		       	<tr>
		       		<td>
		       			 <select id="sortCategory" name="sortCategory">
		        			<option value="All" <% if (request.getParameter("sortCategory") == null || "All".equals(request.getParameter("sortCategory"))) { %> selected="selected" <% } %>>All</option>
				        	<% for (int i = 0; i < VerificationUtils.getCategories().size(); i++) { 
								Category categoryList = (Category)VerificationUtils.getCategories().get(i);
							%>
								<option value="<%= categoryList.getCategory() %>" <% if (categoryList.getCategory().equals(request.getParameter("sortCategory"))) { %> selected="selected" <% } %>><%= categoryList.getCategory() %></option>
							<% } %>
				        </select>
		       		</td>
		       		<td>
		       			 <select id="sortRole" name="sortRole">
		        			<option value="All" <% if (request.getParameter("sortRole") == null || "All".equals(request.getParameter("sortRole"))) { %> selected="selected" <% } %>>All</option>
				        	<% for (int i = 0; i < VerificationUtils.getRoles().size(); i++) { 
								Role roleList = (Role)VerificationUtils.getRoles().get(i);
							%>
								<option value="<%= roleList.getRoleId() %>" <% if (roleList.getRoleId().equals(request.getParameter("sortRole"))) { %> selected="selected" <% } %>><%= roleList.getRole() %></option>
							<% } %>
				        </select>
		       		</td>
		       		<td>
		       			<select id="sortPopularity" name="sortPopularity">
			       			<option value="BestSuggestion" <% if (request.getParameter("sortPopularity") == null || "BestSuggestion".equals(request.getParameter("sortPopularity"))) { %> selected="selected" <% } %>>Best Suggestion</option>
			       			<option value="WorstSuggestion" <% if ("WorstSuggestion".equals(request.getParameter("sortPopularity"))) { %> selected="selected" <% } %>>Worst Suggestion</option>
		       				<option value="MostCommented" <% if ("MostCommented".equals(request.getParameter("sortPopularity"))) { %> selected="selected" <% } %>>Most Commented</option>
		       			</select>
		       		</td>
		       	</tr>
		       	<tr>
		       		<td colspan="3">
		       		    <button type="submit">Filter</button>
		       		</td>
		       	</tr>
        	</table>
        </form>
        <table class="searchTable">
            <tr>
            	<th style="background-color:#CCCCFF" colspan="2">Search</th>
            </tr>
            <tr>
                <td>
                    <form name="searchForm" id="searchForm" method="post" action="index.jsp">
                      <input style="font-size:20px;" type="text" id="search" name="search" size="30" maxlength="80" />
                    </form>
                </td>
            </tr>
            <tr>
            	<td>
            		<button type="button" onclick="document.searchForm.submit();">Search</button>
            	</td>
            </tr>
        </table>
    </div>
   <div class="suggestionPosts">
   		<table class="suggestionPostsHeader">
   			<tr>
   				<td <% if (currentPage > 1) { %> style="padding-right:230px;" <% } else { %> style="padding-right:265px;" <% } %>><% if (currentPage > 1) { %><a href="index.jsp?page=<%= currentPage - 1 %>&sortCategory=<%= category %>&search=<%= search %>&sortRole=<%= group %>&sortPopularity=<%= popularity %>"><img src="images/arrow_left.png" title="Back" /></a><% } %></td>
   				<td style="font-size:25px;">Newest Suggestions</td>
   				<td style="padding-left:230px;"><a href="index.jsp?page=<%= currentPage + 1 %>&sortCategory=<%= category %>&search=<%= search %>&sortRole=<%= group %>&sortPopularity=<%= popularity %>"><img src="images/arrow_right.png" title="Next"/></a></td>
   			</tr>
   		
   		</table>
    	<% 
    	if (activePosts.size() > 0) {
	    	for (int i = 0; i < activePosts.size(); i++) { 
	    		Post displayPost = (Post)activePosts.get(i);
	    	%>
		        <table class="suggestionPostsTable">
		        	<tr>
		            	<td class="suggestionPostsDataCell" style="text-align:left"><%= TextUtils.nullToZero(displayPost.getUser().getRole()) %></td>
		                <td class="suggestionPostsDataCell" style="text-align:left"></td>
		                <td class="suggestionPostsDataCell" style="text-align:right"><%= TextUtils.nullToZero(displayPost.getUser().getFirstName() + " " + TextUtils.nullToZero(displayPost.getUser().getLastName())) %></td>
		            </tr>
		        	<tr>
		            	 <td class="suggestionPostsDataCell" style="text-align:left">
		            	 	 <img src="images/thumbs-up-icon-flipped.png" alt="Thumbs Up" <% if (user.getUserId() != null) { if (VerificationUtils.canVoteSuggestion(displayPost.getPostId(), user.getUserId())) { %> onclick="voteSuggestion('<%= displayPost.getPostId() %>', '1', '<%= currentPage %>', '<%= category %>', '<%= group %>', '<%= popularity %>');" <% } else { %> onclick="alreadyVoted('<%= displayPost.getTitle() %>');" <% } } else { %> onclick="pleaseLogin();" <% } %>/><%= displayPost.getThumbsUp() %>
		            	 </td>
		            	<td class="suggestionPostsTitleCell" style="text-align:center"><a href="postDetails.jsp?postId=<%= TextUtils.nullToZero(displayPost.getPostId()) %>"><%= TextUtils.nullToZero(displayPost.getTitle()) %></a></td>
		                <td class="suggestionPostsDataCell" style="text-align:right">
		            	 	 <%= displayPost.getThumbsDown() %><img src="images/thumbs-down-icon.png" alt="Thumbs Down" <% if (user.getUserId() != null) { if (VerificationUtils.canVoteSuggestion(displayPost.getPostId(), user.getUserId())) { %> onclick="voteSuggestion('<%= displayPost.getPostId() %>', '-1', '<%= currentPage %>', '<%= category %>', '<%= group %>', '<%= popularity %>');" <% } else { %> onclick="alreadyVoted('<%= displayPost.getTitle() %>');" <% } } else { %> onclick="pleaseLogin();" <% } %>/>
		                </td>
		            </tr>
		        </table>
		<% } 
    	}
    	else { %>
    		<br />
    		<p style="margin-left:200px;">You've reached the end of the line. Congratulations.</p>
    	<% } %>
    </div>
    <br />
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
					<select id="userType" name="userType">
						<option value="1" selected="selected">Undergrad</option>
						<option value="2">Graduate</option>
						<option value="3">Alumni</option>
						<option value="4">Staff</option>
						<option value="5">Faculty</option>
					</select>
				</td>
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
				<td>
					<select id="category" name="category">
						<% for (int i = 0; i < VerificationUtils.getCategories().size(); i++) { 
							Category submissionCategoryList = (Category)VerificationUtils.getCategories().get(i);
						%>
							<option value="<%= submissionCategoryList.getCategory() %>"><%= submissionCategoryList.getCategory() %></option>
						<% } %>
					</select>
				</td>
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
<form name="voteSuggestionForm" id="voteSuggestionForm" method="post" action="voteSuggestion">
	<input type="hidden" name="currentPage" id="currentPage" />
	<input type="hidden" name="voteSuggestionPostId" id="voteSuggestionPostId" />
	<input type="hidden" name="vote" id="vote" />
	<input type="hidden" name="sortCategory" id="sortCategory" />
	<input type="hidden" name="sortRole" id="sortRole" />
	<input type="hidden" name="sortPopularity" id="sortPopularity" />
</form>

<div id="mySuggestionsDialog" style="display:none;" title="My Suggestions" style="overflow:auto;">
	<table class="myRejectedSuggestionsTable">
		<tr>
			<th colspan="3">Rejected Suggestions</th>
		</tr>
		<tr>
			<th>Title</th>
			<th>Description</th>
			<th>Date Submitted</th>
		</tr>
		<% for (int i = 0; i < VerificationUtils.getMyRejectedSuggestions(user.getUserId()).size(); i++) { 
		Post p = (Post)VerificationUtils.getMyRejectedSuggestions(user.getUserId()).get(i);
		%>
			<tr>
				<td>
					<%= p.getTitle() %>
				</td>
				<td>
				<% 
					if (p.getDescription().length() > 10) { 
				%>
					<%= p.getDescription().substring(0, 10) %>
				<% }
					else {
				%>
					<%= p.getDescription() %>
				<%
					}
				%>
				</td>
				<td>
					<%= p.getDate() %>
				</td>
			</tr>
		<% } %>
	</table>
	<table class="myActiveSuggestionsTable">
		<tr>
			<th colspan="4">Active Suggestions</th>
		</tr>
		<tr>
			<th>Title</th>
			<th>Description</th>
			<th>Date Submitted</th>
			<th>Go To Post</th>
		</tr>
		<% for (int i = 0; i < VerificationUtils.getMyAcceptedSuggestions(user.getUserId()).size(); i++) { 
		Post p = (Post)VerificationUtils.getMyAcceptedSuggestions(user.getUserId()).get(i);
		%>
			<tr>
				<td>
					<%= p.getTitle() %>
				</td>
				<td>
				<% 
					if (p.getDescription().length() > 10) { 
				%>
					<%= p.getDescription().substring(0, 10) + " ... " %>
				<% }
					else {
				%>
					<%= p.getDescription() %>
				<%
					}
				%>
				</td>
				<td>
					<%= p.getDate() %>
				</td>
				<td>
					<a href="postDetails.jsp?postId=<%= p.getPostId() %>">Click Here</a>
				</td>
			</tr>
		<% } %>
	</table>
	
</div>
</body>
</html>
