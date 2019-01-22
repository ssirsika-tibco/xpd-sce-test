/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcherExtensionPointHelper.TypeMatcherPriority;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Object representing the mapping type matcher extension point.
 * 
 * @author rsomayaj
 * @since 3.3 (11 Jun 2010)
 */
public class MappingTypeMatcher {

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    private static final String SHOW_IN_PREFERENCE_PAGE =
            "showInPreferencePage"; //$NON-NLS-1$

    private static final String DESTINATION_ID = "destinationId"; //$NON-NLS-1$

    private static final String TYPE_MATCHER_CLASS = "typeMatcherClass"; //$NON-NLS-1$

    private static final String INCLUDE_DEFAULT = "includeDefault"; //$NON-NLS-1$

    private static final String DOES_TYPE_MATCHING = "doesTypeMatching"; //$NON-NLS-1$

    private static final String GRAMMAR_ID = "grammarId"; //$NON-NLS-1$

    private static final String PRIORIY = "priority"; //$NON-NLS-1$

    private final IConfigurationElement configElement;

    /**
         * 
         */
    public MappingTypeMatcher(IConfigurationElement configElement) {
        this.configElement = configElement;

    }

    /**
         * 
         */
    private String destinationId;

    /**
         * 
         */
    private String grammarId;

    /**
         * 
         */
    private AbstractTypeMatcher typeMatcher;

    /**
         * 
         */
    private Boolean doesTypeMatching;

    /**
         * 
         */
    private Boolean includeDefault;

    /**
     * 
     */
    private Boolean showInPreferencePage;

    /**
         * 
         */
    private TypeMatcherPriority automapPriority;

    /**
     * Name to be displayed on the preference page.
     */
    private String name;

    /**
     * Id that is used to identify the preference in the preference store.
     */
    private String id;

    /**
     * @return the name
     */
    public String getName() {
        if (name == null) {
            if (configElement != null) {
                Object parent = configElement.getParent();
                if (parent instanceof IExtension) {
                    IExtension parentExtension = (IExtension) parent;
                    name = parentExtension.getLabel();
                }
            }
        }
        return name;
    }

    public String getId() {
        if (id == null) {
            if (configElement != null) {
                Object parent = configElement.getParent();
                if (parent instanceof IExtension) {
                    IExtension parentExtension = (IExtension) parent;
                    id = parentExtension.getUniqueIdentifier();
                }
            }
        }
        return id;
    }

    /**
     * @return the destinationId
     */
    public String getDestinationId() {
        if (destinationId == null) {
            if (configElement != null) {
                destinationId = configElement.getAttribute(DESTINATION_ID);
            }
        }
        return destinationId;
    }

    /**
     * @return the destinationId
     */
    public Boolean getShowInPreferencePage() {
        if (showInPreferencePage == null) {
            if (configElement != null) {
                showInPreferencePage =
                        new Boolean(configElement
                                .getAttribute(SHOW_IN_PREFERENCE_PAGE));
            }
        }
        return showInPreferencePage;
    }

    /**
     * @return the automapPriority
     */
    public TypeMatcherPriority getTypeMatcherPriority() {
        if (automapPriority == null) {
            if (configElement != null) {
                this.automapPriority =
                        TypeMatcherPriority.getPriority(configElement
                                .getAttribute(PRIORIY));
            }
        }
        return automapPriority;
    }

    /**
     * @return the doesTypeMatching
     */
    public Boolean getDoesTypeMatching() {
        if (doesTypeMatching == null) {
            if (configElement != null) {
                this.doesTypeMatching =
                        new Boolean(configElement
                                .getAttribute(DOES_TYPE_MATCHING));
            }
        }
        return doesTypeMatching;
    }

    /**
     * @return the grammarId
     */
    public String getGrammarId() {
        if (grammarId == null) {
            if (configElement != null) {
                this.grammarId = configElement.getAttribute(GRAMMAR_ID);
            }
        }
        return grammarId;
    }

    /**
     * @return the typeMatcher
     */
    public AbstractTypeMatcher getTypeMatcher() {
        if (typeMatcher == null) {
            if (configElement != null) {
                try {
                    Object executableExtension =
                            configElement
                                    .createExecutableExtension(TYPE_MATCHER_CLASS);

                    if (executableExtension instanceof AbstractTypeMatcher) {
                        typeMatcher = (AbstractTypeMatcher) executableExtension;
                    }
                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }
        return typeMatcher;
    }

    /**
     * @return the includeDefault
     */
    public Boolean getIncludeDefault() {
        if (includeDefault == null) {
            if (configElement != null) {
                this.includeDefault =
                        new Boolean(configElement.getAttribute(INCLUDE_DEFAULT));
            }
        }
        return includeDefault;
    }

}