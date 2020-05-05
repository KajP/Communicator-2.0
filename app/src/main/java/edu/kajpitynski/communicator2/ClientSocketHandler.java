package edu.kajpitynski.communicator2;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocketHandler {
    private static final String TAG = "ClientSocketHandler";

    private ClientSocketHandler() {
    }

    static void connect(int port, Handler handler, InetAddress groupOwnerAddress)
            throws IOException {
        Socket socket = new Socket();
        socket.bind(null);
        socket.connect(new InetSocketAddress(groupOwnerAddress.getHostAddress(),
                port), 5000);
        Log.d(TAG, "Launching the I/O handler");
        ChatManager chat = new ChatManager(socket, handler);
        new Thread(chat).start();
    }
}
