<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="An application for classroom">
<meta name="author" content="Yash">
<script src="<c:url value="/jquery/jquery.js" />"></script>
<%
	String isInvalid = (String) request.getAttribute("isInvalid");
	String isFirstTime = (String) request.getAttribute("firstTime");
	System.out.println("jsp invalid login " + isInvalid);
	System.out.println("jsp first time " + isFirstTime);
%>
<script>
	$(document).ready(function() {
		isInvalid = "<%=isInvalid%>";
		isFirstTime = "<%=isFirstTime%>";
		if (isInvalid == "yes") {
			//alert("invalid login");
			writeResponse("your login credentials are wrong");
		}
	});
</script>
<script src="<c:url value="/myjs/websocket.js" />"></script>
<title>My classroom</title>
</head>
<body>
	<p id="debug"></p>
	<div id="wrapper">
		<h1>Welcome to the Class.</h1>
		<div id="content">
			<form id="loginForm" action="homeServlet" method="post">
				<label> username :</label> <input id="username" name="username" />
				<label> password :</label> <input type="password" id="password"
					name="password" /><br> <br> <br> <span>
					Login as : <select name="loginType" id="loginType">
						<option name="type" value="professor">professor</option>
						<option name="type" value="student">student</option>

				</select>
				</span> <br> <br> <span><input type="submit"
					class="button" value="Login" /></span>
			</form>
		</div>
	</div>
</body>
</html>