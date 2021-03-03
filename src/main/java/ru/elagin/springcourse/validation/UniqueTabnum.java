package ru.elagin.springcourse.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTabnumValidator.class)

public @interface UniqueTabnum {

    String message() default "validation.unique.tabnum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

