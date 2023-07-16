package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;

public class NBTByteArray extends NBTNamed {

    int size;

    public byte[] data;

    @Override
    void feed(DataInputStream is) throws IOException {
        super.feed(is);
        size = is.read() << 24;
        size += is.read() << 16;
        size += is.read() << 8;
        size += is.read();
        data = new byte[size];
        try {
            is.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
