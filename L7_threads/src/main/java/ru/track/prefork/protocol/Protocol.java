package ru.track.prefork.protocol;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.ProtocolException;

public interface Protocol <T extends Serializable>{
    public byte[] encode(T msg)throws IOException,ProtocolException;

    @Nullable
    public T decode(InputStream in, Class <T> clazz) throws IOException,ProtocolException;
}
