package io.github.ltennstedt.irrational.core.numeric

import groovy.transform.CompileStatic

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
        return self.divide(divisor)
    }

    /**
     * ** operator
     *
     * @param self self
     * @param exponent exponent
     * @return power
     */
    static BigRational power(final BigRational self, final int exponent) {
        return self.pow(exponent)
    }
}
