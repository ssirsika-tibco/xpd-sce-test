/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.resources.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.n2.ut.configuration.builder.UserActivityParticipantUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rhudson
 *
 */
public class UserActivityParticipantScriptResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core.resources.IMarker)
     *
     * @param marker
     * @throws ResolutionException
     */
    public void run(IMarker marker) throws ResolutionException {
        try
        {
            XpdProjectResourceFactory factory = XpdResourcesPlugin.getDefault().getXpdProjectResourceFactory(
                                    marker.getResource().getProject());
            WorkingCopy workingCopy = factory.getWorkingCopy(marker.getResource());
            String location = (String) marker.getAttribute(IMarker.LOCATION);
            Package xpdlPackage = (Package) workingCopy.getRootElement();
            Resource resource = xpdlPackage.eResource();
            if (resource != null)
            {
                EObject target = resource.getEObject(location);
                if (target != null)
                {
                    if (target instanceof Activity)
                    {
                        removePerformerScripts( (Activity)target );
                    }
                }
            }
        }
        catch (CoreException e)
        {
            throw new ResolutionException(e);
        }

    }

    /**
     * Remove the Conditional scripts from the participants of the given activity
     * 
     * @param activity  Activity to remove the scripts from the participants
     */
    private void removePerformerScripts( Activity activity ) {
        TaskUser user = ((Task)activity.getImplementation()).getTaskUser();
        if (user != null)
        {
            CompoundCommand commands =  new CompoundCommand();
            // Create the operation that will clear the Scripts
            EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(activity);

            for (Performer performer : activity.getPerformerList()) {
                // Check to see if this is a performer field
                ProcessRelevantData participantField = UserActivityParticipantUtils.
                                            getDynamicParticipant(activity, performer.getValue());
                if( participantField != null ) {
                    Object objARP = Xpdl2ModelUtil.getOtherElement(participantField,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConditionalParticipant());
                
                    if( objARP != null ) {
                        if (objARP instanceof ConditionalParticipant ) {
                            Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
                            newExpr.setScriptGrammar("Unspecified");
                            newExpr.getMixed().add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),  "");

                            commands.appendIfCanExecute(SetCommand.create(editingDomain, 
                                    objARP,
                                    XpdExtensionPackage.eINSTANCE.getConditionalParticipant_PerformerScript(),
                                    newExpr));
                        }
                    }
                }
            }
            
            if( commands.canExecute() )
            {
                editingDomain.getCommandStack().execute(commands);   
            }
        }
    }
}
