[![Java CI with Gradle](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml/badge.svg)](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=code+coverage&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=active+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=resolved+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)

# irrational

Mathematical library for the JVM

irrational is a mathematical library for the JVM which will provide implementations of the following number types:

* Rational numbers (long and BigInteger)
* Gaussian numbers (long and BigInteger)
* Complex numbers (double and BigDecimal)

irrational targets polyglot developers who require exact and predictable numeric behavior on the JVM.
irrational requires at least Java 17 to build and run. The project is in a very early stage of development and is still
far from completion so that APIs are unstable and subject to change.

### Building

To successfully build the project, a local installation of both JDK 17 and [Gradle](https://gradle.org/) is required.
The specific Gradle version can be found
in [settings.gradle.kts](https://github.com/ltennstedt/irrational/blob/main/settings.gradle.kts#L6). The wrapper task is
also configured accordingly as an alternative.

### Design principles

* Minimal dependencies (only [JSpecify](https://jspecify.dev/))
* All types are immutable.
* Absolutely no null values
* No silent overflows
* Exactness over performance
* Explicit over implicit
* Single source of truth
* Language ergonomics via thin adapters for [Kotlin](https://kotlinlang.org/) and [Groovy](https://groovy-lang.org/)
