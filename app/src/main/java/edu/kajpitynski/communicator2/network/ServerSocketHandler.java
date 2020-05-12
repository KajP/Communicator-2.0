package edu.kajpitynski.communicator2.network;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerSocketHandler extends Thread {
    private static final String TAG = "ServerSocketHandler";
    private final int THREAD_COUNT = 10;
    private final int SOCKET_PORT;
    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(THREAD_COUNT, THREAD_COUNT,
            10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private ServerSocket serverSocket;
    private Handler handler;

    public ServerSocketHandler(int socket_port, Handler handler) throws IOException {
        SOCKET_PORT = socket_port;
        this.handler = handler;
        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdownNow();
            throw e;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                pool.execute(new ChatManager(serverSocket.accept(), handler));
                Log.d(TAG, "Launching the I/O handler");
            } catch (IOException e) {
                e.printStackTrace();
                if (!serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
