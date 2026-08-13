package io.github.ltennstedt.irrational.groovy.numeric

import groovy.transform.CompileStatic
import io.github.ltennstedt.irrational.core.numeric.BigRational

/**
 * Operators for PrimitiveNumeric
 */
@CompileStatic
final class BigRationalOperators {
    private BigRationalOperators() {}

    /**
     * Binary / operator
     *
     * @param self self
     * @param divisor divisor
     * @return quotient
     */
    static BigRational div(final BigRational self, final BigRational divisor) {
        self.divide(divisor)
    }

    /**
     * ** operator
     *
     * @param self self
     * @param exponent exponent
     * @return power
     */
    static BigRational power(final BigRational self, final int exponent) {
        self.pow(exponent)
    }
}
