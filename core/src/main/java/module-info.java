/** module-info.java */
module io.github.ltennstedt.irrational.core {
    requires org.jspecify;

    exports io.github.ltennstedt.irrational.core.numeric;

    opens io.github.ltennstedt.irrational.core.numeric;
}
