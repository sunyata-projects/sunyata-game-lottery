package org.sunyata.game.login;

import org.sunyata.game.service.AbstractAsyncService;
import org.springframework.stereotype.Component;

/**
 * 异步服务，请注意线程安全
 *
 * @author leo on 14-1-8.
 */
@Component
public final class AsyncService extends AbstractAsyncService {
//    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);

    public AsyncService() {
        super(2, 2);
    }

//    @SuppressWarnings("unchecked")
//    public void excuete(MessageHandler handler, Message message, User user) {
//        submit(user.getUserIdInGateway(), () -> handler.handler(message, user));
//    }

    public void excuete(Runnable run) {
        submit(run);
    }
}
