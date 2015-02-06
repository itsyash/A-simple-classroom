window.onload = init;
var socket;

function onMessage(event) {
	//writeResponse("onMessage");
	var form = document.getElementById("leaveClass");
	form.elements["notes"].value = event.data;
}

function profSubmit() {
	var form = document.getElementById("submitNotes");
	var text = form.elements["notes"].value;
	//$("#debug").append("<p>" + text + " </p>");
	socket.send(text);
}

function init() {
	writeResponse("init");
	
	//if (isFirstTime == "yes") {
		//alert("first time");
		socket = new WebSocket("ws://localhost:8070/WebSocketHome/actions");
		socket.onmessage = onMessage;
//	}
}

function startClass() {
	socket.send("start");
}

function stopClass() {
	socket.send("stop");
}

function clearNotes() {
	document.getElementById("submitNotes").reset();
	socket.send("clear");
}

function writeResponse(text) {
	$("#debug").append("<p>" + text + "</p>");
}