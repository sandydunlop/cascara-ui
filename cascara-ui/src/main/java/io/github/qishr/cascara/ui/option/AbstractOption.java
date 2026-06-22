package io.github.qishr.cascara.ui.option;

public abstract class AbstractOption {
    protected final String id;

    protected AbstractOption(String id) {
        this.id = id;
    }

    public String getOptionId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof AbstractOption option) {
            return id.equals(option.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
