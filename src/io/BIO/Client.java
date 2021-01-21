package io.BIO;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static int DEFAULT_SERVER_PORT = 8088;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    private static void send(String message) {
        send(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT, message);
    }

    private static void send(String ip, int port, String message) {
        Socket socket = null;
        BufferedWriter out = null;
        try {
            socket = new Socket(ip, port);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.write(message);
            out.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        while (true) {
            System.out.println("Please input a message:");
            Scanner in = new Scanner(System.in);
            String msg = in.nextLine();
            send(msg);
        }
    }
}
