package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NBTCompound extends NBTNamed implements Iterable<NBTNamed> {

    Map<String, NBTNamed> tags = new HashMap<>();

    @Override
    void feed(DataInputStream is) throws IOException {
        super.feed(is);

        NBTag tag = NBTag.parseInputStream(is);
        while (tag instanceof NBTNamed) {
            NBTNamed namedTag = (NBTNamed) tag;
            tags.put(namedTag.getName(), namedTag);
            tag = NBTag.parseInputStream(is);
        }
    }

    public NBTNamed getTag(String path) {
        if (path.startsWith("."))
            path = path.substring(1);
        if (path.equals(""))
            return this;

        String[] s = path.split("\\.");
        String looking = s[0];

        if (tags.containsKey(looking)) {
            NBTNamed found = tags.get(looking);

            //There is still hierarchy to traverse
            if (s.length > 1) {
                String deeper = path.substring(looking.length() + 1);

                if (found instanceof NBTCompound)
                    return ((NBTCompound) found).getTag(deeper);
                if (found instanceof NBTList)
                    return ((NBTList) found).getTag(deeper);
                else
                    System.out.println("error: Can't traverse tag " + found + "; not a Compound, nor a List tag.");
            }
            //There isn't
            else
                return found;
        }

        return null;
    }

    public Iterator<NBTNamed> iterator() {
        return tags.values().iterator();
    }
}
