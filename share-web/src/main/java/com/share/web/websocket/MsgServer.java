package com.share.web.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/Msg/point/{userId}/msg.html")
public class MsgServer {
	private Session session;
	private String userId;
	private static final Set<MsgServer> msgServers = new HashSet<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		this.session = session;
		this.userId = userId;
		msgServers.add(this);
	}

	@OnMessage
	public void onMessage(String msg) {
		if (msg.startsWith("#send")) {
			String targetId = msg.substring(5);
			for (MsgServer msgServer : msgServers) {
				if (msgServer.userId.equals(targetId)) {
					try {
						msgServer.session.getBasicRemote().sendText("#come");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
	}

	@OnClose
	public void onClose() {
		msgServers.remove(this);
	}
}
