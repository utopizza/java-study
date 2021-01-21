package io.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int DEFAULT_PORT = 8088;
    private static ServerSocket server;

    private static void start() {
        start(DEFAULT_PORT);
    }

    private synchronized static void start(int port) {
        if (server != null) return;
        try {
            // new a ServerSocket
            server = new ServerSocket(port);
            System.out.println("Server is started. Begin listening on port " + port + "...");

            // create a new thread for each accept
            int num = 0;
            Socket socket;
            while (true) {
                socket = server.accept();
                new Thread(new ServerHandler(socket), String.valueOf(num++)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (server != null) {
                    server.close();
                    server = null;
                    System.out.println("Server is closed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ServerHandler implements Runnable {
        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader in = null;
            String readStr = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((readStr = in.readLine()) != null) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " handled a message: " + readStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                        in = null;
                    }
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        start();
    }
}
