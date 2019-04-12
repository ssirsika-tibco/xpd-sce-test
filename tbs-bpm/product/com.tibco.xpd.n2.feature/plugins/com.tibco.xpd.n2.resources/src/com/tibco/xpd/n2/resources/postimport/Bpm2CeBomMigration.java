/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.resources.postimport;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.migration.IBOMMigration;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Performs migration changes to BOMs imported from AMX BPM Studio
 * 
 * This is run for FORMAT VERSION 6 (which marks the transition from last AMX
 * BPM (5) before first ACE (6) version.
 *
 * @author aallway
 * @since 9 Apr 2019
 */
public class Bpm2CeBomMigration implements IBOMMigration {

    public Bpm2CeBomMigration() {
    }

    @Override
    public Command getMigrationCommand(TransactionalEditingDomain domain,
            Model model) {

        CompoundCommand cmd = new CompoundCommand();

        /*
         * Remove XSD stereotype.
         */
        Command c = removeXsdStereoType(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Switch integer attributes to Decimals with 0 max decimals.
         */
        c = refactorNumberAttributes(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        return !cmd.isEmpty() ? cmd : null;
    }

    /**
     * Create command to remove the XSD Notation Profile (for boms generated
     * from WSDL/XSD).
     * 
     * @param domain
     * @param model
     * @return Command or <code>null</code> is XsdNotationProfile not applied to
     *         this BOM.
     */
    private Command removeXsdStereoType(TransactionalEditingDomain domain,
            final Model model) {
        Command result = null;

        final Profile xsdNotationProfile =
                model.getAppliedProfile(BOMUtils.XSD_NOTATION_PROFILE);

        if (xsdNotationProfile != null) {
            result = new RecordingCommand(domain,
                    Messages.Bpm2CeBomMigration_RemoveXSDNotationProfile_cmd) {
                @Override
                protected void doExecute() {
                    model.unapplyProfile(xsdNotationProfile);
                }
            };

        }

        return result;
    }

    /**
     * Switch integer attributes/primitive-types to Decimal, Fixed Point with 0
     * max decimals.
     * 
     * @param domain
     * @param model
     * @return Command or <code>null</code> if no changes to make.
     */
    private Command refactorNumberAttributes(TransactionalEditingDomain domain,
            Model model) {
        CompoundCommand cmd = new CompoundCommand();

        /* Get the base Decimal primitive type. */
        ResourceSet rs = XpdResourcesPlugin.getDefault().getEditingDomain()
                .getResourceSet();

        PrimitiveType decimalType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        PrimitiveType integerType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        EList<Element> allOwnedElements = model.allOwnedElements();

        for (Element element : allOwnedElements) {
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            if (element instanceof Property) {
                final Property property = (Property) element;

                if (property.getType() != null
                        && PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME
                                .equals(property.getType().getName())) {

                    /*
                     * Switch Integer property type to Decimal, Fixed Point, 0
                     * decimals.
                     */
                    Command c = new RecordingCommand(domain) {

                        @Override
                        protected void doExecute() {
                            property.setType(decimalType);

                            PrimitivesUtil.setFacetPropertyValue(decimalType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT,
                                    property);

                            PrimitivesUtil.setFacetPropertyValue(decimalType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                    0,
                                    property);

                            /*
                             * Move the old integer constraints to their decimal
                             * equivalents
                             */
                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                    decimalType);

                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                    decimalType);

                            moveConstraint(property,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                    decimalType);
                        }

                        /**
                         * Move the srcConstraint to the tgtConstraint
                         * 
                         * @param property
                         * @param srcConstraint
                         * @param tgtConstraint
                         * @param propertyType
                         */
                        private void moveConstraint(Property property,
                                String srcConstraint, String tgtConstraint,
                                PrimitiveType propertyType) {
                            Object srcValue =
                                    PrimitivesUtil.getFacetPropertyValue(
                                            (PrimitiveType) property.getType(),
                                            srcConstraint,
                                            property);

                            if (srcValue != null) {
                                PrimitivesUtil.setFacetPropertyValue(
                                        propertyType,
                                        tgtConstraint,
                                        srcValue,
                                        property);

                                PrimitivesUtil.setFacetPropertyValue(
                                        propertyType,
                                        srcConstraint,
                                        null,
                                        property);
                            }
                        }
                    };

                    cmd.append(c);
                }

            }
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            else if (element instanceof PrimitiveType) {
                final PrimitiveType primitiveType = (PrimitiveType) element;

                /*
                 * If this is a primitive type that directly generalizes the
                 * base Integer primitive type then switch it to the base
                 * Decimal Fixed Point type and set decimals to zero.
                 */
                EList<Classifier> generals = primitiveType.getGenerals();

                if (generals != null && generals.size() == 1
                        && integerType.equals(generals.get(0))) {
                    /*
                     * Switch Integer property type to Decimal, Fixed Point, 0
                     * decimals.
                     */
                    Command c = new RecordingCommand(domain) {

                        @Override
                        protected void doExecute() {
                            primitiveType.getGenerals().clear();
                            primitiveType.getGenerals().add(decimalType);

                            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                    PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

                            PrimitivesUtil.setFacetPropertyValue(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES,
                                    0);
                            /*
                             * Move the old integer constraints to their decimal
                             * equivalents
                             */
                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE,
                                    decimalType);

                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER,
                                    decimalType);

                            moveConstraint(primitiveType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER,
                                    decimalType);
                        }

                        /**
                         * Move the srcConstraint to the tgtConstraint
                         * 
                         * @param primitiveType
                         * @param srcConstraint
                         * @param tgtConstraint
                         * @param decimalType
                         */
                        private void moveConstraint(PrimitiveType primitiveType,
                                String srcConstraint,
                                String tgtConstraint,
                                PrimitiveType decimalType) {
                            Object srcValue =
                                    PrimitivesUtil.getFacetPropertyValue(
                                            primitiveType,
                                            srcConstraint);

                            if (srcValue != null) {
                                PrimitivesUtil.setFacetPropertyValue(
                                        primitiveType,
                                        tgtConstraint,
                                        srcValue);

                                PrimitivesUtil.setFacetPropertyValue(
                                        primitiveType,
                                        srcConstraint,
                                        null);
                            }
                        }
                    };

                    cmd.append(c);
                }

            }

        }

        return !cmd.isEmpty() ? cmd : null;
    }

}
