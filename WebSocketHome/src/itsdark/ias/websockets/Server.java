package itsdark.ias.websockets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions")
public class Server {

	private static final Set<Session> sessions = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void open(Session session) {
		System.out.println("session open");
		sessions.add(session);
		System.out.println("sessions : " + sessions.size());
	}

	@OnClose
	public void close(Session session) {
		System.out.println("session close");
		sessions.remove(session);
	}

	@OnError
	public void onError(Throwable error) {
		Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, error);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		System.out.println("in handle message");
		if (message.equalsIgnoreCase("start")) {
			Globals.classGoing = true;
		} else if (message.equalsIgnoreCase("stop")) {
			Globals.classGoing = false;
		} else {
			sendToAllConnectedSessions(session, message);
		}
	}

	private void sendToAllConnectedSessions(Session current, String message) {
		for (Session session : sessions) {
			if (current.equals(session)) {

			} else {
				sendToSession(session, message);
			}
		}
	}

	private void sendToSession(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {
			sessions.remove(session);
			Logger.getLogger(Server.class.getName())
					.log(Level.SEVERE, null, ex);
		}
	}
}
