package com.krestone.savealife.data.websockets;


import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SocketTest {

    private ClientWebSocket webSocket;

    public SocketTest() {
        webSocket = new ClientWebSocket(message -> Log.i("onxMessage", message), "ws://10.0.1.37:8080/chat");
    }

    public void connect() {
        webSocket.connect()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                () -> Log.i("onxConnected", "completed"),
                throwable -> Log.i("onxErr", throwable.getLocalizedMessage())
        );
    }

    public void sendMessage(String message) {
        webSocket.getConnection().sendText(message);
    }

    public void closeSocket() {
        webSocket.close();
    }
}
