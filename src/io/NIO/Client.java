package io.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 8089;
    private static ClientHandler clientHandler;

    private static class ClientHandler implements Runnable {
        private String host;
        private int port;
        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean started;

        public ClientHandler(String ip, int port) {
            this.host = ip;
            this.port = port;
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                started = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            started = false;
        }

        @Override
        public void run() {
            try {
                // connect
                socketChannel.connect(new InetSocketAddress(host, port));
                while (!socketChannel.finishConnect()) ;
                System.out.println("Connected to Server.");

                // register to selector to receive msg from server
                socketChannel.register(selector, SelectionKey.OP_CONNECT);

                // foreach key to handle msg from server
                while (started) {
                    selector.select(1000);
                    Set<SelectionKey> keys = selector.selectedKeys();
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

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                // acceptable
                if (key.isAcceptable()) {
                    SocketChannel serverSocketChannel = (SocketChannel) key.channel();
                    if (key.isConnectable()) {
                        if (serverSocketChannel.finishConnect()) ;
                        else System.exit(1);
                    }
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
                        System.out.println("Client received message from Server: " + readStr);
                    } else if (readBytes < 0) {
                        key.cancel();
                        socketChannel.close();
                    }
                }
            }
        }

        public void sendMsg(String msg) throws Exception {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel, msg);
        }

        private void doWrite(SocketChannel channel, String request) throws IOException {
            byte[] bytes = request.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

    public static void start() {
        start(DEFAULT_HOST, DEFAULT_PORT);
    }

    public static synchronized void start(String ip, int port) {
        if (clientHandler != null) clientHandler.stop();
        clientHandler = new ClientHandler(ip, port);
        new Thread(clientHandler, "Client").start();
    }

    public static void sendMsg(String msg) throws Exception {
        clientHandler.sendMsg(msg);
    }

    public static void main(String[] args) throws Exception {
        start();
        Thread.sleep(2000);
        while (true) {
            System.out.println("Please input message:");
            Scanner in = new Scanner(System.in);
            String msg = in.nextLine();
            sendMsg(msg);
        }
    }
}
