package org.sunyata.game.client;

import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by leo on 17/11/26.
 */
public class AnyClientPooledObject extends DefaultPooledObject<AnyClient> {

    /**
     * Create a new instance that wraps the provided object so that the pool can
     * track the state of the pooled object.
     *
     * @param object The object to wrap
     */
    public AnyClientPooledObject(AnyClient object) {
        super(object);
    }
}
