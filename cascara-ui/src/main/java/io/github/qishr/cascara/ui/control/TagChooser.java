package io.github.qishr.cascara.ui.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.github.qishr.cascara.ui.option.Option;
import io.github.qishr.cascara.ui.option.OptionProvider;
import io.github.qishr.cascara.ui.option.TagOption;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TagChooser extends FlowPane { // implements OptionListEditor {
    private final TextField input = new TextField();
    @SuppressWarnings("rawtypes")
    private ObservableList chosenTags;
    private OptionProvider provider;
    private String providerParameter;
    private final ContextMenu suggestionsMenu = new ContextMenu();
    private final Stack<TagOption> activeFolders = new Stack<>();
    private boolean isHierarchyMode = false;
    private MenuItem activeMenuItem = null;

    // // For SPI
    // public TagChooser() {}

    public TagChooser(ObservableList<TagOption> chosenTags, OptionProvider provider, String parameter) {
        configure(chosenTags, provider, parameter);
    }

    // @Override
    // public String getContentType() { return "cascara/tag"; }

    private void configure(ObservableList<?> chosenTags, OptionProvider provider, String parameter) {
        this.providerParameter = parameter;
        this.chosenTags = chosenTags;
        this.provider = provider;

        this.getStyleClass().addAll("tag-chooser", "text-input");
        this.setHgap(5);
        this.setVgap(5);

        setupInput();
        updateLayout();

        chosenTags.addListener((ListChangeListener.Change<?> c) -> updateLayout());

        suggestionsMenu.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.TAB || e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                // Get the currently focused or first item
                if (!suggestionsMenu.getItems().isEmpty()) {
                    if (activeMenuItem == null) {
                        activeMenuItem = suggestionsMenu.getItems().stream()
                        .filter(MenuItem::isMnemonicParsing) // A trick to find active items if needed
                        .findFirst().orElse(suggestionsMenu.getItems().get(0));
                    }
                    activeMenuItem.fire(); // This triggers the setOnAction we defined
                }
                e.consume();
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                activeFolders.clear();
                updateLayout();
            }
        });

        // Focus input when the container is clicked
        this.setOnMouseClicked(e -> input.requestFocus());

        this.setPrefWrapLength(300);
    }

    public void requestFocus() {
        input.requestFocus();
    }

    private void updateLayout() {
        this.getChildren().clear();

        // 1. Create the FlowPane for tags/input
        FlowPane flow = new FlowPane();
        flow.setHgap(5);
        flow.setVgap(5);
        flow.setAlignment(Pos.TOP_LEFT);
        flow.prefWrapLengthProperty().bind(this.widthProperty().subtract(70));
        addContent(flow); // Fills flow with pills, breadcrumbs, and input

        // 2. Wrap everything in a Top-Aligned HBox
        HBox mainLayout = new HBox(flow);
        mainLayout.setAlignment(Pos.TOP_LEFT); // Forces alignment to the top
        HBox.setHgrow(flow, Priority.ALWAYS);

        // 3. Add Clear Button if needed
        if (!chosenTags.isEmpty() || !activeFolders.isEmpty()) {
            Node clearBtn = createClearButton();
            HBox.setMargin(clearBtn, new javafx.geometry.Insets(7, 7, 0, 0));
            mainLayout.getChildren().add(clearBtn);
        }

        this.getChildren().add(mainLayout);
    }

    private void addContent(FlowPane flow) {
        for (Object obj : chosenTags) {
            if (obj instanceof TagOption tag) {
                if (tag != null) {
                    Pill pill = new Pill(tag);
                    pill.setOnRemoveClicked(() -> {
                        chosenTags.remove(tag);
                    });
                    flow.getChildren().add(pill);
                }
            }
        }

        // Display breadcrumb-style path to active folder
        if (!activeFolders.isEmpty()) {
            for (int i = 0; i < activeFolders.size(); i++) {
                Label label = new Label(activeFolders.elementAt(i).getOptionText() + " > ");
                flow.getChildren().add(label);
            }
        }

        if (chosenTags.isEmpty() && activeFolders.isEmpty()) {
            // Bind to the container width so it spans the whole line
            input.prefWidthProperty().bind(flow.widthProperty().subtract(20));
            input.setPromptText("Type tag names or '/' to select hierarchically");
        } else {
            // Back to a smaller fixed width when tags exist
            input.prefWidthProperty().unbind();
            input.setPrefWidth(100);
            input.setPromptText("");
        }

        flow.getChildren().add(input);
    }

    private Node createClearButton() {
        if (chosenTags.isEmpty()) return new Region();

        Label clearBtn = new Label("Clear All");
        clearBtn.setStyle("-fx-text-fill: -color-input-placeholder-foreground; -fx-cursor: hand; -fx-font-size: 10px;");
        clearBtn.setOnMouseEntered(e -> clearBtn.setStyle("-fx-text-fill: -color-button-foreground; -fx-cursor: hand; -fx-font-size: 10px;"));
        clearBtn.setOnMouseExited(e -> clearBtn.setStyle("-fx-text-fill: -color-input-placeholder-foreground; -fx-cursor: hand; -fx-font-size: 10px;"));

        clearBtn.setOnMouseClicked(e -> {
            activeFolders.clear();
            chosenTags.clear();
        });
        return clearBtn;
    }

    private void setupInput() {
        input.setBackground(Background.EMPTY);
        input.setPrefWidth(100);

        // Filter suggestions menu
        input.textProperty().addListener((obs, oldVal, newVal) -> showSuggestions(newVal));

        input.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            // In the input box, we only need to deal with backspace.
            // Other key presses are handled in the popup.
            if (e.getCode() == KeyCode.BACK_SPACE) {
                // If the field is totally empty, we handle tag removal
                if (input.getText().isEmpty()) {
                    if (activeFolders.isEmpty()) {
                        if (!chosenTags.isEmpty()) {
                            chosenTags.remove(chosenTags.size() - 1);
                        }
                    } else{
                        activeFolders.pop();
                        updateLayout();
                    }
                    e.consume(); // Prevent any further processing
                }
                // If it's NOT empty, we do NOTHING.
                // We let the event pass through so the TextField can delete its own character.
            }
        });
    }

    private void showSuggestions(String query) {
        String searchLower;
        isHierarchyMode = !activeFolders.isEmpty();

        if (query.startsWith("#") || query.startsWith("/")) {
            isHierarchyMode = true;
            searchLower = query.substring(1).toLowerCase();
        } else {
            searchLower = query.toLowerCase();
        }

        if (!isHierarchyMode && query.equals("")) {
            suggestionsMenu.hide();
            suggestionsMenu.getItems().clear();
            input.requestFocus();
            return;
        }

        List<? extends Option> allOptions = provider.getOptions(null, providerParameter);

        List<MenuItem> items = getMatchingItems(allOptions, searchLower);

        setSuggestionsMenuItems(items);
        input.requestFocus();
    }

    void setSuggestionsMenuItems(List<MenuItem> items) {
        activeMenuItem = null;
        suggestionsMenu.hide();
        if (items.isEmpty()) {
            suggestionsMenu.getItems().clear();
        } else {
            suggestionsMenu.getItems().setAll(items);
            suggestionsMenu.getEventDispatcher();
            suggestionsMenu.show(input, Side.BOTTOM, 0, 0);
        }
    }

    @SuppressWarnings("unchecked")
    List<MenuItem> getMatchingItems(List<? extends Option> allOptions, String query) {
        List<MenuItem> items = new ArrayList<>();

        for (Option o : allOptions) {
            TagOption opt = (TagOption)o;
            if (!isHierarchyMode && contains(opt, chosenTags)) {
                continue;
            }

            boolean matches = matches(query, opt);

            if (matches) {
                // Create the item and immediately set up its click behavior
                MenuItem item = createSuggestionItem(opt, isHierarchyMode, allOptions);
                item.setUserData(opt);

                // Override the action to use our new activeFolder navigation
                item.setOnAction(e -> handleSelection(opt, allOptions));

                items.add(item);
            }
        }

        return items;
    }

    boolean contains(TagOption needle, ObservableList<TagOption> haystack) {
        for (TagOption tag : haystack) {
            if (tag.getOptionId().equals(needle.getOptionId())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    void handleSelection(TagOption opt, List<? extends Option> allOptions) {
        if (isHierarchyMode && hasChildren(opt, allOptions)) {
            activeFolders.push(opt);
            updateLayout();
            input.clear();
            suggestionsMenu.getItems().clear();
            suggestionsMenu.hide();
            showSuggestions("");
            input.requestFocus();
        } else {
            // This is a commit (Space/Enter)
            activeFolders.clear();
            if (!chosenTags.contains(opt)) {
                chosenTags.add(opt);
            }
            input.clear();
            suggestionsMenu.getItems().clear();
            suggestionsMenu.hide();
            input.requestFocus();
        }
    }


    boolean matches(String query, TagOption opt) {
        boolean matches = false;
        String optText = opt.getOptionText().toLowerCase();
        if (isHierarchyMode) {
            if (activeFolders.isEmpty()) {
                // Show roots
                if (isRoot(opt) && (query.isEmpty() || optText.startsWith(query))) {
                    matches = true;
                }
            } else {
                // Show children of the active folder
                if (opt instanceof TagOption tag) {
                    if (activeFolders.peek().getOptionId().equals(tag.getParentId())) {
                        if (query.isEmpty() || optText.startsWith(query)) {
                            matches = true;
                        }
                    }
                }
            }
        } else {
            if (optText.contains(query)) {
                matches = true;
            }
        }
        return matches;
    }

    private boolean isRoot(Option opt) {
        if (opt instanceof TagOption tag) {
            return tag.getParentId() == null || tag.getParentId().isEmpty();
        }
        return true;
    }

    private boolean hasChildren(Option parent, List<? extends Option> allOptions) {
        System.out.println("hasChildren: " + parent.getOptionText());
        for (Option opt : allOptions) {
            if (opt instanceof TagOption tag) {
                System.out.println("  Comparing: " + tag.getParentId() + " to " + parent.getOptionId());
                if (parent.getOptionId().equals(tag.getParentId())) {
                    System.out.println("  True");
                    return true;
                }
            }
        }
        System.out.println("  False");
        return false;
    }

    private MenuItem createSuggestionItem(Option opt, boolean isHierarchy, List<? extends Option> allOptions) {
        boolean canDrillDown = isHierarchy && hasChildren(opt, allOptions);
        String labelText = opt.getOptionText() + (canDrillDown ? " \u25B8" : "");

        Label content = new Label(labelText);
        content.setMaxWidth(Double.MAX_VALUE); // Fill the menu width

        if (opt instanceof TagOption tag) {
            Label indicator = new Label(" ");
            indicator.setStyle("-fx-background-color: " + tag.getColor() + "; -fx-padding: 0 4 0 4; -fx-background-radius: 3;");
            content.setGraphic(indicator);
        }

        CustomMenuItem item = new CustomMenuItem(content, true); // true = hide on click

        // NOW we can use Node properties!
        content.hoverProperty().addListener((obs, wasHovered, isHovered) -> {
            if (isHovered) activeMenuItem = item;
        });

        // This tracks keyboard navigation (arrow keys)
        content.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (newParent != null) {
                newParent.focusedProperty().addListener((o, ov, isFocused) -> {
                    if (isFocused) activeMenuItem = item;
                });
            }
        });

        return item;
    }
}



