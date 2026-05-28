/** module-info.java */
module io.github.ltennstedt.irrational {
    requires org.jspecify;
    requires java.logging;

    exports io.github.ltennstedt.irrational.core.numeric;

    opens io.github.ltennstedt.irrational.core.numeric;
}
