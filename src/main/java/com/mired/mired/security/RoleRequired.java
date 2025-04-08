package com.mired.mired.security;

import com.mired.mired.model.Role;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleRequired {
    Role[] value();
}