/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.validator.resolution;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution for duplicate names.
 * 
 * @author glewis
 * 
 */
public class DuplicateNameResolution extends AbstractWorkingCopyResolution
        implements IResolution {
    
    @SuppressWarnings("restriction")
    @Override
    protected Command getResolutionCommand(final EditingDomain editingDomain,
            final EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        
        if (target.eContainer() != null && target instanceof NamedElement){
            String newName = getUniqueName(target); 
            if (newName != null){
                TransactionalEditingDomain ed = XpdResourcesPlugin.getDefault()
                        .getEditingDomain();
                if (ed != null) {
                    cmd = SetCommand.create(ed, target,
                            OMPackage.Literals.NAMED_ELEMENT__NAME, newName);
                }
            }
        }
        return cmd;
    }   
    
    /**
     * @param target
     * @return
     */
    private String getUniqueName(final EObject target){        
        int i=1;
        NamedElement element = (NamedElement)target;        
        EObject container = element.eContainer();
        String uniqueName = null;
        boolean isUnique = false;
        while (!isUnique){
            uniqueName = element.getName() + i;
            isUnique = isUniqueName(element, container, uniqueName);
            i++;
        }
        return uniqueName;
    }
    
    /**
     * @param element
     * @param container
     * @return
     */
    private boolean isUniqueName(NamedElement element, EObject container, String name) {        
        Iterator<EObject> iter = container.eAllContents();
        while (iter.hasNext()){
            EObject childElem = iter.next();
            if (childElem instanceof NamedElement && childElem != element && childElem.getClass().equals(element.getClass())){
                NamedElement childNamedElem = (NamedElement)childElem;                        
                if (childNamedElem.getName().equals(name)  ){
                    return false;                    
                }
            }
        }
        return true;
    }
}
