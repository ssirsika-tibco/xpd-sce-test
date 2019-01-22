/*
 * Copyright (c) TIBCO Software Inc 20014. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Commands to add/remove Stereotypes to/from a UML Property element.
 * 
 */
public class GlobalDataPropertyStereotypeCommand {

    private static abstract class BaseGlobalDataPropertyStereotypeCommand
            extends RecordingCommand {

        protected StereotypeKind kind;

        protected Property prop;

        protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
                .getInstance();

        protected Logger logger = GlobalDataActivator.getDefault().getLogger();

        public BaseGlobalDataPropertyStereotypeCommand(
                TransactionalEditingDomain domain, Property property,
                StereotypeKind kind) {

            super(domain);
            this.kind = kind;
            this.prop = property;
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#prepare()
         * 
         * @return
         */
        @Override
        protected boolean prepare() {
            if (prop != null) {
                return BOMGlobalDataUtils.hasGlobalDataProfile(prop.getModel());
            }
            return false;
        }
    }

    /**
     * Apply a stereotype to a Property. It should ensure any stereotypes
     * already applied, which are incompatible with the new one, are also
     * removed.
     */
    public static class Set extends BaseGlobalDataPropertyStereotypeCommand {

        public Set(TransactionalEditingDomain domain, Property property,
                StereotypeKind kind) {
            super(domain, property, kind);
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {
            // Check for mutually exclusive states
            if ((kind == StereotypeKind.CASE_STATE)
                    || (kind == StereotypeKind.CID)
                    || (kind == StereotypeKind.AUTO_CASE_IDENTIFIER)
                    || (kind == StereotypeKind.COMPOSITE_CASE_IDENTIFIER)) {
                // Make sure there is only one stereo type for the property
                if (kind != StereotypeKind.CASE_STATE) {
                    if (gdManager.checkForStereotype(prop,
                            StereotypeKind.CASE_STATE)) {
                        gdManager.remove(prop, StereotypeKind.CASE_STATE);
                    }
                }
                if (kind != StereotypeKind.CID) {
                    if (gdManager.checkForStereotype(prop, StereotypeKind.CID)) {
                        gdManager.remove(prop, StereotypeKind.CID);
                    }
                }
                if (kind != StereotypeKind.AUTO_CASE_IDENTIFIER) {
                    if (gdManager.checkForStereotype(prop,
                            StereotypeKind.AUTO_CASE_IDENTIFIER)) {
                        gdManager.remove(prop,
                                StereotypeKind.AUTO_CASE_IDENTIFIER);
                    }
                }
                if (kind != StereotypeKind.COMPOSITE_CASE_IDENTIFIER) {
                    if (gdManager.checkForStereotype(prop,
                            StereotypeKind.COMPOSITE_CASE_IDENTIFIER)) {
                        gdManager.remove(prop,
                                StereotypeKind.COMPOSITE_CASE_IDENTIFIER);
                    }
                }
            }
            if (!gdManager.add(prop, kind)) {
                String fmtMsg =
                        "Could not add Kind '%s' to the Property element '%s'."; //$NON-NLS-1$
                logger.warn(String.format(fmtMsg,
                        kind.toString(),
                        prop.getLabel()));
            }
            // Now set the readonly flag, as auto case identifier will be
            // marked as readonly
            if (kind == StereotypeKind.AUTO_CASE_IDENTIFIER) {
                prop.setIsReadOnly(true);
            }
        }
    }

    /**
     * Remove a stereotype from a Class.
     */
    public static class Unset extends BaseGlobalDataPropertyStereotypeCommand {

        public Unset(TransactionalEditingDomain domain, Property property,
                StereotypeKind kind) {
            super(domain, property, kind);
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {
            if (!gdManager.remove(prop, kind)) {
                String fmtMsg =
                        "Could not remove Kind '%s' from the Property element '%s'. Kind was not recognised."; //$NON-NLS-1$
                logger.error(String.format(fmtMsg,
                        kind.toString(),
                        prop.getLabel()));
            }
            // Now unset the readonly flag, as auto case identifier will be
            // marked as readonly
            if (kind == StereotypeKind.AUTO_CASE_IDENTIFIER) {
                prop.setIsReadOnly(false);
            }
        }
    }
}
