package jozinko.websocketadapter.boundary;

import jozinko.websocketadapter.entity.WebSocketMessage;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.BlockingDeque;


@ServerEndpoint(value = "/websocket", decoders = {WebSocketMessageDecoder.class})
public class WebSocketAdapterServerEndpoint {

    @Inject
    private WebSocketSessionProvider webSocketSessionProvider;

    @OnOpen
    public void onOpen(final Session session) {
        System.out.println("WEBSOCKETADAPTER, onOpen: sessionID = " + session.getId());
        webSocketSessionProvider.setSession(session);
    }

    @OnMessage
    public void onMessage(final WebSocketMessage message, final Session session) {
        System.out.println("WEBSOCKETADAPTER, onMessage: " + message);
        ((BlockingDeque<WebSocketMessage>) session.
                getUserProperties().get(message.id)).add(message);
    }
}
