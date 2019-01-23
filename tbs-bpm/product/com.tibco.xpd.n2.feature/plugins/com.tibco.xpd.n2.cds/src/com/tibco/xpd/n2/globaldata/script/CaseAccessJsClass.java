/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACCastingMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACCreateAllMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACCreateCriteriaWithPageSizeMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACCreateCriteriaWithStringMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACCreateMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACDeleteAllByIdMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACDeleteByCompositeIdentifier;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACDeleteByIdMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACDeleteRefsMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindAllMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindAllWithPageSizeMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindByCompositeIdentifier;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindByCriteriaMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindByCriteriaStringMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindByCriteriaWithPageSizeMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindByMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACFindWithPageSizeMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACReadMethod;
import com.tibco.xpd.n2.globaldata.script.cacmethods.CACUpdateMethod;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.AbstractJsClass;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;

/**
 * Case Access Java Script (factory) class for a case class (a uml class in the
 * bom with case class stereotype)
 * 
 * @author bharge
 * @since 24 Oct 2013
 */
public class CaseAccessJsClass extends AbstractJsClass {

    private CaseRefJsClass caseRefJsClass;

    /**
     * XPD-5301 Now that Global Data task is available , some operations like
     * create,update,delete will be performed using this Task. This flag is used
     * to disable these methods which should not be supported in the normal
     * scripts.
     */
    private static final boolean DISABLED = true;

    /**
     * Because we dont set uml class on case access js class (see comments in
     * constructor below), and if we need corresponding uml class for case
     * access js class (in validations especially) then we can get it via case
     * ref js class
     * 
     * @return the caseRefJsClass
     */
    public CaseRefJsClass getCaseRefJsClass() {

        return caseRefJsClass;
    }

    private static final String CAC = "cac_"; //$NON-NLS-1$

    public CaseAccessJsClass(CaseRefJsClass caseRefJsClass) {

        /*
         * DO NOT SET uml class on case access js classes. setting uml class on
         * case access js class (cac in short) results in validation error. if
         * it finds a uml class on a cac js class, it treats the cac js class as
         * a uml class in the bom and gives the validation error that methods on
         * uml classes are not supported
         */
        // this.umlClass = caseRefJsClass.getUmlClass();
        this.caseRefJsClass = caseRefJsClass;
        this.name = computeCACName(caseRefJsClass.getUmlClass());
        this.image = getIcon(CdsConsts.CAC_FACTORY);
        this.methodList = buildMethods(this.caseRefJsClass, name);
    }

    /**
     * 
     * @param iconPath
     * @return Image
     */
    private Image getIcon(String iconPath) {

        Image image = null;
        if (!XpdResourcesPlugin.isInHeadlessMode()) {

            image = CDSActivator.getDefault().getImageRegistry().get(iconPath);
        }
        return image;
    }

    /**
     * 
     * @param caseClass
     * @return String
     */
    private String computeCACName(Class caseClass) {

        // FIXGlobalData use BDS API to get CAC name
        if (BOMGlobalDataUtils.isCaseClass(caseClass)) {

            StringBuffer cacClassName =
                    new StringBuffer(CAC
                            + caseClass.getQualifiedName().replace('.', '_')
                                    .replace("::", "_")); //$NON-NLS-1$ //$NON-NLS-2$

            return cacClassName.toString();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getMethodList()
     * 
     * @return
     */
    @Override
    public List<JsMethod> getMethodList() {

        return this.methodList;
    }

    /**
     * @param caseRefJsClass
     * @param cacName
     * @return List<JsMethod>
     */
    private List<JsMethod> buildMethods(CaseRefJsClass caseRefJsClass,
            String cacName) {

        List<JsMethod> methodList = new ArrayList<JsMethod>();
        Class caseClass = caseRefJsClass.getUmlClass();

        /*
         * add find all method
         */
        methodList.add(new CACFindAllMethod(caseRefJsClass, cacName));
        /*
         * add find all with page size method
         */
        methodList
                .add(new CACFindAllWithPageSizeMethod(caseRefJsClass, cacName));
        /*
         * add find method
         */
        methodList.add(new CACFindMethod(caseRefJsClass, cacName));
        /*
         * add find with page size method
         */
        methodList.add(new CACFindWithPageSizeMethod(caseRefJsClass, cacName));
        /*
         * add find by criteria method
         */
        methodList.add(new CACFindByCriteriaMethod(caseRefJsClass, cacName));
        /*
         * add find by criteria string method
         */
        methodList.add(new CACFindByCriteriaStringMethod(caseRefJsClass,
                cacName));
        /*
         * add find by criteria with page size method
         */
        methodList.add(new CACFindByCriteriaWithPageSizeMethod(caseRefJsClass,
                cacName));
        /*
         * add read method
         */
        methodList.add(new CACReadMethod(caseRefJsClass, cacName));

        /*
         * XPD-5683: requested to move createCriteria methods from DataUtil to
         * cac classes
         */
        /* add create criteria with string method */
        methodList.add(new CACCreateCriteriaWithStringMethod(caseRefJsClass,
                cacName));
        /* add create criteria with string and page size method */
        methodList.add(new CACCreateCriteriaWithPageSizeMethod(caseRefJsClass,
                cacName));
        /*
         * add casting method
         */
        for (Class superClass : caseClass.getSuperClasses()) {

            methodList.add(new CACCastingMethod(caseRefJsClass, cacName,
                    superClass));
        }

        /*
         * add case id methods
         */
        List<Property> compositeIdProperties = new ArrayList<Property>();
        List<Property> autoOrCustomCIdProperties = new ArrayList<Property>();
        /*
         * get the composite case ids and auto/custom case ids
         */
        for (Property property : caseClass.getAllAttributes()) {

            if (BOMGlobalDataUtils.isCompositeCID(property)) {

                compositeIdProperties.add(property);
            } else if (BOMGlobalDataUtils.isAutoCID(property)
                    || BOMGlobalDataUtils.isCustomCID(property)) {

                autoOrCustomCIdProperties.add(property);
            }
        }
        /* create composite case id specific methods */
        if (compositeIdProperties.size() > 0) {

            /* add find by composite identifier method */
            methodList.add(new CACFindByCompositeIdentifier(caseRefJsClass,
                    cacName, compositeIdProperties));
        }

        /* create auto/custom case id specific methods */
        for (Property property : autoOrCustomCIdProperties) {

            /* add find by case id method */
            methodList.add(new CACFindByMethod(caseRefJsClass, cacName,
                    property));

        }

        /**
         * XPD-5301 Now that Global Data task is available , some operations
         * like create,update,delete will be performed using this Task. This
         * flag is used to disable these methods which should not be supported
         * in the normal scripts.
         */

        if (!DISABLED) {
            /*
             * add create method
             */
            methodList.add(new CACCreateMethod(caseRefJsClass, cacName));
            /*
             * add create all method
             */
            methodList.add(new CACCreateAllMethod(caseRefJsClass, cacName));
            /*
             * add update method
             */
            methodList.add(new CACUpdateMethod(caseRefJsClass, cacName));
            /*
             * add delete method
             */
            methodList.add(new CACDeleteRefsMethod(caseRefJsClass, cacName));

            /* create composite case id specific methods */
            if (compositeIdProperties.size() > 0) {

                /* add delete by composite identifier method */
                methodList.add(new CACDeleteByCompositeIdentifier(
                        caseRefJsClass, cacName, compositeIdProperties));
            }
            /* create auto/custom case id specific methods */
            for (Property property : autoOrCustomCIdProperties) {

                /* add delete by case id method */
                methodList.add(new CACDeleteByIdMethod(caseRefJsClass, cacName,
                        property));
                /* add delete all by case id method */
                methodList.add(new CACDeleteAllByIdMethod(caseRefJsClass,
                        cacName, property));

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

        return Collections.emptyList();
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsClass#getReferenceList()
     * 
     * @return
     */
    @Override
    public List<JsReference> getReferenceList() {

        return Collections.emptyList();
    }
}
