package com.krestone.savealife.data.websockets;


import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import rx.Completable;

public class ClientWebSocket {

    private static final String TAG = "SocketListener";

    private static final int CONNECT_TIMEOUT_MS = 5000;

    private WebSocket webSocket;

    private MessageListener messageListener;

    private String host;

    public ClientWebSocket(MessageListener messageListener, String host) {
        this.messageListener = messageListener;
        this.host = host;
    }

    // blocks, shouldn't be called from main thread
    public Completable connect() {
        if (webSocket != null) {
            reconnect();
        } else {
            try {
                webSocket = createWebSocket();
                webSocket.addListener(new SocketListener());
                webSocket.connect();
            } catch (IOException e) {
                Log.i("onxConnectIOExc", e.getLocalizedMessage());
            } catch (WebSocketException e) {
                Log.i("onxConnectSocketExc", e.getLocalizedMessage());
            }
        }
        return Completable.complete();
    }

    private WebSocket createWebSocket() throws IOException {
        WebSocketFactory socketFactory = new WebSocketFactory();
        return socketFactory.createSocket(host, CONNECT_TIMEOUT_MS);
    }

    private void reconnect() {
        try {
            webSocket = webSocket.recreate().connect();
        } catch (WebSocketException | IOException e) {
            Log.i("onxReconnectExc", e.getLocalizedMessage());
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.disconnect();
        }
    }

    public WebSocket getConnection() {
        return webSocket;
    }

    private class SocketListener extends WebSocketAdapter {

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            super.onConnected(websocket, headers);
            Log.i(TAG, "onConnected");
        }

        public void onTextMessage(WebSocket websocket, String message) {
            messageListener.onSocketMessage(message);
            Log.i(TAG, "Message --> " + message);
        }

        @Override
        public void onError(WebSocket websocket, WebSocketException cause) {
            Log.i(TAG, "Error -->" + cause.getMessage());
            reconnect();
        }

        @Override
        public void onDisconnected(WebSocket websocket,
                                   WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                                   boolean closedByServer) {
            Log.i(TAG, "onDisconnected");
            if (closedByServer) {
                reconnect();
            }
        }

        @Override
        public void onUnexpectedError(WebSocket websocket, WebSocketException cause) {
            Log.i(TAG, "Error -->" + cause.getMessage());
            reconnect();
        }

        @Override
        public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            super.onPongFrame(websocket, frame);
            websocket.sendPing("Are you there?");
        }
    }
}
