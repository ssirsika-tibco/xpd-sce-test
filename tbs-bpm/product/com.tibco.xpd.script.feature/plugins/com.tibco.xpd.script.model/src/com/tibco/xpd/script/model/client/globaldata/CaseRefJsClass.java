/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsClass;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
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

		// ACE-8221 Returning an empty list here because the Content assist for CaseReference fields should not have any
		// attributes and methods
		// Consequently the method 'buildCaseRefMethods' has now been deleted.
		return Collections.emptyList();
		/*
		 * if (null == this.methodList || this.methodList.isEmpty()) {
		 * 
		 * this.methodList = buildCaseRefMethods(umlClass, name, typeFQName); } return this.methodList;
		 */
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getAttributeList()
     * 
     * @return
     */
    @Override
    public List<JsAttribute> getAttributeList() {

		// ACE-8221 Returning an empty list here because the Content assist for CaseReference fields should not have any
		// attributes and methods
		// Consequently the method 'buildCaseRefAttributeList' has now been deleted.
		return Collections.emptyList();
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
