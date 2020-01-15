package com.shy.crm.annotations;

import java.lang.annotation.*;

/**
 * @author suhongyv
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    String code() default "";
}
