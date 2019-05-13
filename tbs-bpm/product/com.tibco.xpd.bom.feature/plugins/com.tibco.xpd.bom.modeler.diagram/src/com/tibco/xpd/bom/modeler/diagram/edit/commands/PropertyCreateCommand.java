/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class PropertyCreateCommand extends CreateElementCommand {

    private static int DEFAULT_LOWER_VALUE = 0;

    private static int DEFAULT_UPPER_VALUE = 1;

    private final Stereotype stereo;

    /**
     * @generated NOT
     */
    public PropertyCreateCommand(CreateElementRequest req) {
        super(req);
        stereo = (Stereotype) req.getParameter(
                BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE);
    }

    /**
     * @generated
     */
    @Override
    protected EObject getElementToEdit() {
        EObject container =
                ((CreateElementRequest) getRequest()).getContainer();
        if (container instanceof View) {
            container = ((View) container).getElement();
        }
        return container;
    }

    /**
     * @generated
     */
    @Override
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getStructuredClassifier();
    }

    /**
     * @generated NOT
     */
    @Override
    protected EObject doDefaultElementCreation() {

        Property newElement = (Property) super.doDefaultElementCreation();

        if (newElement != null) {
            Predicate<StereotypeKind> hasStereo = kind -> {
                return stereo != null && (stereo.equals(GlobalDataProfileManager
                        .getInstance().getStereotype(kind)));
            };

            UMLElementTypes.init_Property_2001(newElement);
            String defaultName = null;

            // If stereotype is already applied then it is a mandatory
            // stereotype
            List<Stereotype> lstStereos = newElement.getAppliedStereotypes();
            if (!lstStereos.isEmpty()) {
                String name = lstStereos.get(0).getLabel();
                defaultName = UML2ModelUtil.createUniquePropertyName(newElement,
                        name);
            } else if (stereo != null) {
                UML2ModelUtil.safeApplyStereotype(newElement, stereo);
                defaultName = UML2ModelUtil.createUniquePropertyName(newElement,
                        stereo.getName());
            } else if (getRequest().getParameter(
                    BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME) != null) {
                defaultName = (String) getRequest().getParameter(
                        // If the default name parameter has been set then use
                        // it,
                        // otherwise
                        // use default name
                        BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME);
            } else {
                defaultName =
                        UML2ModelUtil.createUniquePropertyName(newElement);
            }

            newElement.setName(defaultName);
            // Set the display label
            PrimitivesUtil.setDisplayLabel(newElement, defaultName, false);

            // Set AggregationKind attribute to composite by default
            newElement.setAggregation(AggregationKind.COMPOSITE_LITERAL);

            // If the type has been defined then set this as the default
            Object type = getRequest().getParameter(
                    BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.TYPE);
            if (type instanceof Type) {
                newElement.setType((Type) type);
            } else {
                ResourceSet rset = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();
                // XPD-4793 GlobalData: set default mandatory values for
                // AutoCaseIdentifier property
                if (hasStereo.test(StereotypeKind.AUTO_CASE_IDENTIFIER)) {
                    BOMGlobalDataUtils.setAutoCaseIdentifierRestrictions(
                            newElement,
                            rset);
                } else if (hasStereo.test(StereotypeKind.CASE_STATE)) {
                    // For the Case State we do not set a type, so do
                    // nothing, the type is already not-set by default
                } else {
                    newElement.setType(
                            PrimitivesUtil.getDefaultPrimitiveType(rset));
                }

            }
            // All case identifiers and case state need lower limit of 1.
            if (hasStereo.test(StereotypeKind.CID)
                    || hasStereo.test(StereotypeKind.COMPOSITE_CASE_IDENTIFIER)
                    || hasStereo.test(StereotypeKind.AUTO_CASE_IDENTIFIER)
                    || hasStereo.test(StereotypeKind.CASE_STATE)) {
                // XPD-4793 : CaseIdentifiers are mandatory with upperlimit 1.
                newElement.setLower(/* mandatory */ 1);
            } else {
                newElement.setLower(DEFAULT_LOWER_VALUE);
            }
            newElement.setUpper(DEFAULT_UPPER_VALUE);

        }
        return newElement;
    }
}
