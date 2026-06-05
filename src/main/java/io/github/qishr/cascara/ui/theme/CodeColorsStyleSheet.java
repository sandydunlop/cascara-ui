package io.github.qishr.cascara.ui.theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CodeColorsStyleSheet {
    private List<StyleClass> classes = new ArrayList<>();

    public CodeColorsStyleSheet() {
        // Nothing to see here
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StyleClass styleClass : classes) {
            boolean isFirst = true;
            for (String name : styleClass.getNames()) {
                if (!isFirst) {
                    sb.append(", ");
                }
                sb.append(".");
                sb.append(name);
                isFirst = false;
            }
            sb.append(" {\n");
            for (Entry<String,String> attribute : styleClass.getAttributes().entrySet()) {
                if (attribute.getValue() != null && !attribute.getValue().isEmpty()) {
                    sb.append("    ");
                    sb.append(attribute.getKey());
                    sb.append(": ");
                    sb.append(attribute.getValue());
                    sb.append(";\n");
                }
            }
            sb.append("}\n");
        }
        return sb.toString();
    }

    public StyleClass addClasses(String... names) {
        StyleClass styleClass = new StyleClass(names);
        classes.add(styleClass);
        return styleClass;
    }

    public StyleClass addClass(String name) {
        StyleClass styleClass = new StyleClass(name);
        classes.add(styleClass);
        return styleClass;
    }

    public class StyleClass {
        List<String> names = new ArrayList<>();
        Map<String,String> attributes = new HashMap<>();

        public StyleClass(String... names) {
            for (String name : names) {
                this.names.add(name);
            }
        }

        public void addName(String name) {
            names.add(name);
        }

        public StyleClass(String name) {
            names.add(name);
        }

        public String getName() {
            return names.getFirst();
        }

        public List<String> getNames() {
            return names;
        }

        public void setAttribute(String name, String value) {
            attributes.put(name, value);
        }

        public Map<String,String> getAttributes() {
            return attributes;
        }

        public String getAttribute(String name) {
            return attributes.get(name);
        }
    }
}
