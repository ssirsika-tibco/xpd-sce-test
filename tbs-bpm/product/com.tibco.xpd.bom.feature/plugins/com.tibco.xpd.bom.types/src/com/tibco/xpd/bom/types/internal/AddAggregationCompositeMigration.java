/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.types.internal;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.migration.IBOMMigration;

/**
 * Migration (for migrate to FormatVersion 2) to add missing
 * agregation="composite" attributes from any ownedAttribute that does not have
 * any set.
 * 
 * @author aallway
 * @since 12 Jul 2013
 */
public class AddAggregationCompositeMigration implements IBOMMigration {

    public AddAggregationCompositeMigration() {
    }

    /**
     * @see com.tibco.xpd.bom.resources.migration.IBOMMigration#getMigrationCommand(org.eclipse.emf.transaction.TransactionalEditingDomain,
     *      org.eclipse.uml2.uml.Model)
     * 
     * @param domain
     * @param model
     * @return
     */
    @Override
    public Command getMigrationCommand(TransactionalEditingDomain domain,
            Model model) {
        return new AddMissingAggregationCompositeCommand(domain, model);
    }

    /**
     * Command to recursively search all class attributes adding
     * aggregation="composite" if the aggregation is not currentyl set.
     * 
     * @author aallway
     * @since 12 Jul 2013
     */
    private static class AddMissingAggregationCompositeCommand extends
            RecordingCommand {

        private Model model;

        /**
         * @param domain
         * @param model
         * 
         */
        public AddMissingAggregationCompositeCommand(
                TransactionalEditingDomain domain, Model model) {
            super(domain);
            this.model = model;
        }

        /**
         * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
         * 
         */
        @Override
        protected void doExecute() {
            recursiveProcessPackageElements(model);
        }

        /**
         * Recursively process the classes within the given package.
         * 
         * @param pkg
         */
        private void recursiveProcessPackageElements(Package pkg) {

            for (PackageableElement packageableElement : pkg
                    .getPackagedElements()) {
                if (packageableElement instanceof Class) {
                    processClass((Class) packageableElement);

                } else if (packageableElement instanceof Package) {
                    recursiveProcessPackageElements((Package) packageableElement);
                }

            }
        }

        /**
         * Check all owned attributes for missing aggregation attribute.
         * 
         * @param clazz
         */
        private void processClass(Class clazz) {
            EList<Property> ownedAttributes = clazz.getOwnedAttributes();

            for (Property property : ownedAttributes) {
                if (AggregationKind.NONE_LITERAL.equals(property
                        .getAggregation())) {
                    /*
                     * Do not change real associations, only owned attributes
                     * that have no association of any kind (an attribute
                     * defined by an association type link should_not_ have an
                     * aggregation attribute.
                     */
                    if (property.getAssociation() == null) {
                        property.setAggregation(AggregationKind.COMPOSITE_LITERAL);
                    }
                }
            }
        }

    }
}
