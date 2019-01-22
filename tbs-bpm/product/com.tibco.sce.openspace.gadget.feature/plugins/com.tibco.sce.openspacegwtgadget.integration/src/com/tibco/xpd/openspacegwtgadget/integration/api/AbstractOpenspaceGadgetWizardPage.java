/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.api;

import java.util.Map;

import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Class that supports addition of pages to the Openspace gadget sample creation
 * wizard via extension point
 * com.tibco.xpd.openspacegwtgadget.integration.OpenspaceSampleCreator
 * <p>
 * This class is given a sampleProperties map that is a simple name-value pair
 * map whose keys can be used in building target packages and files and in any
 * JET templates used to create the gadget source.
 * <p>
 * The wizard is expected to add any name-value pairs to the map that the sample
 * requires (both default values and then resetting these from wizard page
 * control selections).
 * 
 * @author aallway
 * @since 21 Feb 2013
 */
public abstract class AbstractOpenspaceGadgetWizardPage extends AbstractXpdWizardPage {

    public AbstractOpenspaceGadgetWizardPage() {
        super("AbstractOpenspaceGadgetWizardPage"); //$NON-NLS-1$
    }

    /**
     * This method is called directly after construction. The wizard page should
     * use this sampleProperties to add additional key->value pairs.
     * <p>
     * These values can be referenced in the SampleFile extension point
     * contributions and the JET template emitters for (SourceJetEmitter
     * contributions).
     * 
     * @param sampleProperties
     */
    public abstract void setVariableProperties(
            Map<String, String> sampleProperties);
}
