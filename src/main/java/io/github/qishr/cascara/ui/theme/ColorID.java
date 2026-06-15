package io.github.qishr.cascara.ui.theme;

import java.util.List;

import io.github.qishr.cascara.common.util.Properties;

public class ColorID {

    // Cascara Colors

    public static final String ACCENT_BACKGROUND = "accentBackground";
    public static final String ACCENT_FOREGROUND = "accentForeground";
    public static final String ACCENT_HOVER_BACKGROUND = "accentHoverBackground";
    public static final String CONTROL_BACKGROUND = "controlBackground";
    public static final String CONTROL_FOREGROUND = "controlForeground";
    public static final String DOCUMENT_BACKGROUND = "documentBackground";
    public static final String EMPTY_BACKGROUND = "emptyBackground";
    public static final String TEXT_FOREGROUND = "textForeground";
    public static final String MARK_FOREGROUND = "markForeground";
    public static final String TRANSPARENT = "transparent";

    // VS Code Colors

    public static final String FOCUS_BORDER = "focusBorder";
    public static final String FOREGROUND = "foreground";
    public static final String ERROR_FOREGROUND = "errorForeground";
    public static final String ICON_FOREGROUND = "iconForeground";
    public static final String SELECTION_BACKGROUND = "selectionBackground";

    public static final String ACTIVITYBAR_INACTIVE_FOREGROUND = "activitybarInactiveForeground";
    public static final String ACTIVITYBAR_INACTIVE_BACKGROUND = "activitybarInactiveBackground";
    public static final String ACTIVITYBAR_ACTIVE_BACKGROUND = "activitybarActiveBackground";
    public static final String ACTIVITYBAR_ACTIVE_FOREGROUND = "activitybarActiveForeground";
    public static final String ACTIVITYBAR_BADGE_BACKGROUND = "activitybarBadgeBackground";
    public static final String ACTIVITYBAR_BADGE_FOREGROUND = "activitybarBadgeForeground";

    public static final String ACTIVITYBAR_TOGGLED_BACKGROUND = "activitybarToggledBackground";
    public static final String ACTIVITYBAR_ACTIVE_BORDER = "activitybarActiveBorder";
    public static final String ACTIVITYBAR_DROP_BORDER = "activitybarDropBorder";
    public static final String ACTIVITYBARTOP_ACTIVE_BORDER = "activitybartopActiveBorder";
    public static final String ACTIVITYBARTOP_DROP_BORDER = "activitybartopDropBorder";
    public static final String ACTIVITYBARTOP_ACTIVE_FOREGROUND = "activitybartopActiveForeground";
    public static final String ACTIVITYBARTOP_INACTIVE_FOREGROUND = "activitybartopInactiveForeground";

    // public static final String ACTIVITYBAR_ERRORBADGE_BACKGROUND = "activitybarErrorbadgeBackground";
    // public static final String ACTIVITYBAR_ERRORBADGE_FOREGROUND = "activitybarErrorbadgeForeground";
    // public static final String ACTIVITYBAR_WARNINGBADGE_BACKGROUND = "activitybarWarningbadgeBackground";
    // public static final String ACTIVITYBAR_WARNINGBADGE_FOREGROUND = "activitybarWarningbadgeForeground";

    public static final String BADGE_INFO_BACKGROUND = "badgeInfoBackground";
    public static final String BADGE_INFO_FOREGROUND = "badgeInfoForeground";
    public static final String BADGE_ERROR_BACKGROUND = "badgeErrorBackground";
    public static final String BADGE_ERROR_FOREGROUND = "badgeErrorForeground";
    public static final String BADGE_WARN_BACKGROUND = "badgeWarnBackground";
    public static final String BADGE_WARN_FOREGROUND = "badgeWarnForeground";

    public static final String BUTTON_BACKGROUND = "buttonBackground";
    public static final String BUTTON_FOREGROUND = "buttonForeground";
    public static final String BUTTON_HOVER_BACKGROUND = "buttonHoverBackground";

    public static final String CHECKBOX_BORDER = "checkboxBorder";
    public static final String CHECKBOX_BACKGROUND = "checkboxBackground";
    public static final String CHECKBOX_DISABLED_BACKGROUND = "checkboxDisabledBackground";
    public static final String CHECKBOX_DISABLED_FOREGROUND = "checkboxDisabledForeground";
    public static final String CHECKBOX_FOREGROUND = "checkboxForeground";
    public static final String CHECKBOX_SELECTED_BACKGROUND = "checkboxSelectedBackground";
    public static final String CHECKBOX_SELECTED_BORDER = "checkboxSelectedBorder";

    public static final String DOCAREA_HEADER_NOTABS_BACKGROUND = "documentAreaHeaderNoTabsBackground";
    public static final String DOCAREA_HEADER_TABS_BACKGROUND = "documentAreaHeaderTabsBackground";

    public static final String DISABLED_FOREGROUND = "disabledForeground";
    public static final String DROPDOWN_BACKGROUND = "dropdownBackground";
    public static final String DROPDOWN_FOREGROUND = "dropdownForeground";
    public static final String DROPDOWN_BORDER = "dropdownBorder";

    public static final String EDITOR_BACKGROUND = "editorBackground";
    public static final String EDITOR_CURSOR_BACKGROUND = "editorCursorBackground";
    public static final String EDITOR_CURSOR_FOREGROUND = "editorCursorForeground";
    public static final String EDITOR_FOREGROUND = "editorForeground";
    public static final String EDITOR_INDENTGUIDE_INACTIVE_BACKGROUND = "editorIndentGuideInactiveBackground";
    public static final String EDITOR_INDENTGUIDE_ACTIVE_BACKGROUND = "editorIndentGuideActiveBackground";
    public static final String EDITOR_MATCH_BACKGROUND = "editorMatchBackground";
    public static final String EDITOR_MATCH_FOREGROUND = "editorMatchForeground";
    public static final String EDITOR_MATCH_BORDER = "editorMatchBorder";
    public static final String EDITOR_MATCH_HIGHLIGHT_BACKGROUND = "editorMatchHighlightBackground";
    public static final String EDITOR_MATCH_HIGHLIGHT_FOREGROUND = "editorMatchHighlightForeground";
    public static final String EDITOR_WORD_HIGHLIGHT_BACKGROUND = "editorWordHighlightBackground";
    public static final String EDITOR_WORD_HIGHLIGHT_STRONG_BACKGROUND = "editorWordHighlightStrongBackground";
    public static final String EDITOR_SELECTION_ACTIVE_BACKGROUND = "editorSelectionActiveBackground";
    public static final String EDITOR_SELECTION_FOREGROUND = "editorSelectionForeground";
    public static final String EDITOR_SELECTION_INACTIVE_BACKGROUND = "editorSelectionInactiveBackground";
    public static final String EDITOR_SELECTION_HIGHLIGHT_BACKGROUND = "editorSelectionHighlightBackground";
    public static final String EDITOR_ERROR_FOREGROUND = "editorErrorForeground";
    public static final String EDITOR_WARN_FOREGROUND = "editorWarnForeground";
    public static final String EDITOR_INFO_FOREGROUND = "editorInfoForeground";

    public static final String GIT_UNTRACKED_FOREGROUND = "gitdecorationUntrackedForeground";
    public static final String GIT_MODIFIED_FOREGROUND = "gitdecorationModifiedForeground";

    public static final String INPUT_FOREGROUND = "inputForeground";
    public static final String INPUT_PLACEHOLDER_FOREGROUND = "inputPlaceholderForeground";
    public static final String INPUT_BACKGROUND = "inputBackground";
    public static final String INPUT_BORDER = "inputBorder";

    public static final String INPUT_VALIDATION_ERROR_BACKGROUND = "inputValidationErrorBackground";
    public static final String INPUT_VALIDATION_ERROR_FOREGROUND = "inputValidationErrorForeground";
    public static final String INPUT_VALIDATION_ERROR_BORDER = "inputValidationErrorBorder";
    public static final String INPUT_VALIDATION_INFO_BACKGROUND = "inputValidationInfoBackground";
    public static final String INPUT_VALIDATION_INFO_FOREGROUND = "inputValidationInfoForeground";
    public static final String INPUT_VALIDATION_INFO_BORDER = "inputValidationInfoBorder";
    public static final String INPUT_VALIDATION_WARNING_BACKGROUND = "inputValidationWarningBackground";
    public static final String INPUT_VALIDATION_WARNING_FOREGROUND = "inputValidationWarningForeground";
    public static final String INPUT_VALIDATION_WARNING_BORDER = "inputValidationWarningBorder";

    public static final String LINK_INACTIVE_FOREGROUND = "linkInactiveForeground";
    public static final String LINK_ACTIVE_FOREGROUND = "linkActiveForeground";

    public static final String LIST_FOCUS_BACKGROUND = "listFocusBackground";
    public static final String LIST_FOCUS_FOREGROUND = "listFocusForeground";
    public static final String LIST_SELECTION_ACTIVE_BACKGROUND = "listSelectionActiveBackground";
    public static final String LIST_SELECTION_ACTIVE_FOREGROUND = "listSelectionActiveForeground";
    public static final String LIST_FOCUS_OUTLINE = "listFocusOutline";
    public static final String LIST_SELECTION_FOCUS_OUTLINE = "listSelectionFocusOutline";
    public static final String LIST_SELECTION_INACTIVE_BACKGROUND = "listSelectionInactiveBackground";
    public static final String LIST_SELECTION_INACTIVE_FOREGROUND = "listSelectionInactiveForeground";
    public static final String LIST_HOVER_BACKGROUND = "listHoverBackground";
    public static final String LIST_WARNING_FOREGROUND = "listWarningForeground";
    public static final String LIST_SELECTION_ACTIVE_ICON_FOREGROUND = "listSelectionActiveIconForeground";
    public static final String LIST_DROP_BACKGROUND = "listDropBackground";

    public static final String MENU_BORDER = "menuBorder";
    public static final String MENU_FOREGROUND = "menuForeground";
    public static final String MENU_BACKGROUND = "menuBackground";
    public static final String MENU_BAR_SELECTION_BACKGROUND = "menuBarSelectionBackground";
    public static final String MENU_BAR_SELECTION_FOREGROUND = "menuBarSelectionForeground";
    public static final String MENU_SELECTION_FOREGROUND = "menuSelectionForeground";
    public static final String MENU_SELECTION_BACKGROUND = "menuSelectionBackground";
    public static final String MENU_SELECTION_BORDER = "menuSelectionBorder";
    public static final String MENU_SEPARATOR_BACKGROUND = "menuSeparatorBackground";

    public static final String NOTIFICATION_BACKGROUND = "notificationBackground";
    public static final String NOTIFICATION_FOREGROUND = "notificationForeground";
    public static final String NOTIFICATION_BORDER = "notificationBorder";
    public static final String NOTIFICATION_LINK = "notificationLink";
    public static final String NOTIFICATION_ICON_INFO = "notificationIconInfo";
    public static final String NOTIFICATION_ICON_ERROR = "notificationIconError";
    public static final String NOTIFICATION_ICON_WARNING = "notificationIconWarning";

    public static final String PANEL_BACKGROUND = "panelBackground";

    public static final String PORTS_ICON_RUNNING_PROCESS_FOREGROUND = "portsIconRunningProcessForeground";

    public static final String RADIO_ACTIVE_BACKGROUND = "radioActiveBackground";
    public static final String RADIO_ACTIVE_BORDER = "radioActiveBorder";
    public static final String RADIO_ACTIVE_FOREGROUND = "radioActiveForeground";
    public static final String RADIO_INACTIVE_BORDER = "radioInactiveBorder";
    public static final String RADIO_INACTIVE_HOVER_BACKGROUND = "radioInactiveHoverBackground";

    public static final String SCROLL_THUMB_BACKGROUND = "scrollThumbBackground";
    public static final String SCROLL_THUMB_HOVER_BACKGROUND = "scrollThumbHoverBackground";
    public static final String SCROLL_THUMB_ACTIVE_BACKGROUND = "scrollThumbActiveBackground";
    public static final String SCROLL_SHADOW = "scrollShadow";

    public static final String SIDEBAR_BACKGROUND = "sidebarBackground";
    public static final String SIDEBAR_FOREGROUND = "sidebarForeground";
    public static final String SIDEBAR_DROP_BACKGROUND = "sidebarDropBackground";
    public static final String SIDEBAR_BORDER = "sidebarBorder";
    public static final String SIDEBAR_TITLE_FOREGROUND = "sidebarTitleForeground";
    public static final String SIDEBAR_TITLE_BACKGROUND = "sidebarTitleBackground";
    public static final String SIDEBAR_HEADER_BACKGROUND = "sidebarHeaderBackground";
    public static final String SIDEBAR_HEADER_FOREGROUND = "sidebarHeaderForeground";
    public static final String SIDEBAR_HEADER_BORDER = "sidebarHeaderBorder";

    public static final String STATUSBAR_BACKGROUND = "statusbarBackground";
    public static final String STATUSBAR_FOREGROUND = "statusbarForeground";
    public static final String STATUSBAR_NOFOLDER_BACKGROUND = "statusbarNofolderBackground";
    public static final String STATUSBAR_DEBUGGING_BACKGROUND = "statusbarDebuggingBackground";
    public static final String STATUSBAR_DEBUGGING_FOREGROUND = "statusbarDebuggingForeground";
    public static final String STATUSBAR_REMOTE_BACKGROUND = "statusbarItemRemoteBackground";
    public static final String STATUSBAR_REMOTE_FOREGROUND = "statusbarItemRemoteForeground";
    public static final String STATUSBAR_REMOTE_HOVER_BACKGROUND = "statusbarItemRemoteHoverBackground";
    public static final String STATUSBAR_REMOTE_HOVER_FOREGROUND = "statusbarItemRemoteHoverForeground";
    public static final String STATUSBAR_HOVER_BACKGROUND = "statusbarItemHoverBackground";
    public static final String STATUSBAR_HOVER_FOREGROUND = "statusbarItemHoverForeground";

    public static final String TAB_ACTIVE_BACKGROUND = "tabActiveBackground";
    public static final String TAB_ACTIVE_BORDER = "tabActiveBorder";
    public static final String TAB_ACTIVE_FOREGROUND = "tabActiveForeground";
    public static final String TAB_INACTIVE_BACKGROUND = "tabInactiveBackground";
    public static final String TAB_INACTIVE_FOREGROUND = "tabInactiveForeground";
    public static final String TAB_SELECTED_BORDER_TOP = "tabSelectedBorderTop";
    public static final String TAB_SELECTED_BACKGROUND = "tabSelectedBackground";
    public static final String TAB_SELECTED_FOREGROUND = "tabSelectedForeground";
    public static final String TAB_LAST_PINNED_BORDER = "tabLastPinnedBorder";

    public static final String TERMINAL_SELECTION_INACTIVE_BACKGROUND = "terminalSelectionInactiveBackground";

    public static final String TITLEBAR_ACTIVE_FOREGROUND = "titlebarActiveForeground";
    public static final String TITLEBAR_ACTIVE_BACKGROUND = "titlebarActiveBackground";
    public static final String TITLEBAR_INACTIVE_BACKGROUND = "titlebarInactiveBackground";

    public static final String TOOLTIP_BORDER = "tooltipBorder";

    public static final String WELCOMEPAGE_BACKGROUND = "welcomepageBackground";

    public static final String WIDGET_BORDER = "widgetBorder";
    public static final String WIDGET_SHADOW = "widgetShadow";


    public static final List<String> UI_COLORS = List.of(
        FOREGROUND,
        FOCUS_BORDER,
        SELECTION_BACKGROUND,
        ICON_FOREGROUND,

        ACTIVITYBAR_INACTIVE_FOREGROUND,
        ACTIVITYBAR_INACTIVE_BACKGROUND,
        ACTIVITYBAR_ACTIVE_BACKGROUND,
        ACTIVITYBAR_ACTIVE_FOREGROUND,
        ACTIVITYBAR_BADGE_BACKGROUND,
        ACTIVITYBAR_BADGE_FOREGROUND,
        ACTIVITYBAR_TOGGLED_BACKGROUND,
        ACTIVITYBAR_ACTIVE_BORDER,
        ACTIVITYBAR_DROP_BORDER,
        ACTIVITYBARTOP_ACTIVE_BORDER,
        ACTIVITYBARTOP_DROP_BORDER,
        ACTIVITYBARTOP_ACTIVE_FOREGROUND,
        ACTIVITYBARTOP_INACTIVE_FOREGROUND,

        // ACTIVITYBAR_ERRORBADGE_BACKGROUND,
        // ACTIVITYBAR_ERRORBADGE_FOREGROUND,
        // ACTIVITYBAR_WARNINGBADGE_BACKGROUND,
        // ACTIVITYBAR_WARNINGBADGE_FOREGROUND,

        BADGE_INFO_BACKGROUND,
        BADGE_INFO_FOREGROUND,
        BADGE_ERROR_BACKGROUND,
        BADGE_ERROR_FOREGROUND,
        BADGE_WARN_BACKGROUND,
        BADGE_WARN_FOREGROUND,

        BUTTON_BACKGROUND,
        BUTTON_FOREGROUND,
        BUTTON_HOVER_BACKGROUND,

        CHECKBOX_BORDER,
        CHECKBOX_BACKGROUND,
        CHECKBOX_DISABLED_BACKGROUND,
        CHECKBOX_DISABLED_FOREGROUND,
        CHECKBOX_FOREGROUND,
        CHECKBOX_SELECTED_BACKGROUND,
        CHECKBOX_SELECTED_BORDER,

        DOCAREA_HEADER_NOTABS_BACKGROUND,
        DOCAREA_HEADER_TABS_BACKGROUND,

        DISABLED_FOREGROUND,

        DROPDOWN_BACKGROUND,
        DROPDOWN_FOREGROUND,
        DROPDOWN_BORDER,

        EDITOR_BACKGROUND,
        EDITOR_CURSOR_BACKGROUND,
        EDITOR_CURSOR_FOREGROUND,
        EDITOR_FOREGROUND,
        EDITOR_INDENTGUIDE_ACTIVE_BACKGROUND,
        EDITOR_INDENTGUIDE_INACTIVE_BACKGROUND,
        EDITOR_SELECTION_ACTIVE_BACKGROUND,
        EDITOR_SELECTION_FOREGROUND,
        EDITOR_SELECTION_INACTIVE_BACKGROUND,
        EDITOR_MATCH_BACKGROUND,
        EDITOR_MATCH_FOREGROUND,
        EDITOR_MATCH_BORDER,
        EDITOR_MATCH_HIGHLIGHT_BACKGROUND,
        EDITOR_MATCH_HIGHLIGHT_FOREGROUND,
        EDITOR_ERROR_FOREGROUND,
        EDITOR_WARN_FOREGROUND,
        EDITOR_INFO_FOREGROUND,

        GIT_UNTRACKED_FOREGROUND,
        GIT_MODIFIED_FOREGROUND,

        INPUT_FOREGROUND,
        INPUT_PLACEHOLDER_FOREGROUND,
        INPUT_BACKGROUND,
        INPUT_BORDER,

        LINK_INACTIVE_FOREGROUND,
        LINK_ACTIVE_FOREGROUND,

        LIST_FOCUS_BACKGROUND,
        LIST_FOCUS_FOREGROUND,
        LIST_SELECTION_ACTIVE_BACKGROUND,
        LIST_SELECTION_ACTIVE_FOREGROUND,
        LIST_FOCUS_OUTLINE,
        LIST_SELECTION_FOCUS_OUTLINE,
        LIST_SELECTION_INACTIVE_BACKGROUND,
        LIST_SELECTION_INACTIVE_FOREGROUND,
        LIST_HOVER_BACKGROUND,
        LIST_WARNING_FOREGROUND,
        LIST_SELECTION_ACTIVE_ICON_FOREGROUND,
        LIST_DROP_BACKGROUND,

        MENU_BAR_SELECTION_BACKGROUND,
        MENU_BAR_SELECTION_FOREGROUND,
        MENU_BORDER,
        MENU_FOREGROUND,
        MENU_BACKGROUND,
        MENU_SELECTION_FOREGROUND,
        MENU_SELECTION_BACKGROUND,
        MENU_SELECTION_BORDER,
        MENU_SEPARATOR_BACKGROUND,

        NOTIFICATION_BACKGROUND,
        NOTIFICATION_FOREGROUND,
        NOTIFICATION_BORDER,
        NOTIFICATION_LINK,
        NOTIFICATION_ICON_INFO,
        NOTIFICATION_ICON_ERROR,
        NOTIFICATION_ICON_WARNING,

        PANEL_BACKGROUND,

        PORTS_ICON_RUNNING_PROCESS_FOREGROUND,

        RADIO_ACTIVE_BACKGROUND,
        RADIO_ACTIVE_BORDER,
        RADIO_ACTIVE_FOREGROUND,
        RADIO_INACTIVE_BORDER,
        RADIO_INACTIVE_HOVER_BACKGROUND,

        SCROLL_THUMB_BACKGROUND,
        SCROLL_THUMB_HOVER_BACKGROUND,
        SCROLL_THUMB_ACTIVE_BACKGROUND,
        SCROLL_SHADOW,

        SIDEBAR_BACKGROUND,
        SIDEBAR_FOREGROUND,
        SIDEBAR_DROP_BACKGROUND,
        SIDEBAR_BORDER,
        SIDEBAR_TITLE_FOREGROUND,
        SIDEBAR_TITLE_BACKGROUND,
        SIDEBAR_HEADER_BACKGROUND,
        SIDEBAR_HEADER_FOREGROUND,
        SIDEBAR_HEADER_BORDER,

        STATUSBAR_BACKGROUND,
        STATUSBAR_FOREGROUND,
        STATUSBAR_NOFOLDER_BACKGROUND,
        STATUSBAR_DEBUGGING_BACKGROUND,
        STATUSBAR_DEBUGGING_FOREGROUND,
        STATUSBAR_REMOTE_BACKGROUND,
        STATUSBAR_REMOTE_FOREGROUND,
        STATUSBAR_REMOTE_HOVER_BACKGROUND,
        STATUSBAR_REMOTE_HOVER_FOREGROUND,
        STATUSBAR_HOVER_BACKGROUND,
        STATUSBAR_HOVER_FOREGROUND,

        TAB_ACTIVE_BACKGROUND,
        TAB_ACTIVE_BORDER,
        TAB_ACTIVE_FOREGROUND,
        TAB_INACTIVE_BACKGROUND,
        TAB_INACTIVE_FOREGROUND,
        TAB_SELECTED_BACKGROUND,
        TAB_SELECTED_BORDER_TOP,
        TAB_SELECTED_FOREGROUND,
        TAB_LAST_PINNED_BORDER,

        TERMINAL_SELECTION_INACTIVE_BACKGROUND,

        TITLEBAR_ACTIVE_FOREGROUND,
        TITLEBAR_ACTIVE_BACKGROUND,
        TITLEBAR_INACTIVE_BACKGROUND,

        TOOLTIP_BORDER,

        WELCOMEPAGE_BACKGROUND,

        WIDGET_BORDER,
        WIDGET_SHADOW

    );

    public static final List<String> PALETTE_COLORS = List.of(
        ACCENT_BACKGROUND,
        ACCENT_FOREGROUND,
        ACCENT_HOVER_BACKGROUND,
        CONTROL_BACKGROUND,
        CONTROL_FOREGROUND,
        DOCUMENT_BACKGROUND,
        EMPTY_BACKGROUND,
        TEXT_FOREGROUND,
        MARK_FOREGROUND
    );

    private static Properties mappedColors = null;
    public static Properties getDefaultPaletteMapping() {
        if (mappedColors == null) {
            mappedColors = new Properties();
            mappedColors.set(ACCENT_BACKGROUND, BUTTON_BACKGROUND);
            mappedColors.set(ACCENT_FOREGROUND, BUTTON_FOREGROUND);
            mappedColors.set(ACCENT_HOVER_BACKGROUND, BUTTON_HOVER_BACKGROUND);
            mappedColors.set(EMPTY_BACKGROUND, SIDEBAR_BACKGROUND);
            mappedColors.set(DOCUMENT_BACKGROUND, EDITOR_BACKGROUND);
            mappedColors.set(CONTROL_FOREGROUND, ACTIVITYBAR_INACTIVE_FOREGROUND); // This could also be NUTTON_FOREGROUND or ICON_FOREGROUND
            mappedColors.set(CONTROL_BACKGROUND, ACTIVITYBAR_INACTIVE_BACKGROUND);
            mappedColors.set(TEXT_FOREGROUND, EDITOR_FOREGROUND);
            mappedColors.set(MARK_FOREGROUND, EDITOR_FOREGROUND);
        }
        return mappedColors;
    }

    private ColorID() {
        // Nothing to see here
    }
}
