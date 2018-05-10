package ru.track.prefork;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.IOUtils;
import ru.track.prefork.protocol.JsonProtocol;
import ru.track.prefork.protocol.Message;
import com.sun.mail.iap.Protocol;
import org.jetbrains.annotations.NotNull;
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


/**
 *
 */

public class Server {
    static Logger log = LoggerFactory.getLogger(Server.class);
    private AtomicLong count = new AtomicLong(0);
    private int port;
    private ConcurrentMap<Long, Worker> workMap;
    private ru.track.prefork.protocol.Protocol<Message> protocol;
    private SynchronousQueue <Socket> SQueue;

    public Server(int port, ru.track.prefork.protocol.Protocol <Message> protocol) {
        this.port = port;
        this.protocol= protocol;
        workMap= new ConcurrentHashMap<>();
    }


    public void serve() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port, 10, InetAddress.getByName("localhost"));
        log.info("on select...");
        Scanner scanner=new Scanner(System.in);
        Thread admin= new Thread(()->{
            while (true){
                String line=scanner.nextLine();
                if(line.equals("list")){
                    workMap.forEach((wID,worker)->{
                    log.info(worker.getName());
                });
                } else if (line.startsWith("drop")) {
                        String[] command = line.split(" ");
                        if (command.length == 2) {
                            try {
                                Worker worker = workMap.get(Long.parseLong(command[1]));
                                worker.interrupt();
                                log.info(String.format("Interrupting: %s", worker.getName()));
                            } catch (NumberFormatException e) {
                                log.error(line + " <- Wrong syntax of drop.", e);
                            }
                        } else {
                            log.error(line + " <- Wrong syntax of drop.");
                        }
                    } else {
                        log.error(line + " <- Wrong command.");
                    }
                }
            });
            admin.setName("AdminThread");
            admin.start();
            SQueue=new SynchronousQueue<Socket>();
            }
        }
        //while (!serverSocket.isClosed()) {
          //  final  Socket client = serverSocket.accept();
            //final long wID=count.getAndIncrement();
           // Worker worker=new Worker(client, (Protocol<Message>) protocol,wID)
            //workMap.put(wID,worker);
            //worker.start();
       // }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(9000, new JsonProtocol());
        try {
            server.serve();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

class Worker extends Thread {
    @NotNull
    final OutputStream out;
    @NotNull
    final InputStream in;
    @NotNull
    Protocol<Message> protocol;
    @NotNull
    private long id;
    @NotNull
    private Socket socket;

    public Worker(@NotNull Socket sock, @NotNull Protocol<Message> protocol, long id) {
        this.socket = sock;
        this.protocol = protocol;
        this.id = id;
        setName(String.format("Client[%d]@%s:%d", id, sock.getInetAddress(), sock.getPort()));
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            handle(socket);
        } catch (Exception e) {
            workMap.remove(id);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException{
        try {
            while(!socket.isClosed()&& !Thread.currentThread().isInterrupted()) {
                byte[] buf = new byte[1024];
                int nRead = in.read(buf);
                if(nRead!=-1) {
                    Message mFromCl = protocol.decode(in, Message.class);
                    //
                    if (mFromCl.text.equals("q")) {
                        Thread.currentThread().interrupt();
                    }
                    mFromCl.text = String.format("Client@%s:%d>%s", socket.getInetAddress(), socket.getPort(), mFromCl.text);
                    workMap.forEach((wID, worker) -> {
                        if (id != wID) {
                            worker.send(mFromCl);
                        }
                    });
                } else {
                    Thread.currentThread().interrupt();
                }

            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ProtocolException e){
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(socket);
            workMap.remove(id);
        }
    }



    private void send(Message message) {
        try {
            out.write(protocol.encode(message));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            log.error("Protocol exception", e);
        }
    }
}





