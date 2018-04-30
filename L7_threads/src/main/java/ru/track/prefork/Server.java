package ru.track.prefork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * - multithreaded +
 * - atomic counter +
 * - setName() +
 * - thread -> Worker +
 * - save threads
 * - broadcast (fail-safe)
 */
class MyThread extends Thread {
    private int number;
    private Thread t;
    private Socket socket;
    MyThread(Socket sock){
        socket=sock;
    }
    @Override
    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
    @Override
    public void run() {
        try {
            System.out.println("Starting thread...");
            InputStream inputStream = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] buf = new byte[1024];
            int nRead = inputStream.read(buf);
            System.out.println(new String(buf, 0, nRead));
            out.write(buf, 0, nRead);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
public class Server {
    static Logger log = LoggerFactory.getLogger(Server.class);

    private int port;
    public Server(int port) {
        this.port = port;
    }
    public void serve() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName("localhost"));
        log.info("on select...");
        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new MyThread(socket);
            t.start();
            t.run();
            //t.join();
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(9000);
        server.serve();
    }
}
