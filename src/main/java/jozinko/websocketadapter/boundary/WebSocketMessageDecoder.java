package jozinko.websocketadapter.boundary;

import jozinko.websocketadapter.entity.WebSocketMessage;

import javax.json.bind.JsonbBuilder;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class WebSocketMessageDecoder implements Decoder.Binary<WebSocketMessage> {
    @Override
    public WebSocketMessage decode(ByteBuffer byteBuffer) throws DecodeException {
        String stringMessage = Charset.forName("UTF-8").decode(byteBuffer).toString();
        return JsonbBuilder.create().fromJson(stringMessage, WebSocketMessage.class);
    }

    @Override
    public boolean willDecode(ByteBuffer byteBuffer) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
