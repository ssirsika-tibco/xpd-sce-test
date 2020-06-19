/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.n2.resources.postimport;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.part.custom.utils.ClassHeaderColourUtil;
import com.tibco.xpd.bom.resources.migration.IBOMMigration;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;
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

        /*
         * Sid ACE-4002 Add missing aggregation="composite" to non-association linkl attributes (these are missing from
         * very old BOMs)
         */
        c = refactorMissingAttributeAggregation(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Add the global data profile - all BOMs in ACE should be in Biz Data
         * projects and hence should have global data profile set (even if
         * they're not currently in biz data projects they'll have to be moved
         * there).
         */
        c = addGlobalDataProfile(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Remove class operations.
         */
        c = removeClassOperations(domain, model);
        if (c != null) {
            cmd.append(c);
        }

        /*
         * Convert any Global Classes to Local Classes
         */
        c = convertGlobalClassesToLocal(domain, model);
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
                                String srcConstraint, String tgtConstraint,
                                PrimitiveType decimalType) {
                            Object srcValue = PrimitivesUtil
                                    .getFacetPropertyValue(primitiveType,
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

    /**
     * Add the global data profile - all BOMs in ACE should be in Biz Data
     * projects and hence should have global data profile set (even if they're
     * not currently in biz data projects they'll have to be moved there).
     * 
     * @param domain
     * @param model
     * 
     * @return Command to add the global data profile or null if already set
     */
    private Command addGlobalDataProfile(TransactionalEditingDomain domain,
            Model model) {
        if (!GlobalDataProfileManager.getInstance().isGlobalDataBOM(model)) {
            return new RecordingCommand(domain) {

                @Override
                protected void doExecute() {
                    GlobalDataProfileManager.getInstance()
                            .applyGlobalDataProfile(model);
                }
            };
        }
        return null;
    }

    /**
     * Remove all operations from (usually ex-generated) BOM classes
     * 
     * @param domain
     * @param model
     * 
     * @return Command to remove the operations from classes in the model
     */
    private Command removeClassOperations(TransactionalEditingDomain domain,
            Model model) {
        return new RecordingCommand(domain) {

            @Override
            protected void doExecute() {
                EList<Element> allOwnedElements = model.allOwnedElements();

                for (Element element : allOwnedElements) {
                    if (element instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class clazz =
                                (org.eclipse.uml2.uml.Class) element;

                        clazz.getOwnedOperations().clear();
                    }
                }
            }
        };
    }

    /**
     * Converts Global Classes to Local Classes
     * 
     * @param domain
     *            The editing domain.
     * @param model
     *            The BOM UML model.
     * @return The command to convert the classes.
     */
    private Command convertGlobalClassesToLocal(
            TransactionalEditingDomain domain, Model model) {
        return new RecordingCommand(domain) {
            @Override
            protected void doExecute() {
                EList<Element> allOwnedElements = model.allOwnedElements();
                EList<EObject> contents = model.eResource().getContents();
                Diagram diagram = null;
                for (EObject content : contents) {
                    if (content instanceof Diagram) {
                        diagram = (Diagram) content;
                    }
                }

                for (Element element : allOwnedElements) {
                    if (element instanceof org.eclipse.uml2.uml.Class) {
                        org.eclipse.uml2.uml.Class clazz =
                                (org.eclipse.uml2.uml.Class) element;
                        GlobalDataProfileManager pm =
                                GlobalDataProfileManager.getInstance();
                        if (pm.isGlobal(clazz)) {
                            ClassHeaderColourUtil chcu =
                                    new ClassHeaderColourUtil();
                            ShapeGradientStyle sgs =
                                    chcu.getShapeGradientStyleForClass(clazz,
                                            diagram);
                            boolean resetColour =
                                    chcu.isDefaultColour(clazz, sgs);
                            pm.remove(clazz, StereotypeKind.GLOBAL);
                            if (resetColour) {
                                sgs.setGradStartColor(
                                        FigureUtilities.RGBToInteger(
                                                chcu.getDefaultColour(clazz)));
                            }
                        }
                    }
                }
            }
        };
    }

    /**
     * Some older BOM models (pre V4.0 Studio I think) did not set aggregation="composite" on standard attributes (non
     * association). This messes up bom->cdm & bom-js transforms and is inconsistent with attributes created in
     * current/later version.
     * 
     * So this function looks for ownedAttribute's that have aggregation=none and no association - these should be
     * compositions.
     * 
     * @param domain
     * @param model
     * @return Command or <code>null</code> if no changes to make.
     */
    private Command refactorMissingAttributeAggregation(TransactionalEditingDomain domain, Model model) {
        CompoundCommand cmd = new CompoundCommand();

        /* Get the base Decimal primitive type. */
        ResourceSet rs = XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet();

        PrimitiveType decimalType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        PrimitiveType integerType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rs, PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        EList<Element> allOwnedElements = model.allOwnedElements();

        for (Element element : allOwnedElements) {
            /*
             * Switch integer properties to FixedPoint with zero decimals.
             */
            if (element instanceof Property) {
                final Property property = (Property) element;

                if (AggregationKind.NONE_LITERAL.equals(property.getAggregation())
                        && property.getAssociation() == null) {
                    /*
                     * Aggregation=none and NOT a case class association so we need to set the plain old attribute as a
                     * composition.
                     */
                    cmd.append(new RecordingCommand(domain) {
                        @Override
                        protected void doExecute() {
                            property.setAggregation(AggregationKind.COMPOSITE_LITERAL);
                        }
                    });
                }
            }
        }

        return !cmd.isEmpty() ? cmd : null;
    }

}
