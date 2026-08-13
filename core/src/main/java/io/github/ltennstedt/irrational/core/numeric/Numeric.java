package io.github.ltennstedt.irrational.core.numeric;

import io.github.ltennstedt.irrational.core.Additive;
import io.github.ltennstedt.irrational.core.Multipliable;
import io.github.ltennstedt.irrational.core.Subtractable;

/**
 * Base interface for numbers
 *
 * @param <N> type of the number
 */
public sealed interface Numeric<N extends Numeric<N, Q>, Q extends Numeric<Q, Q>>
        extends Additive<N>, Subtractable<N>, Multipliable<N> permits Complex, DoubleQuaternion, Rational {}
