package io.github.qishr.cascara.ui.option;

public interface TagOption extends Option  {
    // private final String color;
    // private final String parentId; // The ID of the parent tag, or null if root
    // private final String id;
    // private final String text;

    // public TagOption(long id, String text, String color) {
    //     this(id, text, color, null);
    // }

    // public TagOption(long id, String text, String color, String parentId) {
    //     this.id = String.valueOf(id);
    //     this.text = text;
    //     this.color = color;
    //     this.parentId = parentId;
    // }

    // public String getColor() { return color; }
    // public String getParentId() { return parentId; }
    // public String getOptionId() { return id; }
    // public String getOptionText() { return text; }

    public String getColor();
    public String getParentId();
}