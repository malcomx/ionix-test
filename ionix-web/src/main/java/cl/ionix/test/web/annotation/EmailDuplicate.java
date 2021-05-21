package cl.ionix.test.web.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = EmailDuplicateValidator.class)
public @interface EmailDuplicate {
	String message() default "{Email se encuentra registrado en el sistema}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
