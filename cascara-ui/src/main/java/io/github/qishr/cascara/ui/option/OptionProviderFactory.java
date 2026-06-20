package io.github.qishr.cascara.ui.option;

import io.github.qishr.cascara.common.service.CapabilityQueries;
import io.github.qishr.cascara.common.service.ServiceException;
import io.github.qishr.cascara.common.service.AbstractServiceProviderFactory;
import io.github.qishr.cascara.common.service.ServiceProviderLayer;
import io.github.qishr.cascara.ui.api.ServicePropertyName;

public class OptionProviderFactory extends AbstractServiceProviderFactory {
    public OptionProviderFactory() {
        super();
    }

    public OptionProviderFactory(ServiceProviderLayer layer) {
        super(layer);
    }

    public OptionProvider createOptionProvider(String name) throws ServiceException {
        return createServiceProvider(
            OptionProvider.class,
            CapabilityQueries.hasExactValue(ServicePropertyName.NAME.asString(), name)
        );
    }
}