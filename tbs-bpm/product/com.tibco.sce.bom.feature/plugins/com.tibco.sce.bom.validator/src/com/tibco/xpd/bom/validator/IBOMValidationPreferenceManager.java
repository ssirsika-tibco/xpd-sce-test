/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.validator;

import org.eclipse.core.resources.IProject;

/**
 * BOM validation preference manager that handles the getting and setting of
 * validation options in the BOM. This is used, for example, to service the
 * Validation preference page and properties page (on a project).
 * 
 * @author njpatel
 * 
 */
public interface IBOMValidationPreferenceManager {

    /**
     * Validation destinations that can be enabled/disabled through the
     * preferences (workspace and project level).
     * 
     * @author njpatel
     * 
     */
    public enum ValidationDestination {
        XSD("XSD", "bom.xsd.validation.preference", //$NON-NLS-1$ //$NON-NLS-2$
                BOMValidatorActivator.VALIDATION_DEST_ID_XSD),

        WSDL("WSDL", "bom.wsdl.validation.preference", //$NON-NLS-1$ //$NON-NLS-2$
                BOMValidatorActivator.VALIDATION_DEST_ID_WSDL),

        WSDL_TO_BOM("WSDL", "bom.wsdl.validation.preference", //$NON-NLS-1$ //$NON-NLS-2$
                BOMValidatorActivator.VALIDATION_DEST_ID_WSDL_TO_BOM);

        private final String preferenceId;

        private final String label;

        private final String destinationId;

        ValidationDestination(String label, String preferenceId,
                String destinationId) {
            this.label = label;
            this.preferenceId = preferenceId;
            this.destinationId = destinationId;

        }

        /**
         * Get the destination enumeration with the given destination id.
         * 
         * @param destinationId
         * @return {@link ValidationDestination} or <code>null</code> if not
         *         found.
         */
        public static ValidationDestination getDestination(String destinationId) {
            if (destinationId != null) {
                for (ValidationDestination destination : values()) {
                    if (destinationId.equals(destination.getDestinationId())) {
                        return destination;
                    }
                }
            }
            return null;
        }

        /**
         * Get the id to use to store the value in the preferene store.
         * 
         * @return id
         */
        public String getPreferenceId() {
            return preferenceId;
        }

        /**
         * Get the validation destination id of this option.
         * 
         * @return
         */
        public String getDestinationId() {
            return destinationId;
        }

        /**
         * Get the human-readable name of this destination option.
         * 
         * @return
         */
        public String getLabel() {
            return label;
        }
    }

    boolean getPreferenceSetting(ValidationDestination destination);

    void setPreferenceSetting(ValidationDestination destination, boolean value);

    boolean getPropertiesSetting(IProject project,
            ValidationDestination destination);

    void setPropertiesSetting(IProject project,
            ValidationDestination destination, boolean value);

    boolean isProjectSettingsSet(IProject project);

    void removeProperties(IProject project);

    boolean isValidationEnabled(IProject project,
            ValidationDestination destination);

}
