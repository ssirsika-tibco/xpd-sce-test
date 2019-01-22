/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.commands.SetDisplayLabelCommand;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * 
 * Custom parser that retrieves the name attribute of the Association end member
 * Property representing role name. The index of the Property end member is
 * specified in the constructor argument.
 * 
 * @author rgreen
 * 
 */
public class AssociationRoleParser extends AbstractAssociationParser {

    /**
     * The Constructor
     * 
     * @param index
     *            index of the association end to display/edit
     */
    public AssociationRoleParser(int index) {
        super(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.modeler.custom.parsers.AbstractAssociationParser#
     * getEditString(org.eclipse.core.runtime.IAdaptable, int)
     */
    public String getEditString(IAdaptable element, int flags) {
        String result = (String) element.getAdapter(String.class);
        if (result != null) {
            return result;
        }
        Property prop = getProperty(element);

        return prop != null ? PrimitivesUtil.getDisplayLabel(prop) : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.modeler.custom.parsers.AbstractAssociationParser#
     * getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String,
     * int)
     */
    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        Property prop = getProperty(element);

        if (prop != null) {

            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(prop);
            Resource resource = prop.eResource();

            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);

                return new SetDisplayLabelCommand(ed, file, prop, newString);
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.modeler.custom.parsers.AbstractAssociationParser#
     * isAffectingEvent(java.lang.Object, int)
     */
    public boolean isAffectingEvent(Object event, int flags) {

        if (event instanceof Notification) {
            Notification n = (Notification) event;
            EAttribute f1 = UMLPackage.eINSTANCE.getNamedElement_Name();

            if (n.getFeature() instanceof EAttribute) {
                EAttribute ea = (EAttribute) n.getFeature();

                if (f1.equals(ea)) {
                    return true;
                }
            }
        }
        return false;
    }

}
