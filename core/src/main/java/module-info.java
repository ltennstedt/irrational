/** module-info.java */
module io.github.ltennstedt.irrational {
    requires org.jspecify;

    exports io.github.ltennstedt.irrational.core;
    exports io.github.ltennstedt.irrational.core.numeric;

    opens io.github.ltennstedt.irrational.core;
    opens io.github.ltennstedt.irrational.core.numeric;
}
