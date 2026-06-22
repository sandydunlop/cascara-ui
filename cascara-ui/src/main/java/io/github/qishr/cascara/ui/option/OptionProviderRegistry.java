package io.github.qishr.cascara.ui.option;

import java.util.HashMap;
import java.util.Map;

public class OptionProviderRegistry {
    private Map<String,OptionProvider> providersByName = new HashMap<>();

    private static OptionProviderRegistry instance;

    private OptionProviderRegistry() {
        // This constructor intentionally left blank
    }

    public static OptionProviderRegistry instance() {
        if (instance == null) {
            instance = new OptionProviderRegistry();
        }
        return instance;
    }

    public static void register(OptionProvider provider) {
        instance().providersByName.put(provider.getName(), provider);
    }

    public static OptionProvider get(String name) {
        return instance().providersByName.get(name);
    }
}
