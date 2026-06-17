package io.github.qishr.cascara.ui.service;

import io.github.qishr.cascara.common.service.CapabilityQueries;
import io.github.qishr.cascara.common.service.ServiceException;
import io.github.qishr.cascara.common.service.ServiceProviderFactory;
import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.option.OptionProvider;

public class UiServiceProviderFactory extends ServiceProviderFactory {
    public UiServiceProviderFactory() {
        super();
    }

    public UiServiceProviderFactory(ServiceProviderLayer layer) {
        super(layer);
    }

    public OptionProvider createOptionProvider(String name) throws ServiceException {
        return createServiceProvider(
            OptionProvider.class,
            CapabilityQueries.hasExactValue(ServicePropertyName.NAME.asString(), name)
        );
    }
}