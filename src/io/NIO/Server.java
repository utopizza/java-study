package io.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static int DEFAULT_PORT = 8089;
    private static ServerHandler serverHandler;

    private static class ServerHandler implements Runnable {
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private volatile boolean started;

        public ServerHandler(int port) {
            try {
                selector = Selector.open();
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false); // no blocking
                serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                started = true;
                System.out.println("Server is started, port:" + port);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void stop() {
            started = false;
        }

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                // acceptable
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                // readable
                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(buffer);
                    if (readBytes > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String readStr = new String(bytes, "UTF-8");
                        System.out.println("Server received message from client: " + readStr);
                    } else if (readBytes < 0) {
                        key.cancel();
                        socketChannel.close();
                    }
                }
            }
        }

        @Override
        public void run() {
            try {
                while (started) {
                    selector.select(1000);
                    Set<SelectionKey> keys = selector.selectedKeys();

                    // foreach key(channel) to handle input
                    Iterator<SelectionKey> iterator = keys.iterator();
                    SelectionKey key = null;
                    while (iterator.hasNext()) {
                        key = iterator.next();
                        iterator.remove();
                        handleInput(key);
                    }
                }
                if (selector != null) {
                    selector.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void start() {
        start(DEFAULT_PORT);
    }

    public static synchronized void start(int port) {
        if (serverHandler != null) serverHandler.stop();
        serverHandler = new ServerHandler(port);
        new Thread(serverHandler, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
