package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.BigIntegerVector
import java.math.BigInteger

/** Builder function for BigIntegerVector */
public fun bigIntegerVector(
    size: Int,
    init: BigIntegerVectorKotlinBuilder.() -> Unit,
): BigIntegerVector {
    val builder = BigIntegerVectorKotlinBuilder(size)
    builder.init()
    return builder.build()
}

/**
 * Kotlin builder for BigIntegerVector
 *
 * @property size size
 * @throws IllegalArgumentException when index is negative
 */
public class BigIntegerVectorKotlinBuilder(
    public val size: Int,
) {
    private val builder = BigIntegerVector.builder(size)

    /** Adds an entry */
    public fun entry(
        index: Int,
        value: BigInteger,
    ) {
        builder.entry(index, value)
    }

    /** Builds a LongVector */
    public fun build(): BigIntegerVector = builder.build()
}
