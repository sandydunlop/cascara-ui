package io.github.qishr.cascara.ui.menu;

import java.util.ServiceLoader;

// import io.github.qishr.cascara.module.core.component.StandardSystemMenus;

public class SystemMenusServiceFactory {
    private SystemMenusServiceFactory() {
        // This constructor intentionally left blank
    }

    public static SystemMenusService getSystemMenusService() {
        ServiceLoader<SystemMenusService> loader =
            ServiceLoader.load(SystemMenusService.class);

        if (loader.findFirst().isPresent()) {
            return loader.findFirst().get();
        } else {
            return new StandardSystemMenus();
        }
    }
}
