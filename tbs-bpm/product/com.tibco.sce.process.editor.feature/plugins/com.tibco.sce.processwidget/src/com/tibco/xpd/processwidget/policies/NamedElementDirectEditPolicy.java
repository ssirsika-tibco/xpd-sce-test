/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.policies;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.tibco.xpd.processwidget.adapters.NamedElementAdapter;
import com.tibco.xpd.processwidget.parts.BaseGraphicalEditPart;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;

/**
 * Editing name of the NamedElementAdapter
 * 
 * @author wzurek
 */
public class NamedElementDirectEditPolicy extends DirectEditPolicy {

    
    /*
     * @see org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
     */
    protected Command getDirectEditCommand(DirectEditRequest request) {
        NamedElementAdapter act = (NamedElementAdapter) ((BaseGraphicalEditPart) getHost())
                .getModelAdapter();
        EditingDomain editingDomain = AdapterFactoryEditingDomain
                .getEditingDomainFor(act.getTarget());
        String val = (String) request.getCellEditor().getValue();
        return new EMFCommandWrapper(editingDomain, act.getSetNameCommand(
                editingDomain, val));
    }

    /*
     * @see org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
     */
    protected void showCurrentEditValue(DirectEditRequest request) {
        // do nothing
    }
}
