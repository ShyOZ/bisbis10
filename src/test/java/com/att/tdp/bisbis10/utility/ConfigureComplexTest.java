package com.att.tdp.bisbis10.utility;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@ExtendWith({ SpringExtension.class, TestExtension.class })
public @interface ConfigureComplexTest {
}
