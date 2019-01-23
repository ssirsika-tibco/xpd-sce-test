package com.tibco.xpd.registry.ui.wizard;

import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;

/**
 * This wizard is used whenever there is a WSDL import triggered under
 * circumstances where the source is already known, and there is no need to ask
 * the user to navigate to the source. This happens, for example, during a drag
 * and drop operation from the Registries View.
 */
public class WsdlImportFromExistingRegistryWizard extends WsdlImportBaseWizard {
    public WsdlImportFromExistingRegistryWizard(
            RegistryServiceSelector registryServiceSelector) {
        super(null, Messages.WsdlRegistryImportWizard_Selection_title);
        updateTarget(getWsdlImportMappings(registryServiceSelector));
    }
}
