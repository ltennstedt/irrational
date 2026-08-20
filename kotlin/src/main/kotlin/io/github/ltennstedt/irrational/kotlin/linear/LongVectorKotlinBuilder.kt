package io.github.ltennstedt.irrational.kotlin.linear

import io.github.ltennstedt.irrational.core.linear.LongVector

/** Builder function for LongVector */
public fun longVector(
    size: Int,
    init: LongVectorKotlinBuilder.() -> Unit,
): LongVector {
    val builder = LongVectorKotlinBuilder(size)
    builder.init()
    return builder.build()
}

/**
 * Kotlin builder for LongVector
 *
 * @property size size
 * @throws IllegalArgumentException when index is negative
 */
public class LongVectorKotlinBuilder(
    public val size: Int,
) {
    private val builder = LongVector.builder(size)

    /** Adds an entry */
    public fun entry(
        index: Int,
        value: Long,
    ) {
        builder.entry(index, value)
    }

    /** Builds a LongVector */
    public fun build(): LongVector = builder.build()
}
