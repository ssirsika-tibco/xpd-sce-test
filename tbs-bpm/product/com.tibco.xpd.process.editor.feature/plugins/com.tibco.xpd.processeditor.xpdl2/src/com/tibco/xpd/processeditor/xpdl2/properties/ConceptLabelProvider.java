/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldImageProvider;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldTextProvider;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ConceptLabelProvider implements ILabelProvider {

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
     */
    @Override
    public Image getImage(Object element) {
        Image image = null;
        if (element instanceof ConceptPath) {
            element = ((ConceptPath) element).getItem();
            ImageRegistry ir =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();
            image = ir.get(ProcessEditorConstants.ICON_CHOICE);
        }
        if (element instanceof EObject) {
            image = WorkingCopyUtil.getImage((EObject) element);
        } else if (element instanceof InitialValue) {
            ImageRegistry ir =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();
            image = ir.get(ProcessEditorConstants.IMG_INITIAL_VALUE);
        } else if (element instanceof DataFieldImageProvider) {
            image = ((DataFieldImageProvider) element).getImage();
        }
        return image;
    }

    /**
     * @param element
     * @return
     * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
     */
    @Override
    public String getText(Object element) {
        String text = null;
        String conceptPathSuffix = ""; //$NON-NLS-1$

        /**
         * ChoiceConceptPath represents the xsd:choice equivalent. This is an
         * artificial node
         */
        if (element instanceof ChoiceConceptPath) {
            return Messages.ConceptLabelProvider_ChoiceNode_label;
        }
        if (element instanceof ConceptPath) {
            ConceptPath conceptPath = (ConceptPath) element;
            if (conceptPath.isArray()) {
                String indexStr = "";//$NON-NLS-1$
                if (conceptPath.isArrayItem()) {
                    // indexStr = String.valueOf(conceptPath.getIndex() - 1);
                    indexStr = String.valueOf(conceptPath.getIndex());
                }
                conceptPathSuffix = "[" + indexStr + "]";//$NON-NLS-1$//$NON-NLS-2$
            }
            element = conceptPath.getItem();

        }
        /*
         * SID XPD-1666: When we are passed properties directly then we should
         * show [] for multiplicity.
         */
        else if (element instanceof Property) {
            int upper = ((Property) element).getUpper();
            if (upper < 0 || upper > 1) {
                conceptPathSuffix = "[]";//$NON-NLS-1$
            }
        }

        if (element instanceof DataFieldTextProvider) {
            text = ((DataFieldTextProvider) element).getName();
        }
        if (element instanceof Property) {
            Property property = (Property) element;
            Type type = property.getType();

            if (type != null) {

                String subType = null;
                if (type instanceof PrimitiveType) {
                    /*
                     * If this is a base type Primitive Type then get an
                     * appropriate subtype if applicable (eg BigDecimal).
                     */

                    if (type.eResource() != null
                            && type.eResource().getURI() != null
                            && PrimitivesUtil.BOM_PRIMITIVE_TYPES_LIBRARY_URI
                                    .equals(type.eResource().getURI()
                                            .toString())) {
                        subType = getSubType((PrimitiveType) type, property);
                    }
                }

                if (null != subType) {
                    text = String.format("%1$s %2$s : %3$s", //$NON-NLS-1$
                            PrimitivesUtil.getDisplayLabel(property, true),
                            conceptPathSuffix,
                            subType);
                } else {

                    /*
                     * XPD-7870: Add Case Ref or BOM Type info after the Text.
                     */
                    if (type instanceof Class) {

                        /*
                         * XPD-7929: Saket: BOM child attribute is always BOM
                         * type even if it's a case class.
                         */
                        text = String.format("%1$s %2$s : %3$s (%4$s)", //$NON-NLS-1$
                                PrimitivesUtil.getDisplayLabel(property, true),
                                conceptPathSuffix,
                                PrimitivesUtil.getDisplayLabel(
                                        property.getType(),
                                        true),
                                Messages.ConceptLabelProvider_BomType_label);

                    } else {
                        text = String.format("%1$s %2$s : %3$s", //$NON-NLS-1$
                                PrimitivesUtil.getDisplayLabel(property, true),
                                conceptPathSuffix,
                                PrimitivesUtil.getDisplayLabel(
                                        property.getType(),
                                        true));
                    }
                }
            } else {
                text = PrimitivesUtil.getDisplayLabel(property, true);
            }
            /*
             * suffix attribute type "Object" with the sub-type (from BOM
             * advanced properties – such as xsd:AnyAttribute etc)
             */
            String objectSubType =
                    ConceptUtil.getObjectSubType((Property) element);
            if (objectSubType.length() > 0) {
                text += "(" + objectSubType + ")"; //$NON-NLS-1$//$NON-NLS-2$
            }
            /*
             * XPD-2128: show member types for a union separated by comma. eg.
             * jeans_size (Union: sizebyno, sizebystring)
             */
            if (type instanceof PrimitiveType) {
                /*
                 * Sid ACE-194 - we don't support XSD based BOMs in ACE
                 */

                // boolean isUnion = XSDUtil.isUnion((DataType) type);
                // if (isUnion) {
                // List<DataType> unionMemberTypes =
                // XSDUtil.getUnionMemberTypes((DataType) type);
                // if (unionMemberTypes.size() > 0) {
                // text += " (Union: "; //$NON-NLS-1$
                // for (DataType dataType : unionMemberTypes) {
                // text +=
                // PrimitivesUtil.getDisplayLabel(dataType,
                // true) + ", "; //$NON-NLS-1$;
                // }
                // text = text.substring(0, text.length() - 2);
                // text += ")"; //$NON-NLS-1$
                // }
                // }
            }
        } else if (element instanceof EObject) {
            if (StandardMappingUtil.REPLY_IMMEDIATE_PROCESS_ID_FORMALPARAMETER
                    .equals(element)) {
                text = Xpdl2ModelUtil.getDisplayName((NamedElement) element);
            } else {
                text = WorkingCopyUtil.getText((EObject) element);
                if (element instanceof ProcessRelevantData) {

                    ProcessRelevantData data = (ProcessRelevantData) element;

                    text = Xpdl2ModelUtil.getDisplayNameOrName(data);

                    if (data.isIsArray()) {
                        text += " []"; //$NON-NLS-1$
                    }

                    /*
                     * XPD-7448: Append the Human readable data type to the
                     * text.
                     */
                    String resolvedPrdType = getReadablePrdDataTpe(data);
                    /*
                     * appends the resolved Data Type.
                     */
                    if (resolvedPrdType != null) {
                        text += String.format(" : %1$s", resolvedPrdType); //$NON-NLS-1$
                    }
                }
            }
        }
        return text;
    }

    /**
     * 
     * @param data
     *            the data whose human readable data type is to be fetched.
     * @return the UI Data type to be displayed.
     */
    public String getReadablePrdDataTpe(Object data) {
        Object baseType =
                BasicTypeConverterFactory.INSTANCE.getBaseType(data, false);

        String resolvedType = null;

        if (baseType instanceof BasicType) {
            BasicType dataType = (BasicType) baseType;

            resolvedType =
                    ProcessDataUtil.getBasicTypeLabel(dataType.getType());

        } else if (baseType instanceof Classifier) {
            Classifier classifier = (Classifier) baseType;

            /*
             * XPD-7870: Mention the Case Ref and BOM type depending on the type
             * selected and not based on the referenced element because we can
             * ref a case Class by BOM Type as well.
             */

            if (data instanceof ProcessRelevantData) {
                ProcessRelevantData prd = (ProcessRelevantData) data;

                if (prd.getDataType() instanceof ExternalReference) {
                    /*
                     * label for bom type
                     */
                    resolvedType = String.format("%1$s (%2$s)", //$NON-NLS-1$
                            classifier.getLabel() != null
                                    ? classifier.getLabel()
                                    : classifier.getName(),
                            Messages.ConceptLabelProvider_BomType_label);

                } else if (prd.getDataType() instanceof RecordType) {
                    /*
                     * label for record type
                     */
                    resolvedType = String.format("%1$s (%2$s)", //$NON-NLS-1$
                            classifier.getLabel() != null
                                    ? classifier.getLabel()
                                    : classifier.getName(),
                            Messages.ConceptLabelProvider_CaseRef_label);
                } else {
                    /*
                     * for type declaration no need to mention Type Decl in the
                     * label as that is obvious and will take too much space.
                     */
                    resolvedType = classifier.getLabel() != null
                            ? classifier.getLabel()
                            : classifier.getName();
                }
            } else {

                resolvedType =
                        classifier.getLabel() != null ? classifier.getLabel()
                                : classifier.getName();
            }
        }
        return resolvedType;
    }

    /**
     * @param classifier
     * @return
     */
    private String getSubType(Classifier classifier, Property property) {
        String intOrDeciSubType = null;
        String typeName = null;

        if (classifier instanceof PrimitiveType) {
            Classifier basePrimitiveType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) classifier);
            if (null != basePrimitiveType) {
                typeName = basePrimitiveType.getName();
            }

            if (null != property) {
                if (JsConsts.INTEGER.equals(typeName)) {
                    Object facetPropertyValue = PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) classifier,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                                    property);

                    if (facetPropertyValue instanceof EnumerationLiteral
                            && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                    .equals((((EnumerationLiteral) facetPropertyValue)
                                            .getName()))) {
                        intOrDeciSubType = JsConsts.INTEGER;
                    } else {
                        intOrDeciSubType = JsConsts.INTEGER;
                    }
                } else if (JsConsts.DECIMAL.equals(typeName)) {
                    Object facetPropertyValue = PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) classifier,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                    property);
                    if (facetPropertyValue instanceof EnumerationLiteral
                            && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT.equals(
                                    (((EnumerationLiteral) facetPropertyValue)
                                            .getName()))) {
                        intOrDeciSubType = JsConsts.DECIMAL;
                    } else {
                        intOrDeciSubType = JsConsts.DECIMAL;
                    }

                }
            } else {

                if (JsConsts.INTEGER.equals(typeName)) {
                    Object facetPropertyValue = PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) classifier,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);

                    if (facetPropertyValue instanceof EnumerationLiteral
                            && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                    .equals((((EnumerationLiteral) facetPropertyValue)
                                            .getName()))) {
                        intOrDeciSubType = JsConsts.INTEGER;
                    } else {
                        intOrDeciSubType = JsConsts.INTEGER;
                    }
                } else if (JsConsts.DECIMAL.equals(typeName)) {
                    Object facetPropertyValue = PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) classifier,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);
                    if (facetPropertyValue instanceof EnumerationLiteral
                            && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT.equals(
                                    (((EnumerationLiteral) facetPropertyValue)
                                            .getName()))) {
                        intOrDeciSubType = JsConsts.DECIMAL;
                    } else {
                        intOrDeciSubType = JsConsts.DECIMAL;
                    }

                }
            }
        }
        return intOrDeciSubType;
    }

    /**
     * @param listener
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    /**
     * 
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /**
     * @param element
     * @param property
     * @return
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
     *      java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    /**
     * @param listener
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
    }

}
