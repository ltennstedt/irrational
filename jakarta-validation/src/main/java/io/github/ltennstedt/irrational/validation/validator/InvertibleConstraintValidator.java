package io.github.ltennstedt.irrational.validation.validator;

import io.github.ltennstedt.irrational.core.numeric.Numeric;
import io.github.ltennstedt.irrational.validation.annotation.Invertible;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;

/** {@link ConstraintValidator} for {@link Invertible} */
public class InvertibleConstraintValidator implements ConstraintValidator<Invertible, Numeric<?, ?>> {
    @Override
    public boolean isValid(final @Nullable Numeric<?, ?> value, final @Nullable ConstraintValidatorContext context) {
        return value == null || value.isInvertible();
    }
}
