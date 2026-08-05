package io.github.ltennstedt.irrational.validation.validator;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.ltennstedt.irrational.core.numeric.LongRational;
import io.github.ltennstedt.irrational.validation.annotation.Invertible;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

final class InvertibleConstraintValidatorTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void tearDownAll() {
        validatorFactory.close();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
        0, 1
        1, 0
        """)
    void isValid_should_return_violation_when_is_not_invertible(final long numerator, final int expected) {
        final var actual = validator.validate(new Holder(new LongRational(numerator, 1L)));

        assertThat(actual).hasSize(expected).allMatch(it -> it.getMessage().equals("must be invertible"));
    }

    private record Holder(@Invertible LongRational rational) {}
}
