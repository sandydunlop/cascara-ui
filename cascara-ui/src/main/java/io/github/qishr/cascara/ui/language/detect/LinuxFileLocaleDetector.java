package io.github.qishr.cascara.ui.language.detect;

import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

public class LinuxFileLocaleDetector {
    public static Locale detect() {
        File localeFile = new File("/etc/default/locale");
        if (!localeFile.exists()) return null;

        try (Scanner scanner = new Scanner(new FileReader(localeFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("LANG=")) {
                    String lang = line.split("=")[1].replace("\"", ""); // e.g., "en_US.UTF-8"
                    String[] parts = lang.split("[._]");
                    return new Locale(parts[0], parts.length > 1 ? parts[1] : "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}