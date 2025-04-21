[![Java CI](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml/badge.svg)](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=code+coverage&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=active+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=resolved+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)

# irrational

Exact and predictable numeric types for the JVM

irrational is a mathematical library for the JVM which will provide implementations of the following number types:

* Rational numbers (`long`
  and [BigInteger](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html))
* Gaussian numbers (`long`
  and [BigInteger](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html))
* Complex numbers (`double`
  and [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html))
* Vectors (`double`
  and [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html))
* Matrices (`double`
  and [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html))

irrational targets polyglot developers who need exact and predictable numeric behavior on the JVM.
irrational requires at least Java 17 to build and run. The project is in a very early stage of development and is still
far from completion. APIs are unstable and subject to change.

### Building

To successfully build the project, a local installation of a JDK and [Gradle](https://gradle.org/) is required.
The specific versions can be found as follows:

* For Gradle in [gradle.properties](https://github.com/ltennstedt/irrational/blob/main/gradle.properties#L1)
* For Java in [build.gradle.kts](https://github.com/ltennstedt/irrational/blob/main/build.gradle.kts#L97)

The wrapper task is configured accordingly.

### Design goals

* Minimal dependencies
* Nullability annotations via [JSpecify](https://jspecify.dev/)
* All types are immutable
* Absolutely no null values
* All leaf classes are final
* No silent overflows
* Exactness over performance
* Explicit over implicit
* Single source of truth
* Parameter validation and fast failing
* Builders for vectors and matrices
* Prefer [records](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Record.html) over classes
* Language ergonomics via thin adapters for [Kotlin](https://kotlinlang.org/) and [Groovy](https://groovy-lang.org/)
* Informative [Javadoc](https://docs.oracle.com/en/java/javase/17/javadoc/javadoc.html), [KDoc](https://kotlinlang.org/docs/kotlin-doc.html)
  and [GroovyDoc](https://groovy-lang.org/groovydoc.html)
* High code quality ensured by [Spotless](https://github.com/diffplug/spotless), [Checkstyle](https://checkstyle.sourceforge.io/), [PMD](https://pmd.github.io/), [SpotBugs](https://spotbugs.github.io/), [Detekt](https://detekt.dev/), [CodeNarc](https://codenarc.org/), [SonarQube for IDE](https://www.sonarsource.com/products/sonarqube/ide/)
  and [DeepSource](https://deepsource.com/)
* Good test coverage ensured by [JUnit](https://junit.org/), [AssertJ](https://assertj.github.io/doc/), [EqualsVerifier](https://jqno.nl/equalsverifier/), [Kotest](https://kotest.io/), [Spock](https://spockframework.org/)
  and [JaCoCo](https://www.eclemma.org/jacoco/)
