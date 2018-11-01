package org.sunyata.core.util;

/**
 * @author leo on 14-1-15.
 */
public interface CloneInterface extends Cloneable {
    public Object clone() throws CloneNotSupportedException;
}
