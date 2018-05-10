package ru.track.prefork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ProtocolException;
import java.net.Socket;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.track.prefork.protocol.JsonProtocol;
import ru.track.prefork.protocol.Message;
import ru.track.prefork.protocol.Protocol;


public class Client {
    static Logger log = LoggerFactory.getLogger(Client.class);
    //private JsonProtocol protocol;
    private int port;
    private String host;
    private Protocol<Message> protocol;

    public Client(int port, String host, Protocol<Message> protocol;) {
        this.port = port;
        this.host = host;
        this.protocol = protocol;
    }

    public void loop() throws Exception {
        Socket socket = new Socket(host, port);
        try (OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream()) {
            Scanner scanner = new Scanner(System.in);
            Thread scannerT = new Thread();
            try {
                while (true) {
                    String line = scanner.nextLine();
                    if ("q".equals(line)) {
                        break;
                    }
                    Message mess=new Message(System.currentTimeMillis(),line);
                    mess.user="user";
                    out.write(protocol.encode(mess));
                    out.flush();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            scannerT.start();
            byte[] buf = new byte[1024];
            while (!socket.isOutputShutdown()) {
                int nRead = in.read(buf);
                if(nRead!=-1) {
                    Message messFromServ = protocol.decode(in, Message.class);
                    log.info("Server said:" + messFromServ);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            socket.close();
        }

    }

    public static void main(String[] args) throws Exception {
        Client client = new Client(9000, "localhost", new JsonProtocol());
        try {
            client.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
