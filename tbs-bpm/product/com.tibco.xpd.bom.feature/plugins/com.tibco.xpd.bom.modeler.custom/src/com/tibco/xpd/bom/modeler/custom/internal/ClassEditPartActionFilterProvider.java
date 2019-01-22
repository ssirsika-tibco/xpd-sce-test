/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.custom.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.action.filter.TestAttributeOperation;
import org.eclipse.gmf.runtime.emf.ui.services.action.AbstractModelActionFilterProvider;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Action filter provider for bom editor class edit part that defines behaviour
 * for case/global/local classes.
 * 
 * 
 * @author bharge
 * @since 7 Feb 2014
 */
public class ClassEditPartActionFilterProvider extends
        AbstractModelActionFilterProvider {

    /*
     * this string is used to identify this provider. (for instance from the
     * context menu option for generating business service on case class)
     */
    private static final String PARAM_TYPE = "type"; //$NON-NLS-1$

    private static final String TYPE_GLOBAL = "GLOBAL"; //$NON-NLS-1$

    /*
     * this string is used to provide the value for the key used for this
     * provider. (for instance generation of business service is allowed only on
     * case classes. this string is provided as the object state from context
     * menu option for generating business service on case class)
     */
    private static final String TYPE_CASE = "CASE"; //$NON-NLS-1$

    private static final String TYPE_LOCAL = "LOCAL"; //$NON-NLS-1$

    /**
     * @see org.eclipse.gmf.runtime.emf.ui.services.action.AbstractModelActionFilterProvider#doTestAttribute(java.lang.Object,
     *      java.lang.String, java.lang.String)
     * 
     * @param target
     * @param name
     * @param value
     * @return
     */
    @Override
    protected boolean doTestAttribute(Object target, String name, String value) {

        if (target instanceof ClassEditPart && name != null && value != null) {

            EObject eo = ((ClassEditPart) target).resolveSemanticElement();
            if (eo instanceof Class) {

                Class clz = (Class) eo;
                if (PARAM_TYPE.equals(name)) {

                    if (TYPE_GLOBAL.equals(value)) {

                        return BOMGlobalDataUtils.isGlobalClass(clz);
                    } else if (TYPE_CASE.equals(value)) {

                        return BOMGlobalDataUtils.isCaseClass(clz);
                    } else if (TYPE_LOCAL.equals(value)) {

                        return BOMGlobalDataUtils.isLocal(clz);
                    }
                }
            }
        }
        return false;
    }

    /**
     * @see org.eclipse.gmf.runtime.emf.ui.services.action.AbstractModelActionFilterProvider#doProvides(org.eclipse.gmf.runtime.common.core.service.IOperation)
     * 
     * @param operation
     * @return
     */
    @Override
    protected boolean doProvides(IOperation operation) {

        if (operation instanceof TestAttributeOperation) {

            return ((TestAttributeOperation) operation).getTarget() instanceof ClassEditPart;
        }
        return false;
    }

    /**
     * @see org.eclipse.gmf.runtime.emf.ui.services.action.AbstractModelActionFilterProvider#getEditingDomain(java.lang.Object)
     * 
     * @param target
     * @return
     */
    @Override
    protected TransactionalEditingDomain getEditingDomain(Object target) {

        return XpdResourcesPlugin.getDefault().getEditingDomain();
    }

}