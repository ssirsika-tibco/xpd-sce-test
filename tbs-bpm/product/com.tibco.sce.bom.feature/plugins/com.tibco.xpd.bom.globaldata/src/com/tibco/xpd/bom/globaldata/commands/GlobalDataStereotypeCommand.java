/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.globaldata.commands;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.GlobalDataActivator;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Commands to add/remove Stereotypes to/from a UML Class element. Includes some
 * checks on target class whilst adding/removing Stereotypes.
 * 
 * @author patkinso
 * @since 28 Feb 2013
 */
public class GlobalDataStereotypeCommand {

    private static abstract class BaseGlobalDataStereotypeCommand extends
            RecordingCommand {

        protected StereotypeKind kind;

        protected Class clazz;

        protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
                .getInstance();

        protected Logger logger = GlobalDataActivator.getDefault().getLogger();

        /**
         * @param domain
         * @param label
         * @param description
         */
        public BaseGlobalDataStereotypeCommand(
                TransactionalEditingDomain domain, Class clazz,
                StereotypeKind kind) {

            super(domain);
            this.kind = kind;
            this.clazz = clazz;
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#prepare()
         * 
         * @return
         */
        @Override
        protected boolean prepare() {
            if (clazz != null) {
                // return BOMGlobalDataUtils.isGlobalDataBOM(clazz.getModel());
                return BOMGlobalDataUtils
                        .hasGlobalDataProfile(clazz.getModel());
            }
            return false;
        }
    }

    /**
     * Apply a stereotype to a Class. It should ensure any stereotypes already
     * applied, which are incompatible with the new one, are also removed.
     */
    public static class Set extends BaseGlobalDataStereotypeCommand {

        /**
         * @param domain
         * @param clazz
         * @param kind
         */
        public Set(TransactionalEditingDomain domain, Class clazz,
                StereotypeKind kind) {
            super(domain, clazz, kind);
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {
            if (!gdManager.add(clazz, kind)) {
                String fmtMsg =
                        "Could not add Kind '%s' from the Class element '%s'."; //$NON-NLS-1$
                logger.warn(String.format(fmtMsg,
                        kind.toString(),
                        clazz.getLabel()));
            }
        }

    }

    /**
     * Remove a stereotype from a Class.
     */
    public static class Unset extends BaseGlobalDataStereotypeCommand {

        /**
         * @param domain
         * @param clazz
         * @param kind
         */
        public Unset(TransactionalEditingDomain domain, Class clazz,
                StereotypeKind kind) {
            super(domain, clazz, kind);
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {
            if (!gdManager.remove(clazz, kind)) {
                String fmtMsg =
                        "Could not remove Kind '%s' from the Class element '%s'. Kind was not recognised."; //$NON-NLS-1$
                logger.error(String.format(fmtMsg,
                        kind.toString(),
                        clazz.getLabel()));
            } else {
                // If we have unset the Global Data Stereotype that made it
                // either a Case Class or Global Class, then we should also
                // remove the searchable flag from any of the attributes that
                // have it
                final Stereotype stereotype =
                        gdManager.getStereotype(StereotypeKind.SEARCHABLE);
                // Make sure the Stereotype is found
                if (stereotype != null) {
                    for (Property prop : clazz.getAttributes()) {
                        // Check if the searchable stereotype is already applied
                        if (prop.getAppliedStereotypes().contains(stereotype)) {
                            // Remove the searchable stereotype if not valid
                            prop.unapplyStereotype(stereotype);
                        }
                    }
                }
            }
        }
    }
}
