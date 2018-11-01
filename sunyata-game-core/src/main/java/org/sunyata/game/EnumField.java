package org.sunyata.game;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumField {
    String name() default "未填写描述";
}
