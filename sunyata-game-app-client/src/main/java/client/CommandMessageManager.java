package client;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.game.server.message.OctopusToUserRawMessage;

/**
 * Created by leo on 17/11/8.
 */
@Component
public class CommandMessageManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    Logger logger = LoggerFactory.getLogger(CommandMessageManager.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        ObjectPool.NonBlocking()
        this.applicationContext = applicationContext;
    }


    public void handler(OctopusToUserRawMessage request, ChannelHandlerContext ctx) throws Exception {
        //Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(ServerCommand.class);
//        Object bean = applicationContext.getBean(String.valueOf(request.getCmd()));
//        if (bean == null) {
//            logger.error("命令没有被实现:CommandId:{}", request.getCmd());
//        }
        String cmd = String.valueOf(request.getCmd());

//        if (Objects.equals(cmd, Commands.Login)) {
//            //new LoginRet().run(request, ctx);
//        logger.info("command:{},errCode:{}", request.getCmd(), request.getCode());


    }
}
