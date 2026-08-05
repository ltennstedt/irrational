package io.github.ltennstedt.irrational.validation.annotation;

import io.github.ltennstedt.irrational.validation.annotation.Invertible.List;
import io.github.ltennstedt.irrational.validation.validator.InvertibleConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jspecify.annotations.Nullable;

/** The annotated {@link io.github.ltennstedt.irrational.core.numeric.Numeric} must be invertible */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InvertibleConstraintValidator.class)
@Documented
@Repeatable(List.class)
public @interface Invertible {
    /**
     * Returns message
     *
     * @return message
     */
    String message() default "{irrational.validation.Invertible.message}";

    /**
     * Returns groups
     *
     * @return groups
     */
    Class<?>[] groups() default {};

    /**
     * Returns payload
     *
     * @return payload
     */
    Class<? extends Payload>[] payload() default {};

    /** Defines several {@link Invertible} annotations on the same element */
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * Returns value
         *
         * @return value
         */
        @Nullable Invertible[] value();
    }
}
