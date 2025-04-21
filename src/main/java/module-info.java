/** module-info.java */
module io.github.ltennstedt.irrational {
    requires org.jspecify;

    exports io.github.ltennstedt.irrational.core.numeric;
    exports io.github.ltennstedt.irrational.core.util;

    opens io.github.ltennstedt.irrational.core.numeric;
}
