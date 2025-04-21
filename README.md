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

### Modules (work in progress)

| Module  |               Language               |     Description      |
|---------|--------------------------------------|----------------------|
| java    | [Java](https://www.java.com/)        | Core implementations |
| kotlin  | [Kotlin](https://kotlinlang.org/)    | Kotlin support       |
| scala   | [Scala](https://www.scala-lang.org/) | Scala support        |
| groovy  | [Groovy](https://groovy-lang.org/)   | Groovy support       |
| clojure | [Clojure](https://clojure.org/)      | Clojure support      |

### Design principles

* Minimal dependencies (only [JSpecify](https://jspecify.dev/))
* All types are immutable.
* Absolutely no null values
* No silent overflows
* Exactness over performance
* Explicit over implicit
* Single source of truth
* Language ergonomics via thin adapters

### Code Quality

Following tools are or will be used during development to ensure the quality and correctness of the code:

* [Spotless](https://github.com/diffplug/spotless)
* [Checkstyle](https://checkstyle.sourceforge.io/)
* [PMD](https://pmd.github.io/)
* [SpotBugs](https://spotbugs.github.io/)
* [Detekt](https://detekt.dev/)
* [Scalafix](https://scalacenter.github.io/scalafix/)
* [Wartremover](https://www.wartremover.org/)
* [Codenarc](https://codenarc.org/)
* [cljfmt](https://github.com/weavejester/cljfmt)
* [clj-kondo](https://github.com/clj-kondo/clj-kondo)
* [DeepSource](https://deepsource.com/)
* [SonarQube IDE](https://www.sonarsource.com/products/sonarqube/ide/)
