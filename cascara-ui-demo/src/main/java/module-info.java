module cascara.ui.demo {
    requires transitive cascara.common;
    requires transitive cascara.common.io;
    requires cascara.lang.json;
    requires cascara.lang.xml;
    requires transitive cascara.lang.yaml;
    requires transitive cascara.schema;
    requires transitive cascara.ui;

    requires javafx.base;
    requires transitive javafx.controls;
    requires javafx.graphics;

    exports io.github.qishr.cascara.ui.demo to cascara.ui, javafx.base, javafx.graphics;
    opens io.github.qishr.cascara.ui.demo to cascara.ui, cascara.schema, javafx.base;
}
