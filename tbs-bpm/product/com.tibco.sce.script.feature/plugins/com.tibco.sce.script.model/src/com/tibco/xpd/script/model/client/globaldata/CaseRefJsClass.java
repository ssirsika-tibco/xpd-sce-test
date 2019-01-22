/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsClass;
import com.tibco.xpd.script.model.client.DynamicJsAttribute;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.ToStringMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefDeleteMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefGetLinkedObjectRefsMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefLinkAllMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefLinkMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefMultiValuedNavigateByCriteriaMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefMultiValuedNavigateByCriteriaStringMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefNavigateByCriteriaMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefNavigateByCriteriaStringMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefNavigateByCriteriaWithPageSizeMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefReadMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefUnlinkAllMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefUnlinkMethod;
import com.tibco.xpd.script.model.client.globaldata.caserefmethods.CaseRefUpdateMethod;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Case Ref Java Script class for a case class (a uml class in the bom with case
 * class stereotype)
 * 
 * @author bharge
 * @since 24 Oct 2013
 */
public class CaseRefJsClass extends AbstractJsClass {

    private String typeFQName;

    private final String REF = "Ref"; //$NON-NLS-1$

    private final String CLASSNAME = "className"; //$NON-NLS-1$

    private final String SIMPLE_CLASS_NAME = "simpleClassName"; //$NON-NLS-1$

    /**
     * XPD-5301 Now that Global Data task is available , some operations like
     * create,update,delete, link & unlink will be performed using this Task.
     * This flag is used to disable these methods which should not be supported
     * in the normal scripts.
     */
    private static final boolean DISABLED = true;

    /**
     * @param caseClass
     *            - uml class in the bom that has case class stereotype
     */
    public CaseRefJsClass(Class caseClass) {

        this.umlClass = caseClass;
        this.name = getCaseRefName(caseClass);
        this.image = getIcon(JsConsts.CASE_REF_TYPE);
        this.typeFQName = getCaseRefClassFQN(caseClass);
    }

    /**
     * Returns the Reference Type name for the given Global class.Returns blank
     * string for a local type.
     * 
     * @param caseClass
     * @return String
     */
    private String getCaseRefName(Class caseClass) {

        // FIXGlobalData use BDS API to get case ref name
        StringBuffer caseRefClass = new StringBuffer(caseClass.getName() + REF);
        return caseRefClass.toString();
    }

    /**
     * 
     * @param caseClass
     * @return String
     */
    private String getCaseRefClassFQN(Class caseClass) {

        return JScriptUtils.getFQType(caseClass) + REF;
    }

    /**
     * 
     * @param iconPath
     * @return Image
     */
    private Image getIcon(String iconPath) {

        Image image = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {

            image = Activator.getDefault().getImageRegistry().get(iconPath);
        }
        return image;
    }

    public String getType() {

        return this.typeFQName;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getMethodList()
     * 
     * @return
     */
    @Override
    public List<JsMethod> getMethodList() {

        if (null == this.methodList || this.methodList.isEmpty()) {

            this.methodList = buildCaseRefMethods(umlClass, name, typeFQName);
        }
        return this.methodList;
    }

    /**
     * build the method list for case ref types
     * 
     * @param caseClass
     * @param caseRefName
     * @param typeFQName
     * @return List<JsMethod>
     */
    private List<JsMethod> buildCaseRefMethods(Class caseClass,
            String caseRefName, String typeFQName) {

        List<JsMethod> methodList = new ArrayList<JsMethod>();

        /* add read method */
        methodList.add(new CaseRefReadMethod(caseClass, caseRefName));

        /*
         * add link/unlink methods
         */
        for (Property property : umlClass.getAllAttributes()) {
            /*
             * if association is shared or none add link/unlink methods on refs
             */
            if (property.getAssociation() != null
                    && (property.getAggregation() == AggregationKind.NONE_LITERAL || property
                            .getAggregation() == AggregationKind.SHARED_LITERAL)) {

                boolean multiValued = false;

                if (property.getUpper() > 1 || property.getUpper() == -1) {

                    multiValued = true;
                }
                /*
                 * different navigateByCriteria methods required based on the
                 * multiplicity of the association
                 */
                if (multiValued) {

                    methodList.add(new CaseRefGetLinkedObjectRefsMethod(
                            umlClass, property, name));
                    methodList
                            .add(new CaseRefMultiValuedNavigateByCriteriaMethod(
                                    umlClass, property, name));
                    methodList
                            .add(new CaseRefMultiValuedNavigateByCriteriaStringMethod(
                                    umlClass, property, name));
                    methodList
                            .add(new CaseRefNavigateByCriteriaWithPageSizeMethod(
                                    umlClass, property, name));

                } else {

                    methodList.add(new CaseRefNavigateByCriteriaMethod(
                            umlClass, property, name));
                    methodList.add(new CaseRefNavigateByCriteriaStringMethod(
                            umlClass, property, name));
                }
            }
        }
        // XPD-6031: Added toString() method needed for case reference mapping.
        methodList.add(new ToStringMethod());

        /**
         * XPD-5301 Now that Global Data task is available , some operations
         * like create,update,delete, link & unlink will be performed using this
         * Task. This flag is used to disable these methods which should not be
         * supported in the normal scripts.
         */
        if (!DISABLED) {
            /*
             * add update method
             */
            methodList.add(new CaseRefUpdateMethod(caseClass, this,
                    caseRefName, typeFQName));
            /*
             * add delete method
             */
            methodList.add(new CaseRefDeleteMethod(caseClass, caseRefName));

            for (Property property : umlClass.getAllAttributes()) {
                /*
                 * if association is shared or none add link/unlink methods on
                 * refs
                 */
                if (property.getAssociation() != null
                        && (property.getAggregation() == AggregationKind.NONE_LITERAL || property
                                .getAggregation() == AggregationKind.SHARED_LITERAL)) {

                    boolean multiValued = false;
                    if (property.getUpper() > 1 || property.getUpper() == -1) {

                        multiValued = true;
                    }

                    methodList.add(new CaseRefLinkMethod(umlClass, property,
                            name));

                    methodList.add(new CaseRefUnlinkMethod(umlClass, property,
                            name));

                    if (multiValued) {

                        methodList.add(new CaseRefLinkAllMethod(umlClass,
                                property, name, multiValued));

                        methodList.add(new CaseRefUnlinkAllMethod(umlClass,
                                property, multiValued, name));

                    }
                }
            }
        }

        return methodList;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getAttributeList()
     * 
     * @return
     */
    @Override
    public List<JsAttribute> getAttributeList() {

        if (null == this.attributeList || this.attributeList.isEmpty()) {

            this.attributeList = buildCaseRefAttributeList(umlClass);
        }
        return this.attributeList;
    }

    /**
     * case refs must not list the uml attributes defined in the bom model. they
     * must list className and simpleClassName as its attributes
     * 
     * @param caseClass
     * @return List<JsAttribute>
     */
    private List<JsAttribute> buildCaseRefAttributeList(Class caseClass) {

        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();

        /* className - the qualified class name */

        /*
         * XPD-7205: Preventing invalid thread access in the headless mode (DAA
         * export)
         */
        Image jsAttribImage = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            jsAttribImage =
                    Activator.getDefault().getImageRegistry()
                            .get(JsConsts.ICON_JSATTRIBUTE);
        }

        String comment = Messages.GlobalDataGenerator_comment_ref_className;

        DynamicJsAttribute classNameJsAttrib =
                new DynamicJsAttribute(CLASSNAME, "String", false, //$NON-NLS-1$ 
                        comment, jsAttribImage);
        attributeList.add(classNameJsAttrib);

        /* simpleClassName - the simple class name */

        comment = Messages.GlobalDataGenerator_comment_ref_simple_className;
        DynamicJsAttribute simpleClassNameJsAttrib =
                new DynamicJsAttribute(SIMPLE_CLASS_NAME, "String", false, //$NON-NLS-1$ 
                        comment, jsAttribImage);
        attributeList.add(simpleClassNameJsAttrib);

        return attributeList;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getReferenceList()
     * 
     * @return
     */
    @Override
    public List<JsReference> getReferenceList() {

        if (null == this.referenceList || this.referenceList.isEmpty()) {

            this.referenceList = buildCaseRefReferenceList(umlClass);
        }

        return this.referenceList;
    }

    /**
     * case refs must list its association attributes as '<relationName>Ref' or
     * '<relationName>Refs' based on the multiplicity of the association
     * attribute. this helps the user to navigate to another case class ref from
     * the given case class ref.
     * 
     * @param caseClass
     * @return List<JsReference>
     */
    private List<JsReference> buildCaseRefReferenceList(Class caseClass) {

        List<JsReference> referenceList = new ArrayList<JsReference>();
        /*
         * XPD-7205: Preventing invalid thread access in the headless mode (DAA
         * export)
         */
        Image jsAttribImage = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            jsAttribImage =
                    Activator.getDefault().getImageRegistry()
                            .get(JsConsts.ICON_JSATTRIBUTE);
        }
        for (Property property : caseClass.getAllAttributes()) {

            /*
             * if association is shared or none add this property as reference
             * to the reference list
             */
            if (property.getAssociation() != null
                    && (property.getAggregation() == AggregationKind.NONE_LITERAL || property
                            .getAggregation() == AggregationKind.SHARED_LITERAL)) {

                String comment =
                        Messages.GlobalDataGenerator_comment_ref_attribute;

                if (property.isMultivalued()) {

                    comment =
                            Messages.GlobalDataGenerator_comment_ref_attributes;

                }

                CaseRefJsReference jsRef =
                        new CaseRefJsReference(property, jsAttribImage, comment);
                referenceList.add(jsRef);

            }
        }
        return referenceList;
    }

}
