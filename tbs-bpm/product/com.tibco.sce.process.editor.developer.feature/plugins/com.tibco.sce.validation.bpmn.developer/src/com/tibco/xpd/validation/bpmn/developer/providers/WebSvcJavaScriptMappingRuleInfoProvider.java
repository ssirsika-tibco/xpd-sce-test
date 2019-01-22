/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.implementer.resources.xpdl2.properties.JavaScriptConceptUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.validation.bpmn.rules.baserules.ProcessDataMappingRuleInfoProvider;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Mapping rule content info provider for JavaScript web service mapper content
 * (i.e. web service params defined as generated BOM).
 * <p>
 * This is based on {@link ProcessDataMappingRuleInfoProvider} because the
 * javascript WSDL content provider uses faked formal-parameters to represent
 * WSDL message parts, so the standard process data and BOM handling in
 * {@link ProcessDataMappingRuleInfoProvider} should work just fine.
 * 
 * @author aallway
 * @since 3.3 (23 Jun 2010)
 */
public class WebSvcJavaScriptMappingRuleInfoProvider extends
        ProcessDataMappingRuleInfoProvider {

    /**
     * 
     */
    private static final String TEXT_PRIMITIVE_TYPE = "Text"; //$NON-NLS-1$

    /**
     * @param contentProvider
     */
    public WebSvcJavaScriptMappingRuleInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.
     * ProcessDataMappingRuleInfoProvider#getMinimumInstances(java.lang.Object)
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        /*
         * Check for the special case of the special "value" attribute created
         * to represent the simple content of a simple content element (with
         * extra attributes).
         * 
         * This should always be treated as required because if any other
         * sibling is mapped then the simple content must be given a value too.
         */
        if (objectInTree instanceof ConceptPath) {
            Object item = ((ConceptPath) objectInTree).getItem();
            if (item instanceof Property) {
                Object valueForStereotype =
                        XsdStereotypeUtils
                                .getValueForStereotype((Property) item,
                                        XsdStereotypeUtils.XSD_BASED_PROPERTY,
                                        XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_EXTENSION);
                if (!isStringContent((Property) item)) {
                    if (valueForStereotype instanceof Boolean) {
                        if (!isDefaultSpecified((Property) item)) {
                            Boolean isSimpleContentExtension =
                                    (Boolean) valueForStereotype;

                            if (isSimpleContentExtension) {
                                return 1;
                            }
                        }
                    }
                }
            }
            /*
             * Don't call super (which will treat all top level process-data
             * entries in list as mandatory) if this is a dummy formal parameter
             * placed here when real BOM class or input part cannot be found.
             */
            else if (item instanceof ProcessRelevantData) {
                if (JavaScriptConceptUtil
                        .isInvalidPartParameter((ConceptPath) objectInTree)) {
                    return 0;
                }
            }
        }

        return super.getMinimumInstances(objectInTree);
    }

    /**
     * @param item
     * @return
     */
    private boolean isDefaultSpecified(Property item) {
        EObject parent = item.eContainer();

        if (parent instanceof Property) {
            Object valueForStereotype =
                    XsdStereotypeUtils.getValueForStereotype((Property) parent,
                            XsdStereotypeUtils.XSD_BASED_PROPERTY,
                            XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

            return valueForStereotype != null
                    && valueForStereotype.toString().length() > 0;
        }
        return false;
    }

    /**
     * This is to validate if the primitive type of the simple content type
     * "value" is text. Text values can be empty strings and should be
     * complained about if not mapped.
     * 
     * @param item
     * @return
     */
    private boolean isStringContent(Property property) {
        if (property.getType() instanceof PrimitiveType) {
            if (TEXT_PRIMITIVE_TYPE.equals(property.getType().getName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.bpmn.rules.baserules.
     * ProcessDataMappingRuleInfoProvider#areProcessRelevantDataItemsMandatory()
     */
    @Override
    protected boolean areProcessRelevantDataItemsMandatory() {
        /*
         * Process relevant data items are mandatory targets because they
         * represent WSDL input/output part.
         */
        return true;
    }
}