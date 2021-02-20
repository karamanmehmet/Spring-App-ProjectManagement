package com.cybertek.annotation;

import java.lang.annotation.*;

/**
 * Tracks the execution time.
 */
//Allowed to use only on methods
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecutionTime {
}