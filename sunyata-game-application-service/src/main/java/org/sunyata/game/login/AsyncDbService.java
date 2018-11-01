package org.sunyata.game.login;

import org.sunyata.game.service.AbstractAsyncService;

/**
 * 数据库异步执行
 *
 * @author leo on 14-1-8.
 */
public final class AsyncDbService extends AbstractAsyncService {
//    private static final Logger log = LoggerFactory.getLogger(AsyncDbService.class);
    public AsyncDbService(int threadNums) {
        super(threadNums, threadNums);
    }


//    //public void excuete(User user, Runnable task) {
//        submit(user.getUserIdInGateway(), task);
//    }

    public void excuete(int userId, Runnable task) {
        submit(userId, task);
    }

    public void excuete(Runnable task) {
        submit(task);
    }
}
