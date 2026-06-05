package io.github.qishr.cascara.ui.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Badge {
    private final StringProperty badgeText = new SimpleStringProperty("");
    private final SimpleIntegerProperty badgeNumber = new SimpleIntegerProperty(0);
    private final ObjectProperty<BadgeType> badgeType = new SimpleObjectProperty<>(BadgeType.NONE);

    public Badge(String text, Integer number, BadgeType type) {
        badgeText.set(text);
        badgeNumber.set(number);
        badgeType.set(type);
    }

    public StringProperty badgeTextProperty() { return badgeText; }
    public SimpleIntegerProperty badgeNumberProperty() { return badgeNumber; }
    public ObjectProperty<BadgeType> badgeTypeProperty() { return badgeType; }

    public BadgeType getBadgeType() { return badgeType.get(); }
    public String getBadgeText() { return badgeText.get(); }
    public Integer getBadgeNumber() { return badgeNumber.get(); }

}
