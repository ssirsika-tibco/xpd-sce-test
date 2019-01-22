/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDWildcard;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.ImageConstants;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.WsdlPartPath;
import com.tibco.xpd.implementer.script.XsdPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ChoiceConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdExtension.XpdExtAttribute;
import com.tibco.xpd.xpdExtension.XpdExtAttributes;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */

// TODO - Extend
public class ParameterLabelProvider extends ConceptLabelProvider {

    private ImageRegistry imageRegistry;

    /**
     * @param param
     * @return
     */
    private String getDisplayNameForParamType(FormalParameter param) {
        Object baseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(param, true);
        if (baseType instanceof BasicType) {
            return ((BasicType) baseType).getType().toString().toLowerCase();
        } else {
            if (param.getDataType() instanceof ExternalReference) {
                Object clss = ConceptUtil.getExternalRefClass(param);
                if (clss instanceof Classifier) {
                    Classifier classifier = (Classifier) clss;
                    if (classifier instanceof PrimitiveType) {
                        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                                .equalsIgnoreCase(classifier.getName())) {
                            String objectSubType =
                                    ConceptUtil.getObjectSubType(classifier);
                            if (null != objectSubType
                                    && objectSubType.length() > 0) {
                                return objectSubType;
                            }
                        }
                        String unionLabel =
                                ConceptUtil.getUnionLabel(classifier);
                        if (null != unionLabel && unionLabel.length() > 0) {
                            return unionLabel;
                        }
                    }
                    if (null != classifier && null != classifier.getName()
                            && classifier.getName().length() > 0) {
                        String subType = getSubType(classifier);
                        if (null != subType) {
                            return classifier.getName() + "(" + subType + ")"; //$NON-NLS-1$//$NON-NLS-2$
                        }
                        return classifier.getName();
                    }

                } else {
                    /*
                     * COuldn't access referenced classifier so see if we can
                     * use the classifier name stored in ext attrs.
                     */
                    XpdExtAttributes extAttribs =
                            (XpdExtAttributes) Xpdl2ModelUtil
                                    .getOtherElement(param,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ExtendedAttributes());
                    if (extAttribs != null) {
                        EObject inList =
                                EMFSearchUtil
                                        .findInList(extAttribs.getAttributes(),
                                                XpdExtensionPackage.eINSTANCE
                                                        .getXpdExtAttribute_Name(),
                                                JavaScriptConceptUtil.ORIGINAL_TYPE_DEF_EXTRATTR_NAME);

                        if (inList instanceof XpdExtAttribute) {
                            return ((XpdExtAttribute) inList).getValue();
                        }
                    }
                }
            }

        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @param classifier
     * @return
     */
    private String getSubType(Classifier classifier) {
        String intOrDeciSubType = null;
        String typeName = null;

        if (classifier instanceof PrimitiveType) {
            Classifier basePrimitiveType =
                    PrimitivesUtil
                            .getBasePrimitiveType((PrimitiveType) classifier);
            if (null != basePrimitiveType) {
                typeName = basePrimitiveType.getName();
            }

            if (JsConsts.INTEGER.equals(typeName)) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) classifier,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);

                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGINTEGER;
                } else {
                    intOrDeciSubType = JsConsts.INTEGER;
                }
            } else if (JsConsts.DECIMAL.equals(typeName)) {
                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) classifier,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);
                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGDECIMAL;
                } else {
                    intOrDeciSubType = JsConsts.DECIMAL;
                }

            }
        }
        return intOrDeciSubType;
    }

    /**
     * @param element
     *            The element to get the image for.
     * @return The image for the element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (imageRegistry == null) {
            imageRegistry = Activator.getDefault().getImageRegistry();
        }

        if (element instanceof ChoiceConceptPath) {

            return imageRegistry.get(ImageConstants.CHOICE);
            /*
             * return XpdResourcesUIActivator.getDefault().getImageRegistry()
             * .get(XpdResourcesUIConstants.ICON_BLANK16);
             */
        }
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        if (JavaScriptConceptUtil.isParameterRepresentingPart(element)) {
            image = imageRegistry.get(ImageConstants.PART);
        } else {
            image = super.getImage(element);
        }

        if (image == null) {
            if (element instanceof WsdlPartPath) {
                image = imageRegistry.get(ImageConstants.PART);
            } else if (element instanceof XsdPath) {
                XSDConcreteComponent content =
                        ((XsdPath) element).getComponent();
                boolean isArray = false;
                if (content instanceof XSDParticle) {
                    XSDParticle particle = (XSDParticle) content;
                    int max = particle.getMaxOccurs();
                    isArray = max > 1 || max == -1;
                    content = particle.getContent();
                }

                /*
                 * XPD-1491 allow for xsd:any content
                 */
                if (content instanceof XSDWildcard) {
                    if (isArray) {
                        image =
                                imageRegistry
                                        .get(ImageConstants.XSD_WILDCARD_ARRAY);
                    } else {
                        image = imageRegistry.get(ImageConstants.XSD_WILDCARD);
                    }
                } else if (content instanceof XSDElementDeclaration) {
                    if (isArray) {
                        image = imageRegistry.get(ImageConstants.ARRAY);
                    } else {
                        image = imageRegistry.get(ImageConstants.ELEMENT);
                    }
                } else if (content instanceof XSDAttributeUse) {
                    image = imageRegistry.get(ImageConstants.ATTRIBUTE);
                } else if (content instanceof XSDModelGroup) {
                    XSDCompositor compositor =
                            ((XSDModelGroup) content).getCompositor();
                    if (XSDCompositor.CHOICE_LITERAL.equals(compositor)) {
                        image = imageRegistry.get(ImageConstants.CHOICE);
                    } else {
                        image = imageRegistry.get(ImageConstants.SEQUENCE);
                    }
                }
            } else if (element instanceof EObject) {
                image = WorkingCopyUtil.getImage((EObject) element);
            }
        }
        return image;
    }

    /**
     * @param element
     *            The element to get the text for.
     * @return The text label for the element.
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(final Object item) {
        Object element = item;
        String text = null;
        /**
         * ChoiceConceptPath represents the xsd:choice equivalent. This is an
         * artificial node
         */
        if (element instanceof ChoiceConceptPath) {
            return Messages.ParameterLabelProvider_ChoiceNode_label;
        }
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
        }
        if (JavaScriptConceptUtil.isParameterRepresentingPart(element)) {
            FormalParameter param = (FormalParameter) element;
            String displayNameForParamType = getDisplayNameForParamType(param);
            return String.format("%1$s : %2$s", //$NON-NLS-1$
                    param.getName(),
                    displayNameForParamType);
        } else {
            text = super.getText(item);
        }
        if (text == null) {
            if (element instanceof WsdlPartPath) {
                Part part = ((WsdlPartPath) element).getPart();
                if (part.getName() == null) {
                    if (part.getElementName() != null) {
                        text = part.getElementName().getLocalPart();
                    }
                } else {
                    if (part.getElementName() == null) {
                        if (part.getTypeDefinition() == null) {
                            text = part.getName();
                        } else {
                            text = part.getName() + " : " //$NON-NLS-1$
                                    + part.getTypeDefinition().getName();
                        }
                    } else {
                        text = part.getName() + " : " //$NON-NLS-1$
                                + part.getElementName().getLocalPart();
                    }
                }
            } else if (element instanceof XsdPath) {
                text = ((XsdPath) element).getDisplayName();
            } else {
                text = element.toString();
            }
        }
        return text;
    }

    /**
     * Not supported.
     * 
     * @param listener
     *            n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param element
     *            The elemement to check.
     * @param property
     *            The element property.
     * @return false.
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * Not supported.
     * 
     * @param listener
     *            n/a
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

}
