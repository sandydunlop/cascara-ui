package io.github.qishr.cascara.ui.theme;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.common.util.Property;
import io.github.qishr.cascara.lang.json.ast.JsonMapNode;
import io.github.qishr.cascara.lang.json.ast.JsonNode;
import io.github.qishr.cascara.lang.json.ast.JsonScalarNode;
import io.github.qishr.cascara.lang.json.ast.JsonSequenceNode;
import io.github.qishr.cascara.lang.json.processor.JsonParser;
import io.github.qishr.cascara.ui.api.HighlightingToken;
import io.github.qishr.cascara.ui.color.ColorDefinition;
import io.github.qishr.cascara.ui.color.ColorException;
import io.github.qishr.cascara.ui.color.ColorUtil;

public class VSCodeTheme {
    private static Properties uiMappingToVSCode = new Properties();
    private static Properties uiMappingToCascara = new Properties();
    private static Properties defaultUiColors = new Properties();
    private static Properties defaultHlColors = new Properties();
    private static FallbackColors fallbackColors = new FallbackColors();

    private Properties uiColors = new Properties();
    private Properties hlColors = new Properties();

    private List<CodeTokenCategory> categories = new ArrayList<>();

    protected VSCodeTheme() {
        if (VSCodeTheme.uiMappingToVSCode.isEmpty()) {
            VSCodeTheme.initializeMapping(uiMappingToVSCode);
            VSCodeTheme.loadDefaultColors();
        }
    }

    public VSCodeTheme(String jsonString) throws ColorException {
        this();
        load(jsonString);
    }

    //
    //
    //

    private static void initializeMapping(Properties map) {
        mapColorId(map, ColorID.FOCUS_BORDER, str("focusBorder"));
        mapColorId(map, ColorID.FOREGROUND, str("foreground"));
        mapColorId(map, ColorID.ERROR_FOREGROUND, str("errorForeground"));
        mapColorId(map, ColorID.ICON_FOREGROUND, str("icon.foreground"));
        mapColorId(map, ColorID.SELECTION_BACKGROUND, str("selection.background"));


        mapColorId(map, ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND, str("activityBar.inactiveForeground"));
        mapColorId(map, ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND, str("activityBar.background"));
        mapColorId(map, ColorID.ACTIVITYBAR_ACTIVE_BACKGROUND, str("activityBar.activeBackground"));
        mapColorId(map, ColorID.ACTIVITYBAR_ACTIVE_FOREGROUND, str("activityBar.foreground"));
        mapColorId(map, ColorID.ACTIVITYBAR_BADGE_BACKGROUND, str("activityBarBadge.background"));
        mapColorId(map, ColorID.ACTIVITYBAR_BADGE_FOREGROUND, str("activityBarBadge.foreground"));

		mapColorId(map, ColorID.ACTIVITYBAR_TOGGLED_BACKGROUND, str("actionBar.toggledBackground"));
		mapColorId(map, ColorID.ACTIVITYBAR_ACTIVE_BORDER, str("activityBar.activeBorder"));
		mapColorId(map, ColorID.ACTIVITYBAR_DROP_BORDER, str("activityBar.dropBorder"));

        mapColorId(map, ColorID.ACTIVITYBARTOP_ACTIVE_BORDER, str("activityBarTop.activeBorder"));
		mapColorId(map, ColorID.ACTIVITYBARTOP_DROP_BORDER, str("activityBarTop.dropBorder"));
		mapColorId(map, ColorID.ACTIVITYBARTOP_ACTIVE_FOREGROUND, str("activityBarTop.foreground"));
		mapColorId(map, ColorID.ACTIVITYBARTOP_INACTIVE_FOREGROUND, str("activityBarTop.inactiveForeground"));

        // mapColorId(map, ColorID.ACTIVITYBAR_ERRORBADGE_BACKGROUND, str("activityErrorBadge.background"));
		// mapColorId(map, ColorID.ACTIVITYBAR_ERRORBADGE_FOREGROUND, str("activityErrorBadge.foreground"));

        // mapColorId(map, ColorID.ACTIVITYBAR_WARNINGBADGE_BACKGROUND, str("activityWarningBadge.background"));
		// mapColorId(map, ColorID.ACTIVITYBAR_WARNINGBADGE_FOREGROUND, str("activityWarningBadge.foreground"));

		mapColorId(map, ColorID.BADGE_INFO_BACKGROUND, str("badge.background"));
		mapColorId(map, ColorID.BADGE_INFO_FOREGROUND, str("badge.foreground"));
		mapColorId(map, ColorID.BADGE_ERROR_BACKGROUND, str("activityErrorBadge.background"));
		mapColorId(map, ColorID.BADGE_ERROR_FOREGROUND, str("activityErrorBadge.foreground"));
		mapColorId(map, ColorID.BADGE_WARN_BACKGROUND, str("activityWarningBadge.background"));
		mapColorId(map, ColorID.BADGE_WARN_FOREGROUND, str("activityWarningBadge.foreground"));

        mapColorId(map, ColorID.BUTTON_BACKGROUND, str("button.background"));
        mapColorId(map, ColorID.BUTTON_FOREGROUND, str("button.foreground"));
        mapColorId(map, ColorID.BUTTON_HOVER_BACKGROUND, str("button.hoverBackground"));

		mapColorId(map, ColorID.CHECKBOX_BORDER, str("checkbox.border"));
		mapColorId(map, ColorID.CHECKBOX_BACKGROUND, str("checkbox.background"));
		mapColorId(map, ColorID.CHECKBOX_DISABLED_BACKGROUND, str("checkbox.disabled.background"));
		mapColorId(map, ColorID.CHECKBOX_DISABLED_FOREGROUND, str("checkbox.disabled.foreground"));
		mapColorId(map, ColorID.CHECKBOX_FOREGROUND, str("checkbox.foreground"));
		mapColorId(map, ColorID.CHECKBOX_SELECTED_BACKGROUND, str("checkbox.selectBackground"));
		mapColorId(map, ColorID.CHECKBOX_SELECTED_BORDER, str("checkbox.selectBorder"));

        mapColorId(map, ColorID.DOCAREA_HEADER_NOTABS_BACKGROUND, str("editorGroupHeader.noTabsBackground"));
        mapColorId(map, ColorID.DOCAREA_HEADER_TABS_BACKGROUND, str("editorGroupHeader.tabsBackground"));

        mapColorId(map, ColorID.DISABLED_FOREGROUND, str("disabledForeground"));

        mapColorId(map, ColorID.DROPDOWN_BACKGROUND, str("dropdown.background"));
        mapColorId(map, ColorID.DROPDOWN_FOREGROUND, str("dropdown.border"));
        mapColorId(map, ColorID.DROPDOWN_BORDER, str("dropdown.foreground"));

        mapColorId(map, ColorID.EDITOR_BACKGROUND, str("editor.background"));
        mapColorId(map, ColorID.EDITOR_FOREGROUND, str("editor.foreground"));
        mapColorId(map, ColorID.EDITOR_SELECTION_ACTIVE_BACKGROUND, str("editor.selectionBackground"));
        mapColorId(map, ColorID.EDITOR_SELECTION_FOREGROUND, str("editor.selectionForeground"));
        mapColorId(map, ColorID.EDITOR_SELECTION_INACTIVE_BACKGROUND, str("editor.inactiveSelectionBackground"));
        mapColorId(map, ColorID.EDITOR_MATCH_BACKGROUND, str("editor.findMatchBackground"));
        mapColorId(map, ColorID.EDITOR_MATCH_FOREGROUND, str("editor.findMatchForeground"));
        mapColorId(map, ColorID.EDITOR_MATCH_BORDER, str("editor.findMatchBorder"));
        mapColorId(map, ColorID.EDITOR_MATCH_HIGHLIGHT_BACKGROUND, str("editor.findMatchHighlightBackground"));
        mapColorId(map, ColorID.EDITOR_MATCH_HIGHLIGHT_FOREGROUND, str("editor.findMatchHighlightForeground"));
        mapColorId(map, ColorID.EDITOR_CURSOR_BACKGROUND, str("editorCursor.background"));
        mapColorId(map, ColorID.EDITOR_CURSOR_FOREGROUND, str("editorCursor.foreground"));
        mapColorId(map, ColorID.EDITOR_INDENTGUIDE_INACTIVE_BACKGROUND, str("editorIndentGuide.background1"));
        mapColorId(map, ColorID.EDITOR_INDENTGUIDE_ACTIVE_BACKGROUND, str("editorIndentGuide.activeBackground1"));
        mapColorId(map, ColorID.EDITOR_SELECTION_HIGHLIGHT_BACKGROUND, str("editor.selectionHighlightBackground"));
        mapColorId(map, ColorID.EDITOR_ERROR_FOREGROUND, str("editorError.foreground"));
        mapColorId(map, ColorID.EDITOR_WARN_FOREGROUND, str("editorWarning.foreground"));
        mapColorId(map, ColorID.EDITOR_INFO_FOREGROUND, str("editorInfo.foreground"));

        mapColorId(map, ColorID.EDITOR_WORD_HIGHLIGHT_BACKGROUND, str("editor.wordHighlightBackground"));
        mapColorId(map, ColorID.EDITOR_WORD_HIGHLIGHT_STRONG_BACKGROUND, str("editor.wordHighlightStrongBackground"));



        mapColorId(map, ColorID.GIT_UNTRACKED_FOREGROUND, str("gitDecoration.untrackedResourceForeground"));
        mapColorId(map, ColorID.GIT_MODIFIED_FOREGROUND, str("gitDecoration.modifiedResourceForeground"));

        mapColorId(map, ColorID.INPUT_PLACEHOLDER_FOREGROUND, str("input.placeholderForeground"));
        mapColorId(map, ColorID.INPUT_FOREGROUND, str("input.foreground"));
        mapColorId(map, ColorID.INPUT_BACKGROUND, str("input.background"));
        mapColorId(map, ColorID.INPUT_BORDER, str("input.border"));

        mapColorId(map, ColorID.INPUT_VALIDATION_ERROR_BACKGROUND, str("inputValidation.errorBackground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_ERROR_FOREGROUND, str("inputValidation.errorForeground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_ERROR_BORDER, str("inputValidation.errorBorder"));
        mapColorId(map, ColorID.INPUT_VALIDATION_INFO_BACKGROUND, str("inputValidation.infoBackground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_INFO_FOREGROUND, str("inputValidation.infoForeground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_INFO_BORDER, str("inputValidation.infoBorder"));
        mapColorId(map, ColorID.INPUT_VALIDATION_WARNING_BACKGROUND, str("inputValidation.warningBackground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_WARNING_FOREGROUND, str("inputValidation.warningForeground"));
        mapColorId(map, ColorID.INPUT_VALIDATION_WARNING_BORDER, str("inputValidation.warningBorder"));

        mapColorId(map, ColorID.LINK_INACTIVE_FOREGROUND, str("textLink.foreground"));
        mapColorId(map, ColorID.LINK_ACTIVE_FOREGROUND, str("textLink.activeForeground"));

        mapColorId(map, ColorID.LIST_FOCUS_BACKGROUND, str("list.focusBackground"));
        mapColorId(map, ColorID.LIST_FOCUS_FOREGROUND, str("list.focusForeground"));
        mapColorId(map, ColorID.LIST_SELECTION_ACTIVE_BACKGROUND, str("list.activeSelectionBackground"));
        mapColorId(map, ColorID.LIST_SELECTION_ACTIVE_FOREGROUND, str("list.activeSelectionForeground"));
        mapColorId(map, ColorID.LIST_FOCUS_OUTLINE, str("list.focusOutline"));
        mapColorId(map, ColorID.LIST_SELECTION_FOCUS_OUTLINE, str("list.focusAndSelectionOutline"));
        mapColorId(map, ColorID.LIST_SELECTION_INACTIVE_BACKGROUND, str("list.inactiveSelectionBackground"));
        mapColorId(map, ColorID.LIST_SELECTION_INACTIVE_FOREGROUND, str("list.inactiveSelectionForeground"));
        mapColorId(map, ColorID.LIST_HOVER_BACKGROUND, str("list.hoverBackground"));
        mapColorId(map, ColorID.LIST_WARNING_FOREGROUND, str("list.warningForeground"));
        mapColorId(map, ColorID.LIST_DROP_BACKGROUND, str("list.dropBackground"));
        mapColorId(map, ColorID.LIST_SELECTION_ACTIVE_ICON_FOREGROUND, str("list.activeSelectionIconForeground"));

        mapColorId(map, ColorID.MENU_BORDER, str("menu.border"));
        mapColorId(map, ColorID.MENU_FOREGROUND, str("menu.foreground"));
        mapColorId(map, ColorID.MENU_BACKGROUND, str("menu.background"));
        mapColorId(map, ColorID.MENU_BAR_SELECTION_BACKGROUND, str("menubar.selectionBackground"));
        mapColorId(map, ColorID.MENU_BAR_SELECTION_FOREGROUND, str("menubar.selectionForeground"));
        mapColorId(map, ColorID.MENU_SELECTION_FOREGROUND, str("menu.selectionForeground"));
        mapColorId(map, ColorID.MENU_SELECTION_BACKGROUND, str("menu.selectionBackground"));
        mapColorId(map, ColorID.MENU_SELECTION_BORDER, str("menu.selectionBorder"));
        mapColorId(map, ColorID.MENU_SEPARATOR_BACKGROUND, str("menu.separatorBackground"));

        mapColorId(map, ColorID.NOTIFICATION_BACKGROUND, str("notifications.background"));
        mapColorId(map, ColorID.NOTIFICATION_FOREGROUND, str("notifications.foreground"));
        mapColorId(map, ColorID.NOTIFICATION_BORDER, str("notifications.border"));
        mapColorId(map, ColorID.NOTIFICATION_LINK, str("notificationLink.foreground"));
        mapColorId(map, ColorID.NOTIFICATION_ICON_INFO, str("notificationsInfoIcon.foreground"));
        mapColorId(map, ColorID.NOTIFICATION_ICON_ERROR, str("notificationsErrorIcon.foreground"));
        mapColorId(map, ColorID.NOTIFICATION_ICON_WARNING, str("notificationsWarningIcon.foreground"));

        // TOOO:
        // notificationToast.border: Notification toast border color.
        // notificationCenter.border: Notification Center border color.
        // notificationCenterHeader.foreground: Notification Center header foreground color.
        // notificationCenterHeader.background: Notification Center header background color.

        mapColorId(map, ColorID.PANEL_BACKGROUND, str("panel.background"));

        mapColorId(map, ColorID.PORTS_ICON_RUNNING_PROCESS_FOREGROUND, str("ports.iconRunningProcessForeground"));

        mapColorId(map, ColorID.RADIO_ACTIVE_BACKGROUND, str("radio.activeBackground"));
		mapColorId(map, ColorID.RADIO_ACTIVE_BORDER, str("radio.activeBorder"));
		mapColorId(map, ColorID.RADIO_ACTIVE_FOREGROUND, str("radio.activeForeground"));
		mapColorId(map, ColorID.RADIO_INACTIVE_BORDER, str("radio.inactiveBorder"));
		mapColorId(map, ColorID.RADIO_INACTIVE_HOVER_BACKGROUND, str("radio.inactiveHoverBackground"));

        mapColorId(map, ColorID.SCROLL_THUMB_BACKGROUND, str("scrollbarSlider.background"));
        mapColorId(map, ColorID.SCROLL_THUMB_HOVER_BACKGROUND, str("scrollbarSlider.hoverBackground"));
        mapColorId(map, ColorID.SCROLL_THUMB_ACTIVE_BACKGROUND, str("scrollbarSlider.activeBackground"));
        mapColorId(map, ColorID.SCROLL_SHADOW, str("scrollbar.shadow"));

        mapColorId(map, ColorID.SIDEBAR_BACKGROUND, str("sideBar.background"));
        mapColorId(map, ColorID.SIDEBAR_FOREGROUND, str("sideBar.foreground"));
        mapColorId(map, ColorID.SIDEBAR_DROP_BACKGROUND, str("sideBar.dropBackground"));
        mapColorId(map, ColorID.SIDEBAR_BORDER, str("sideBar.border"));
        mapColorId(map, ColorID.SIDEBAR_TITLE_FOREGROUND, str("sideBarTitle.foreground"));
        mapColorId(map, ColorID.SIDEBAR_TITLE_BACKGROUND, str("sideBarTitle.background"));
        mapColorId(map, ColorID.SIDEBAR_HEADER_BACKGROUND, str("sideBarSectionHeader.background"));
        mapColorId(map, ColorID.SIDEBAR_HEADER_FOREGROUND, str("sideBarSectionHeader.foreground"));
        mapColorId(map, ColorID.SIDEBAR_HEADER_BORDER, str("sideBarSectionHeader.border"));

        mapColorId(map, ColorID.STATUSBAR_BACKGROUND, str("statusBar.background"));
        mapColorId(map, ColorID.STATUSBAR_FOREGROUND, str("statusBar.foreground"));
        mapColorId(map, ColorID.STATUSBAR_NOFOLDER_BACKGROUND, str("statusBar.noFolderBackground"));
        mapColorId(map, ColorID.STATUSBAR_DEBUGGING_BACKGROUND, str("statusBar.debuggingBackground"));
        mapColorId(map, ColorID.STATUSBAR_DEBUGGING_FOREGROUND, str("statusBar.debuggingForeground"));
        mapColorId(map, ColorID.STATUSBAR_REMOTE_BACKGROUND, str("statusBarItem.remoteBackground"));
        mapColorId(map, ColorID.STATUSBAR_REMOTE_FOREGROUND, str("statusBarItem.remoteForeground"));
        mapColorId(map, ColorID.STATUSBAR_REMOTE_HOVER_BACKGROUND, str("statusBarItem.remoteHoverBackground"));
        mapColorId(map, ColorID.STATUSBAR_REMOTE_HOVER_FOREGROUND, str("statusBarItem.remoteHoverForeground"));
        mapColorId(map, ColorID.STATUSBAR_HOVER_BACKGROUND, str("statusBarItem.hoverBackground"));
        mapColorId(map, ColorID.STATUSBAR_HOVER_FOREGROUND, str("statusBarItem.hoverForeground"));

        mapColorId(map, ColorID.TAB_ACTIVE_BACKGROUND, str("tab.activeBackground"));
        mapColorId(map, ColorID.TAB_ACTIVE_BORDER, str("tab.activeBorder"));
        mapColorId(map, ColorID.TAB_ACTIVE_FOREGROUND, str("tab.activeForeground"));
        mapColorId(map, ColorID.TAB_INACTIVE_BACKGROUND, str("tab.inactiveBackground"));
        mapColorId(map, ColorID.TAB_INACTIVE_FOREGROUND, str("tab.inactiveForeground"));
        mapColorId(map, ColorID.TAB_SELECTED_BORDER_TOP, str("tab.selectedBorderTop"));
        mapColorId(map, ColorID.TAB_SELECTED_BACKGROUND, str("tab.selectedBackground"));
        mapColorId(map, ColorID.TAB_SELECTED_FOREGROUND, str("tab.selectedForeground"));
        mapColorId(map, ColorID.TAB_LAST_PINNED_BORDER, str("tab.lastPinnedBorder"));

        mapColorId(map, ColorID.TERMINAL_SELECTION_INACTIVE_BACKGROUND, str("terminal.inactiveSelectionBackground"));

        mapColorId(map, ColorID.TITLEBAR_ACTIVE_BACKGROUND, str("titleBar.activeBackground"));
        mapColorId(map, ColorID.TITLEBAR_ACTIVE_FOREGROUND, str("titleBar.activeForeground"));
        mapColorId(map, ColorID.TITLEBAR_INACTIVE_BACKGROUND, str("titleBar.inactiveBackground"));

        mapColorId(map, ColorID.TOOLTIP_BORDER, str("editorHoverWidget.border"));

        mapColorId(map, ColorID.WELCOMEPAGE_BACKGROUND, str("welcomePage.background"));

        mapColorId(map, ColorID.WIDGET_BORDER, str("widget.border"));
        mapColorId(map, ColorID.WIDGET_SHADOW, str("widget.shadow"));
    }

    private static void mapColorId(Properties map, String name, String vsid) {
        map.set(name, vsid);
        uiMappingToCascara.set(vsid, name);
    }

    private static String str(String str) {
        return str;
    }

    public static String getVscodeId(String name) {
        return uiMappingToVSCode.getString(name);
    }

    //
    //
    //

    private static void loadDefaultColors() {
        try {
            String themeString = getTextResource("theme.json");
            JsonParser parser = new JsonParser();
            JsonMapNode json = (JsonMapNode) parser.parse(themeString);
            loadUiColors(json, defaultUiColors);
            List<CodeTokenCategory> tmpCats = new ArrayList<>();
            loadSyntaxColors(json, defaultHlColors, getUiColor("editor.foreground", defaultUiColors), tmpCats);
        } catch (IOException | ColorException e) {
            e.printStackTrace();
        }
    }

    // private static void loadDefaultColors() {
    //     // CascaraTheme theme = ThemeEngine.getInstance().getTheme("default");
    //     // Variation defaultVariation = theme.getVariations().getFirst();
    //     // try {
    //     //     String jsonString = getVariationJson(defaultVariation);
    //     //     JsonParser parser = new JsonParser();
    //     //     JsonMapNode json = (JsonMapNode) parser.parse(jsonString).getRoot();
    //     //     loadUiColors(json, defaultUiColors);
    //     //     List<CodeTokenCategory> tmpCats = new ArrayList<>();
    //     //     loadSyntaxColors(json, defaultHlColors, getUiColor("editor.foreground", defaultUiColors), tmpCats);
    //     // } catch (ColorException e) {
    //     //     e.printStackTrace();
    //     // }
    // }

    //
    //
    //

    public ThemeVariation getVariation() throws ColorException {
        ThemeVariation variation = new ThemeVariation();
        for (Property colorProp : uiColors.asList()) {
            variation.setUiColor(colorProp.getName(), colorProp.getString());
        }
        for (Property colorProp : hlColors.asList()) {
            variation.setCodeColor(colorProp.getName(), colorProp.getString());
        }
        addDefaultPaletteColors(variation);
        return variation;
    }

    //
    //
    //

    private void addDefaultPaletteColors(ThemeVariation variation) throws ColorException {
        for (String paletteColorId : ColorID.PALETTE_COLORS) {
            String mappedId = ColorID.getDefaultPaletteMapping().getString(paletteColorId);
            variation.setPaletteColor(paletteColorId, getUiColor(mappedId, uiColors));
        }
    }

    private static String getColorForTokenKind(HighlightingToken.Kind kind, String defaultTextColor, List<CodeTokenCategory> cats) {
        String hexColor = defaultTextColor;
        String tokenCategoryName = HighlightingToken.getString(kind);
        CodeTokenCategory tokenCategory = getTokenCategory(tokenCategoryName, cats);
        if (tokenCategory != null) {
            hexColor = tokenCategory.getColor();
        }
        return hexColor;
    }

    public static CodeTokenCategory getTokenCategory(String name, List<CodeTokenCategory> cats) {
        for (CodeTokenCategory tc : cats) {
            if (tc.getName().equals(name)) {
                return tc;
            }
        }
        return null;
    }

    public String getDefaultTextColor() throws ColorException {
        return getUiColor("editor.foreground");
    }

    public String getUiColor(String name) throws ColorException {
        return getUiColor(name, uiColors);
    }

    public static String getUiColor(String name, Properties colors) throws ColorException {
        for (Property prop : colors.asList()) {
            if (prop.getName().equals(name)) {
                return prop.getString();
            }
        }

        String fallbackColor = VSCodeTheme.fallbackColors.resolve(name, colors);
        if (fallbackColor == null) {
            fallbackColor = loadColorFromDefaultTheme(name);
        }
        return fallbackColor;
    }

    private static String loadColorFromDefaultTheme(String vsid) {
        // System.out.println("loadColorFromDefaultTheme " + vsid);
        String val = defaultUiColors.getString(vsid);
        if (val != null) {
            return val;
        }
        return "red";
    }

    //
    // Loading and Exporting
    //

    public void load(String jsonString) throws ColorException {
        JsonParser parser = new JsonParser();
        JsonMapNode json = (JsonMapNode) parser.parse(jsonString);
        load(json);
    }

    private void load(JsonMapNode json) throws ColorException {
        categories = new ArrayList<>();
        uiColors = new Properties();
        hlColors = new Properties();
        loadUiColors(json, uiColors); // UI colors are loaded first so that getDefaultTextColor() works
        loadSyntaxColors(json, hlColors, getDefaultTextColor(), categories);
        addMissingUiColors();
    }

    private static void loadUiColors(JsonMapNode json, Properties colors) {
        JsonMapNode uiColorsJson = json.getMap("colors");
        for (String colorId : ColorID.UI_COLORS) {
            String vsid = uiMappingToVSCode.getString(colorId);
            String hexValue = uiColorsJson.getString(vsid);
            colors.set(colorId, hexValue);
        }
    }

    private static void loadSyntaxColors(JsonMapNode json, Properties colors, String defaultTextColor, List<CodeTokenCategory> cats) {
        cats.clear();
        JsonMapNode syntaxColorsJson = json.getMap("tokenColors");
        for (JsonNode keyNode : syntaxColorsJson.keySet()) {
            if (!(keyNode instanceof JsonScalarNode scalarKey)) continue;
            JsonMapNode jsonCat = syntaxColorsJson.getMap(scalarKey.asString());
            String name = jsonCat.getString("name");
            JsonSequenceNode jsonScope = jsonCat.getSequence("scope");
            JsonMapNode jsonSettings = jsonCat.getMap("settings");

            CodeTokenCategory tc = new CodeTokenCategory();
            tc.setName(name.toLowerCase());

            if (jsonScope != null) {
                for (JsonNode node : jsonScope.getElements()) {
                    if (node instanceof JsonScalarNode scalar) {
                        tc.getScope().add(scalar.asString());
                    }
                }
            }
            for (JsonNode settingsKey : jsonSettings.keySet()) {
                if (settingsKey instanceof JsonScalarNode scalar) {
                    String propName = scalar.asString();
                    String propValue = jsonSettings.getString(propName);
                    Property prop = new Property(propName, propValue);
                    if (propName.equals("foreground")) {
                        tc.setColor(propValue);
                    }
                    tc.getSettings().add(prop);
                }
            }

            cats.add(tc);
        }
        addHightlightColors(colors, defaultTextColor, cats);
    }

    public static String getVariationJson(Variation variation) throws ColorException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"name\": \"exported theme\"\n");

        sb.append("  \"colors\": {\n");
        for (Entry<String, ColorDefinition> entry : variation.getUiColors().entrySet()) {
            ColorDefinition colordef = entry.getValue();
            String vsid = VSCodeTheme.getVscodeId(colordef.getId());
            ColorUtil.processColor(colordef, variation);
            String value = colordef.getHexColor();
            sb.append("    \"" + vsid + "\": \"" + value + "\",\n");
        }
        sb.append("  },\n");

        sb.append("  \"tokenColors\": [\n");
        for (Entry<String, ColorDefinition> entry : variation.getCodeColors().entrySet()) {
            String name = entry.getKey();
            // ColorDefinition def = entry.getValue();
            sb.append("    {\n");
            sb.append("      \"settings\": {\\n");
            sb.append("      },\\n");
            sb.append("      \"scope\": [\\n");
            sb.append("      ],\\n");
            sb.append("      \"name\": \" " + name + "\"\n");
            sb.append("    }\n");
        }
        sb.append("  ]\n");
        sb.append("}\n");

        return sb.toString();
    }

    // {
    //     "settings": {
    //         "foreground": "#a0d0ea",
    //         "fontStyle": "italic"
    //     },
    //     "scope": [
    //         "storage.type.annotation.java",
    //         "meta.annotation.java",
    //         "decorator"
    //     ],
    //     "name": "annotation"
    // },


    //
    //
    //

    private static void addHightlightColors(Properties colors, String defaultTextColor, List<CodeTokenCategory> cats) {
        Set<HighlightingToken.Kind> kinds = HighlightingToken.getKinds();
        for (HighlightingToken.Kind kind : kinds) {
            String colorId = kind.toString().toLowerCase();
            colors.set(colorId, getColorForTokenKind(kind, defaultTextColor, cats));
        }
    }

    private void addMissingUiColors() throws ColorException {
        for (String id : ColorID.UI_COLORS) {
            Property colorprop = uiColors.get(id);
            if (colorprop == null) {
                colorprop = new Property(id);
                uiColors.add(colorprop);
            }
            if(colorprop.isEmpty()) {
                String fallbackColor = getFallbackUiColor(id);
                colorprop.setValue(fallbackColor);
            }
        }
    }

    private String getFallbackUiColor(String id) throws ColorException {
        String fallbackColor = VSCodeTheme.fallbackColors.resolve(id, uiColors);
        if (fallbackColor == null || fallbackColor.isBlank()) {
            fallbackColor = defaultUiColors.getString(id);
        }
        if (fallbackColor == null || fallbackColor.isBlank()) {
            fallbackColor = "#FF00FF";
        }
        return fallbackColor;
    }

    public static String getTextResource(String resourcePath) throws IOException {
        InputStream is = VSCodeTheme.class.getResourceAsStream(resourcePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw e;
        }
    }

    //
    //
    //

    /// Fills in missing palette colors in the variation
    public static void populateMissingColorGroups(ThemeVariation variation) {
        for (String paletteColorId : ColorID.PALETTE_COLORS) {
            ColorDefinition colordef = variation.getPaletteColor(paletteColorId);
            String colorId = ColorID.getDefaultPaletteMapping().getString(paletteColorId);
            if (colordef == null) {
                colordef = variation.getUiColor(colorId);
                variation.setPaletteColor(paletteColorId, colordef.duplicate());
            }
        }
    }

    public static void populateMissingColorNames(ThemeVariation variation) {
        for (String colorId : ColorID.UI_COLORS) {
            ColorDefinition colordef = variation.getUiColor(colorId);
            if (colordef == null) {
                String fallbackColor = getFallbackUiColor(colorId, variation.getUiColors());
                if (fallbackColor != null) {
                    colordef = new ColorDefinition(fallbackColor);
                    colordef.setId(colorId);
                    colordef.setName(colorId);
                    variation.getUiColors().put(colorId, colordef);
                }
            } else if (colordef.getName().equals("<name>")) {
                colordef.setName(colorId);
            }
        }
    }

    // TODO: Similar method exists in VSCodeTheme. It deals with current UI Colors
    // This one deals with color defs passed in that came from a theme variation
    public static String getFallbackUiColor(String id, Map<String, ColorDefinition> colors) {
        Properties colorProperties = new Properties();
        for (Entry<String, ColorDefinition> entry : colors.entrySet()) {
            ColorDefinition colordef = entry.getValue();
            colorProperties.set(colordef.getName(), colordef.getHexColor());
        }
        String fallbackColor;
        try {
            fallbackColor = VSCodeTheme.fallbackColors.resolve(id, colorProperties);
            return fallbackColor;
        } catch (ColorException e) {
            return null;
        }
    }
}
