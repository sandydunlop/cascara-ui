package io.github.qishr.cascara.ui.theme;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.ui.color.ColorException;

public class FallbackColorsTests extends ThemeTestsBase {
    static Properties colors;

    static String hexValue01 = "#EEEEEE";
    static String hexValue02 = "#EEEEEE";

    @BeforeAll
    static void init() {
        colors = new Properties();
        colors.set(ColorID.ACTIVITYBAR_ACTIVE_BACKGROUND, hexValue01);
        colors.set(ColorID.ACTIVITYBAR_ACTIVE_FOREGROUND, hexValue02);
    }

    @Test
    void test_fallback() {

    }

    @ParameterizedTest
    @MethodSource("stringArraysProvider")
    void test_fallbackSucceeds(String input, String expected) throws IOException, ColorException {
        FallbackColors fbc = new FallbackColors();
        String color = fbc.resolve(input, colors);
        assertEquals(expected, color);
    }

    static String[][] stringArraysProvider() {
        return new String[][]{
            {ColorID.ACTIVITYBAR_INACTIVE_BACKGROUND, hexValue01},
            {ColorID.ACTIVITYBAR_INACTIVE_FOREGROUND, "#777777"}
        };
    }
}
