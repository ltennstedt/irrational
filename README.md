[![Java CI](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml/badge.svg)](https://github.com/ltennstedt/irrational/actions/workflows/gradle.yaml)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=code+coverage&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=active+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)
[![DeepSource](https://app.deepsource.com/gh/ltennstedt/irrational.svg/?label=resolved+issues&show_trend=true&token=l7glGnfi90yVbb9sy5mkCVv6)](https://app.deepsource.com/gh/ltennstedt/irrational/)

# irrational

Exact and predictable numeric types for the JVM

irrational is a mathematical library for the JVM providing implementations of the following numeric types:

|    Number type    | Fixed precision |                                         Arbitrary precision                                          |
|-------------------|-----------------|------------------------------------------------------------------------------------------------------|
| Rational numbers  | `long`          | [BigInteger](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html) |
| Gaussian integers | `long`          | [BigInteger](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html) |
| Complex numbers   | `double`        | [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html) |
| Quaternions       | `double`        | [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html) |
| Vectors           | `double`        | [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html) |
| Matrices          | `double`        | [BigDecimal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigDecimal.html) |

irrational targets polyglot developers who need exact and predictable numeric behavior on the JVM.
irrational requires at least Java 17 to build and run. The project is in a very early stage of development and is still
far from completion. APIs are unstable and subject to change.

### Motivation

The JVM provides excellent primitive numeric types and arbitrary precision classes, but they do not always offer the
mathematical semantics required by numeric applications.

Existing mathematical libraries often focus on specific domains or provide building blocks rather than a unified set of
numeric types. irrational aims to provide comprehensive arbitrary precision support, JVM ecosystem integration and
idiomatic APIs for Java, Kotlin and Groovy developers.

### Usage

**Java**

```java
final var rational = new LongRational(1L, 2L);
rational.add(new LongRational(3L, 4L));

// Java 19/20 (preview) and Java 21+
if (rational instanceof LongRational(long n, long d)) {
    System.out.println(n + "/" + d);
}
```

**Kotlin**

```kotlin
val rational = LongRational(1L, 2L)
rational + LongRational(3L, 4L)
5.toLongRational()
listOf(rational).map { (n, d) -> "$n / $d" }
```

**Groovy**

```groovy
final rational = new LongRational(1L, 2L)
rational + new LongRational(3L, 4L)
rational ** 5
```

### Building

The Gradle Wrapper is provided to build this project. An appropriate JDK will be downloaded automatically if no
compatible toolchain is found locally.

|        Programming language        | Version |
|------------------------------------|---------|
| [Java](https://www.java.com/)      | 17      |
| [Kotlin](https://kotlinlang.org/)  | 2.2     |
| [Groovy](https://groovy-lang.org/) | 5.0     |

Versions are aligned with those of [Spring Boot](https://spring.io/projects/spring-boot) which are still publicly
supported (OSS support).

[Spring Boot support matrix](https://spring.io/projects/spring-boot#support)

A convenience task for local developer builds including formatting, compiling, testing, packaging and checking is
available.

```shell
# Unix-like
./gradlew localBuild

# Windows
./gradlew.bat localBuild
```

### Subprojects

|  Name  |  Description   | Programming language |     Status     |
|--------|----------------|----------------------|----------------|
| core   | Core types     | Java                 | In development |
| kotlin | Kotlin wrapper | Kotlin               | In development |
| groovy | Groovy wrapper | Groovy               | In development |

More subprojects are planned for the future.

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
* Prefer [records](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Record.html) over classes and
  [sealed interfaces](https://docs.oracle.com/en/java/javase/17/language/sealed-classes-and-interfaces.html)
* Language ergonomics via thin adapters for [Kotlin](https://kotlinlang.org/) and [Groovy](https://groovy-lang.org/)
* Informative [Javadoc](https://docs.oracle.com/en/java/javase/17/javadoc/javadoc.html),
  [KDoc](https://kotlinlang.org/docs/kotlin-doc.html)
  and [GroovyDoc](https://groovy-lang.org/groovydoc.html)

### Quality

**Formatting**
* [Spotless](https://github.com/diffplug/spotless)

**Linting**
* [Checkstyle](https://checkstyle.sourceforge.io/)
* [PMD](https://pmd.github.io/)
* [SpotBugs](https://spotbugs.github.io/)
* [Detekt](https://detekt.dev/)
* [CodeNarc](https://codenarc.org/)
* [SonarQube for IDE](https://www.sonarsource.com/products/sonarqube/ide/)
* [DeepSource](https://deepsource.com/)

**Testing**
* [JUnit](https://junit.org/)
* [AssertJ](https://assertj.github.io/doc/)
* [Kotest](https://kotest.io/)
* [Spock](https://spockframework.org/)
* [JaCoCo](https://www.eclemma.org/jacoco/)
