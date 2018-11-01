package org.sunyata.game;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ServerCommand {
    public final static String fanoutMode = "fanout";//所有server，只要实现此命令即接收此命令
    public final static String lbMode = "loadBalance";//每种服务只接收一次，即每个服务随机选中一台接收
    public final static String directMode = "direct";//指定serverId,来接收，即如果多个服务实现了此命令，则只会发送到指定的serverId上

    String value() default "";

    String description() default "";

    String version() default "";

    String serviceName() default "";

    String commandName() default "";

    String commandId() default "";

    //int loadBalanceModel() default 0;

    //boolean isEvent() default false;

    String routeModel() default ServerCommand.lbMode;//0代表同一个服务,只有一个接收点即可,1代表多个服务多个接收点
}

