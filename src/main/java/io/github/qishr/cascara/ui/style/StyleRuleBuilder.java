package io.github.qishr.cascara.ui.style;

import java.util.ArrayList;
import java.util.List;

// New class: StyleRuleBuilder.java (or inner class of ControlStyle)

public class StyleRuleBuilder {
    private final StringBuilder declarations = new StringBuilder();
    private final List<String> selectors = new ArrayList<>();

    // Replaces new CSSStyleRule().addSelector(...)
    public StyleRuleBuilder addSelector(String selector) {
        selectors.add(selector);
        return this;
    }

    // Replaces .addDeclaration(PROPERTY, VALUES, IMPORTANT)
    public StyleRuleBuilder addDeclaration(String property, String value, boolean important) {
        declarations.append("    ")
                    .append(property)
                    .append(": ")
                    .append(value);
        if (important) {
            declarations.append(" !important");
        }
        declarations.append(";\n");
        return this;
    }

    // Final method to generate the required string
    public String build() {
        if (selectors.isEmpty()) return "";

        return String.join(", ", selectors) + " {\n" +
               declarations.toString() +
               "}\n";
    }
}