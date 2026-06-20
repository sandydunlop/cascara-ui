package io.github.qishr.cascara.ui.style;


public abstract class PartStyle extends ControlStyle {
    protected String controlClass;

    @Override
    protected String selector(String s) {
        if (s.startsWith(":")) {
            return "." + controlClass + s;
        } else {
            return "." + controlClass + " " + s;
        }
    }

    @Override
    protected String classSelector(String s) {
        return "." + controlClass + " ." + s;
    }

    @Override
    protected String childSelector(String s) {
        return "." + controlClass + " > " + s;
    }

    @Override
    protected String pseudoSelector(String s) {
        return "." + controlClass + ":" + s;
    }
}
