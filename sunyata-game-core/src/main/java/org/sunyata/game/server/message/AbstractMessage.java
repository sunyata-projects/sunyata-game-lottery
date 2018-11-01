package org.sunyata.game.server.message;

import org.sunyata.game.server.Session;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractMessage implements Message {
    @SuppressWarnings("rawtypes")
    private Session session;

    @SuppressWarnings("unchecked")
    @Override
    public final <T> Session<T> getSession() {
        return session;
    }

    @Override
    public final <T> void setSession(Session<T> session) {
        this.session = session;
    }

    public byte[] toBytes() {
        return null;
    }

    public Object toPbObject() {
        return null;
    }

    public List<Integer> toList(int[] array) {
        if (array != null) {

            List<Integer> intList = new ArrayList<Integer>();
            for (int index = 0; index < array.length; index++) {
                intList.add(array[index]);
            }
            return intList;
        }
        return new ArrayList<>();
    }
}
