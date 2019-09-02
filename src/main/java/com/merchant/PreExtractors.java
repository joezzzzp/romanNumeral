package com.merchant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author created by zzz at 2019/9/2 17:21
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreExtractors {

    Class<? extends Extractor>[] value() default {};
}
