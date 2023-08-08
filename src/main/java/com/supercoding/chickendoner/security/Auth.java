package com.supercoding.chickendoner.security;

import com.supercoding.chickendoner.security.UserRole;

import javax.management.relation.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    UserRole role() default UserRole.USER;
    boolean includeUserIdx() default true;
}
