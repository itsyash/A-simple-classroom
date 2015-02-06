<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="An application for classroom">
<meta name="author" content="Yash">
<title>My classroom</title>
<script src="<c:url value="/myjs/websocket.js" />"></script>
<script src="<c:url value="/jquery/jquery.js" />"></script>
</head>
<body>
	<p id="debug"></p>
	<div id="wrapper">
		<h1>Student's Screen</h1>
		<p>Welcome to the Class.</p>
		<br />
		<div id="content">
			<form id="leaveClass">
				<h3>Write some notes</h3>
				<textarea name="description" id="notes" rows="10" cols="50"></textarea>
				<input type="button" class="button" value="Leave Class"
					onclick=studSubmit() />
			</form>
		</div>
		<br />
	</div>
</body>
</html>