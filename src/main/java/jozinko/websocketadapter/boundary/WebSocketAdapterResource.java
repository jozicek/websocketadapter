package jozinko.websocketadapter.boundary;

import jozinko.websocketadapter.entity.WebSocketMessage;

import javax.inject.Inject;
import javax.websocket.Session;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Path("mock")
public class WebSocketAdapterResource {

    @Inject
    private WebSocketSessionProvider sessionProvider;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(String requestMessage) throws IOException, InterruptedException {
        final WebSocketMessage message = new WebSocketMessage(UUID.randomUUID().toString(), requestMessage);
        System.out.println("WEBSOCKETADAPTER, request: " + requestMessage);
        final Session session = sessionProvider.provide();
        if (session == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No active websocket session").build();
        }
        session.getUserProperties().put(message.id, new LinkedBlockingDeque<WebSocketMessage>());
        session.getBasicRemote().sendBinary(Charset.forName("UTF-8").encode(message.toString()));
        WebSocketMessage responseMessage = ((BlockingDeque<WebSocketMessage>) session.
                getUserProperties().get(message.id)).take();
        System.out.println("WEBSOCKETADAPTER, response: " + responseMessage.message);
        return Response.ok(responseMessage).build();
    }

}
