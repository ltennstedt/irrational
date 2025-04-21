/** module-info.java */
module io.github.ltennstedt.irrational {
    requires org.jspecify;

    exports io.github.ltennstedt.irrational.numeric;
    exports io.github.ltennstedt.irrational.util;

    opens io.github.ltennstedt.irrational.numeric;
}
