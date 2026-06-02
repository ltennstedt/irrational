package io.github.ltennstedt.irrational.core.util;

import java.math.BigDecimal;
import java.math.MathContext;

/** Constants */
public final class Constants {
    /** Default {@link MathContext} */
    public static final MathContext DEFAULT_MATH_CONTEXT = MathContext.DECIMAL64;

    /** Pi based on DEFAULT_MATH_CONTEXT */
    public static final BigDecimal BIG_PI = PiCalculator.pi(DEFAULT_MATH_CONTEXT);

    /** Pi over 2 based on DEFAULT_MATH_CONTEXT */
    public static final BigDecimal HALF_BIG_PI = BIG_PI.divide(BigDecimal.valueOf(2L), MathContext.DECIMAL128);

    private Constants() {}
}
