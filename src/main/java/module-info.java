module cascara.ui {
    requires transitive cascara.common;
    requires transitive cascara.common.io;
    requires cascara.lang.json;
    requires cascara.lang.xml;
    requires transitive cascara.lang.yaml;
    requires transitive cascara.schema;

    requires javafx.base;
    requires transitive javafx.controls;
    requires javafx.graphics;

    uses io.github.qishr.cascara.common.service.ServiceProvider;
    uses io.github.qishr.cascara.ui.menu.SystemMenusService;

    uses io.github.qishr.cascara.ui.api.render.OptionListEditor;
    uses io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer;
    uses io.github.qishr.cascara.ui.api.render.ScalarRenderer;

    exports io.github.qishr.cascara.ui.api;
    exports io.github.qishr.cascara.ui.api.data;
    exports io.github.qishr.cascara.ui.option;
    exports io.github.qishr.cascara.ui.api.render;
    exports io.github.qishr.cascara.ui.color;
    exports io.github.qishr.cascara.ui.control;
    exports io.github.qishr.cascara.ui.data;
    exports io.github.qishr.cascara.ui.form;
    exports io.github.qishr.cascara.ui.l10n;
    exports io.github.qishr.cascara.ui.menu;
    exports io.github.qishr.cascara.ui.render;
    exports io.github.qishr.cascara.ui.render.control;
    exports io.github.qishr.cascara.ui.render.factory;
    exports io.github.qishr.cascara.ui.render.standard;
    exports io.github.qishr.cascara.ui.schema;
    exports io.github.qishr.cascara.ui.theme;
    exports io.github.qishr.cascara.ui.style;
    exports io.github.qishr.cascara.ui.style.custom;
    exports io.github.qishr.cascara.ui.style.standard;
    exports io.github.qishr.cascara.ui.vsix;
    exports io.github.qishr.cascara.ui.widget;
    exports io.github.qishr.cascara.ui.window;

    opens io.github.qishr.cascara.ui.color;
    opens io.github.qishr.cascara.ui.control;
    opens io.github.qishr.cascara.ui.render;
    opens io.github.qishr.cascara.ui.style;
    opens io.github.qishr.cascara.ui.style.custom;
    opens io.github.qishr.cascara.ui.style.part;
    opens io.github.qishr.cascara.ui.style.standard;
    opens io.github.qishr.cascara.ui.theme;
    opens io.github.qishr.cascara.ui.window;

    opens io.github.qishr.cascara.ui.l10n to cascara.schema, cascara.lang.yaml;

    opens io.github.qishr.cascara.ui.data to cascara.schema;
    opens io.github.qishr.cascara.ui.menu to cascara.schema;

    provides io.github.qishr.cascara.ui.api.render.ArrayEditorRenderer
        with io.github.qishr.cascara.ui.render.control.TableRenderer,
             io.github.qishr.cascara.ui.render.control.TagChooserRenderer;

    provides io.github.qishr.cascara.ui.api.render.ScalarEditorRenderer
        with io.github.qishr.cascara.ui.render.control.ColorChooserRenderer,
             io.github.qishr.cascara.ui.render.control.DateChooserRenderer,
             io.github.qishr.cascara.ui.render.control.FileChooserRenderer,
             io.github.qishr.cascara.ui.render.control.OptionChooserRenderer;

    provides io.github.qishr.cascara.ui.api.render.ScalarRenderer
        with io.github.qishr.cascara.ui.render.control.DiagnosticRenderer,
             io.github.qishr.cascara.ui.render.control.SingleLineCellRenderer,
             io.github.qishr.cascara.ui.render.control.TagRenderer,
             io.github.qishr.cascara.ui.render.control.TimeRenderer,
             io.github.qishr.cascara.ui.render.control.UriRenderer;
}
