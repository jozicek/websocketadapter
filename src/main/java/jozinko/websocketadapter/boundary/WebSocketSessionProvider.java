package jozinko.websocketadapter.boundary;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class WebSocketSessionProvider {

    private Session session;

    public Session provide() {
        return session;
    }

    public void setSession(Session session) {
        if (this.session != null) {
            throw new IllegalStateException("Cant initialize more then one session");
        }
        this.session = session;
    }
}
