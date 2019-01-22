/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.bom.types.internal;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.migration.IBOMMigration;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Performs migration changes to the features of the PrimitiveTypesFacets data
 * types.
 * 
 * <ul>
 * <li>1. In Studio 3.5 the Boolean type does not have a default value (false)
 * set. Therefore BOMs created prior to 3.5 will have to have FALSE set
 * explicitly or else it will be unset. The user will expect the original values
 * to be preserved.</li>
 * </ul>
 * 
 * @author rgreen
 * 
 */
public class BOMMigration1Types implements IBOMMigration {

    public BOMMigration1Types() {
    }

    @Override
    public Command getMigrationCommand(TransactionalEditingDomain domain,
            Model model) {

        return new SetBooleanDefaultsCommand(domain, model);
    }

    /**
     * @author rgreen
     * 
     */
    private class SetBooleanDefaultsCommand extends RecordingCommand {

        Model model;

        TransactionalEditingDomain ed;

        public SetBooleanDefaultsCommand(EditingDomain ed, Model mod) {
            super((TransactionalEditingDomain) ed);
            model = mod;
        }

        @Override
        protected void doExecute() {
            if (model != null) {

                EList<PackageableElement> packagedElements =
                        model.getPackagedElements();

                checkPackagedElements(packagedElements);

            }
        }

        /**
         * Loops through each packaged element checking it is either a Primitive
         * Type or Property. If the types are set to Boolean then checks to see
         * whther the former default value of false should be preserverd.
         * 
         * @param packagedElements
         */
        private void checkPackagedElements(
                EList<PackageableElement> packagedElements) {

            for (PackageableElement pElem : packagedElements) {

                if (pElem instanceof Package) {
                    Package pkge = (Package) pElem;
                    checkPackagedElements(pkge.getPackagedElements());
                }

                if (pElem instanceof Class) {
                    Class cl = (Class) pElem;
                    EList<Property> ownedAttributes = cl.getOwnedAttributes();

                    for (Property property : ownedAttributes) {
                        Type type = property.getType();

                        if (type instanceof PrimitiveType) {
                            checkBooleanValueIsSet((PrimitiveType) type,
                                    property);
                        }

                    }
                }

                if (pElem instanceof PrimitiveType) {
                    PrimitiveType pt = (PrimitiveType) pElem;
                    checkBooleanValueIsSet(pt, null);
                }

            }

        }

        /**
         * Performs the actual setting of the stereotype application (facet)
         * 
         * @param pt
         * @param prop
         */
        private void checkBooleanValueIsSet(PrimitiveType pt, Property prop) {
            // Check for a PrimitiveType of type Object
            ResourceSet rSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();

            PrimitiveType baseBooleanType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);

            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(pt);

            if (basePrimitiveType == baseBooleanType) {
                // Deal with Property first
                if (prop != null) {
                    Object facetPropertyValue =
                            PrimitivesUtil
                                    .getFacetPropertyValue(pt,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE,
                                            prop);

                    if (facetPropertyValue == null
                            && !BOMProfileUtils.isXSDProfileApplied(prop
                                    .getModel())) {
                        // If the value is null then prior to 3.5 the default
                        // value would have been implicitly FALSE which would
                        // have been
                        // inherited from the PrimitiveTypesFacets profile.
                        // This would not have created a stereotype
                        // profile (until the default value had been explicitly
                        // set). However, the user will expect the default value
                        // to show as FALSE in 3.5 so, because we have
                        // introduced the "not set" option we must explicitly
                        // set the stereotype application now.

                        // This is NOT NEEDED however, if the BOM has a
                        // XSDNotationProfile applied, since the BOM value
                        // should reflect the XSD stereotype default attribute
                        // value. This was a bug in previous version because the
                        // BOM value only had 2 states i.e. true and false.
                        // There was no ability to represent "not set". So now
                        // we have a good opportunity to align things correctly

                        // Then set to FALSE
                        PrimitivesUtil
                                .setFacetPropertyValue(pt,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE,
                                        "false",
                                        prop);
                    }

                } else {
                    // Deal with PrimitiveType

                    // No extra work required if the XSDNotation profile is
                    // applied (i.e. originates from import/build) since XSD
                    // SimpleType does not have the attribute "default". So we
                    // just set false explicitly and the PrimitiveType will keep
                    // on displaying the warning that default values will be
                    // ignored in XSD export.
                    Object facetPropertyValue2 =
                            PrimitivesUtil
                                    .getFacetPropertyValue(pt,
                                            PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE);

                    if (facetPropertyValue2 == null) {
                        // Then set to FALSE
                        PrimitivesUtil
                                .setFacetPropertyValue(pt,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_BOOLEAN_DEFAULT_VALUE,
                                        "false");
                    }

                }

            }

        }
    }

}
