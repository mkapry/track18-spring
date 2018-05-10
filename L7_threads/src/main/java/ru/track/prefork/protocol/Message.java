package ru.track.prefork.protocol;

import java.io.Serializable;

public class Message implements Serializable {
    private long ts;
    private String message;
    public String user;

    public Message(long ts, String message){
        this.ts = ts;
        this.message = message;
    }
    @Override
    public String toString(){
        return "Message{"+
                "ts="+ts+
                ", message="+ message+
                ",user="+ user+'}';
    }

}
