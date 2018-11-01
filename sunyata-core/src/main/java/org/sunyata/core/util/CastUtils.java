package org.sunyata.core.util;

public final class CastUtils {

    @SuppressWarnings("unchecked")
    public static <E> E cast(Object o) {
        return (E) o;
    }
}
