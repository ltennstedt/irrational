/** module-info.java */
module io.github.ltennstedt.irrational.validation {
    requires org.jspecify;
    requires jakarta.validation;
    requires io.github.ltennstedt.irrational.core;

    exports io.github.ltennstedt.irrational.validation.annotation;

    opens io.github.ltennstedt.irrational.validation.annotation;
}
